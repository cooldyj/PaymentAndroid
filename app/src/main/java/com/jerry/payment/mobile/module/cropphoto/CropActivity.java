package com.jerry.payment.mobile.module.cropphoto;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.utils.CropPhotoUtils;
import com.jerry.payment.mobile.utils.Logs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

/**
 * 自定义剪裁照片Activity
 */
public class CropActivity extends MonitoredActivity {

    private static final String TAG = "CropImage";

    // private static final boolean RECYCLE_INPUT = true;

    private int mAspectX, mAspectY;
    private final Handler mHandler = new Handler();

    // These options specifiy the output image size and whether we should
    // scale the output to fit it (or just crop it).
    // private int mOutputX, mOutputY;
    // private boolean mScale;
    // private boolean mScaleUp = true;
    private boolean mCircleCrop = false;

    boolean mSaving; // Whether the "save" button is already clicked.

    private CropImageView mImageView;

    private Bitmap mBitmap;

    // private RotateBitmap rotateBitmap;
    HighlightView mCrop;

    Uri targetUri;
    File cropPhotoDir;
    String cropPhotoName;
    // 用来记录图片重复次数，和保存图片命名有关
    int copyCount = 0;

    boolean isHaveCrop = true;

    HighlightView hv;

    // private int rotation = 0;
    //
    // private static final int ONE_K = 1024;
    // private static final int ONE_M = ONE_K * ONE_K;

    private ContentResolver mContentResolver;

    private static final int DEFAULT_WIDTH = 512;
    private static final int DEFAULT_HEIGHT = 384;

    private int width;
    private int height;
    private int sampleSize = 1;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.app_crop_img_activity);

        initViews();

        Intent intent = getIntent();
        targetUri = intent.getParcelableExtra(CropPhotoUtils.IMAGE_URI);
        cropPhotoDir = new File(intent.getStringExtra(CropPhotoUtils.CROP_PHOTO_DIR_PATH));
        cropPhotoName = intent.getStringExtra(CropPhotoUtils.CROP_PHOTO_NAME);
        isHaveCrop = intent.getBooleanExtra(CropPhotoUtils.CROP_RECT_KEY, true);
        copyCount = intent.getIntExtra(CropPhotoUtils.CROP_DUPLICAITION_COUNT, 0);
        mContentResolver = getContentResolver();

        boolean isBitmapRotate = false;
        if (mBitmap == null) {
            String path = getFilePath(targetUri);
            // 判断图片是不是旋转了90度，是的话就进行纠正。
            isBitmapRotate = isRotateImage(path);
            getBitmapSize();
            getBitmap();

        }

        if (mBitmap == null) {
            finish();
            return;
        }

        startFaceDetection(isBitmapRotate);
    }

    /**
     * 初始化view
     */
    private void initViews() {
        mImageView = (CropImageView) findViewById(R.id.image);
        mImageView.mContext = this;
        // 舍弃
        findViewById(R.id.discard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        // 旋转
        findViewById(R.id.rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRotateClicked();
            }
        });
        // 保存
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });
    }

    /**
     * 获取Bitmap分辨率，太大了就进行压缩
     */
    private void getBitmapSize() {
        InputStream is = null;
        try {

            is = getInputStream(targetUri);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);

            width = options.outWidth;
            height = options.outHeight;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void getBitmap() {
        InputStream is = null;
        try {
            try {
                is = getInputStream(targetUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while ((width / sampleSize > DEFAULT_WIDTH * 2) || (height / sampleSize > DEFAULT_HEIGHT * 2)) {
                sampleSize *= 2;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;
            mBitmap = BitmapFactory.decodeStream(is, null, options);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 判断照片是否需要旋转
     */
    private boolean isRotateImage(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取输入流
     */
    private InputStream getInputStream(Uri mUri) throws IOException {
        try {
            if (mUri.getScheme().equals("file")) {
                return new FileInputStream(mUri.getPath());
            } else {
                return mContentResolver.openInputStream(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    /**
     * 根据Uri返回文件路径
     */
    private String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    /**
     * 根据uri获取图片路径
     */
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        String imgPath;
        Cursor cursor = mContentResolver.query(mUri, null, null, null, null);
        cursor.moveToFirst();
        imgPath = cursor.getString(1); // 图片文件路径
        return imgPath;
    }

    /**
     * 检测是否旋转图片
     */
    private void startFaceDetection(final boolean isRotate) {
        if (isFinishing()) {
            return;
        }
        if (isRotate) {
            initBitmap();
        }

        mImageView.setImageBitmapResetBase(mBitmap, true);

        startBackgroundJob(this, null, getResources().getString(R.string.me_modify_wait), new Runnable() {
            @Override
            public void run() {
                final CountDownLatch latch = new CountDownLatch(1);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap b = mBitmap;
                        if (b != mBitmap && b != null) {
                            mImageView.setImageBitmapResetBase(b, true);
                            mBitmap.recycle();
                            mBitmap = b;
                        }
                        if (mImageView.getScale() == 1F) {
                            mImageView.center(true, true);
                        }
                        latch.countDown();
                    }
                });
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (isHaveCrop) {
                    mRunFaceDetection.run();
                }
            }
        }, mHandler);
    }

    /**
     * 旋转原图
     */
    private void initBitmap() {
        Matrix m = new Matrix();
        m.setRotate(90);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        try {
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, m, true);
        } catch (OutOfMemoryError ooe) {

            m.postScale((float) 1 / sampleSize, (float) 1 / sampleSize);
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, m, true);
        }
    }

    private static class BackgroundJob extends MonitoredActivity.LifeCycleAdapter implements Runnable {

        private final MonitoredActivity mActivity;
        private final ProgressDialog mDialog;
        private final Runnable mJob;
        private final Handler mHandler;
        private final Runnable mCleanupRunner = new Runnable() {
            @Override
            public void run() {
                mActivity.removeLifeCycleListener(BackgroundJob.this);
                if (mDialog.getWindow() != null)
                    mDialog.dismiss();
            }
        };

        public BackgroundJob(MonitoredActivity activity, Runnable job, ProgressDialog dialog, Handler handler) {
            mActivity = activity;
            mDialog = dialog;
            mJob = job;
            mActivity.addLifeCycleListener(this);
            mHandler = handler;
        }

        @Override
        public void run() {
            try {
                mJob.run();
            } finally {
                mHandler.post(mCleanupRunner);
            }
        }

        @Override
        public void onActivityDestroyed(MonitoredActivity activity) {
            // We get here only when the onDestroyed being called before
            // the mCleanupRunner. So, run it now and remove it from the queue
            mCleanupRunner.run();
            mHandler.removeCallbacks(mCleanupRunner);
        }

        @Override
        public void onActivityStopped(MonitoredActivity activity) {
            mDialog.hide();
        }

        @Override
        public void onActivityStarted(MonitoredActivity activity) {
            mDialog.show();
        }
    }

    private static void startBackgroundJob(MonitoredActivity activity, String title, String message, Runnable job,
            Handler handler) {
        // Make the progress dialog uncancelable, so that we can gurantee
        // the thread will be done before the activity getting destroyed.
        ProgressDialog dialog = ProgressDialog.show(activity, title, message, true, false);
        new Thread(new BackgroundJob(activity, job, dialog, handler)).start();
    }

    Runnable mRunFaceDetection = new Runnable() {
        float mScale = 1F;
        Matrix mImageMatrix;

        // Create a default HightlightView if we found no face in the picture.
        private void makeDefault() {
            // mImageView.re
            if (hv != null) {
                mImageView.remove(hv);
            }
            hv = new HighlightView(mImageView);

            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();

            Rect imageRect = new Rect(0, 0, width, height);

            // make the default size about 4/5 of the width or height
            int cropWidth = Math.min(width, height) * 4 / 5;
            int cropHeight = cropWidth;

            if (mAspectX != 0 && mAspectY != 0) {
                if (mAspectX > mAspectY) {
                    cropHeight = cropWidth * mAspectY / mAspectX;
                } else {
                    cropWidth = cropHeight * mAspectX / mAspectY;
                }
            }

            int x = (width - cropWidth) / 2;
            int y = (height - cropHeight) / 2;

            RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
            hv.setup(mImageMatrix, imageRect, cropRect, mCircleCrop, mAspectX != 0 && mAspectY != 0);
            mImageView.add(hv);
        }

        @Override
        public void run() {
            mImageMatrix = mImageView.getImageMatrix();

            mScale = 1.0F / mScale;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    makeDefault();

                    mImageView.invalidate();
                    if (mImageView.mHighlightViews.size() == 1) {
                        mCrop = mImageView.mHighlightViews.get(0);
                        mCrop.setFocus(true);
                    }
                }
            });
        }
    };

    /**
     * 旋转图片，每次以90度为单位
     */
    private void onRotateClicked() {
        startFaceDetection(true);
    }

    /**
     * 点击保存的处理，这里保存成功回传的是一个Uri，系统默认传回的是一个bitmap图，
     * 如果传回的bitmap图比较大的话就会引起系统出错。会报这样一个异常：
     * android.os.transactiontoolargeexception。为了规避这个异常， 采取了传回Uri的方法。
     */
    Bitmap croppedImage = null;

    private void onSaveClicked() {
        // TODO this code needs to change to use the decode/crop/encode single
        // step api so that we don't require that the whole (possibly large)
        // bitmap doesn't have to be read into memory
        if (isHaveCrop && mCrop == null) {
            return;
        }

        if (mSaving)
            return;
        mSaving = true;

        // final Bitmap croppedImage = null;
        if (isHaveCrop) {
            Rect r = mCrop.getCropRect();

            int width = r.width();
            int height = r.height();

            // If we are circle cropping, we want alpha channel, which is the
            // third param here.
            croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(croppedImage);
            Rect dstRect = new Rect(0, 0, width, height);

            canvas.drawBitmap(mBitmap, r, dstRect, null);

            // Release bitmap memory as soon as possible
            mImageView.clear();
            mBitmap.recycle();
            mBitmap = null;
        } else {
            croppedImage = mBitmap;
        }

        mImageView.setImageBitmapResetBase(croppedImage, true);
        mImageView.center(true, true);
        mImageView.mHighlightViews.clear();

        String imgPath = getFilePath(targetUri);
        // System.out.println("imgPath :"+imgPath);
        final String cropPath = imgPath.replace(".", "_crop_image.").trim();
        // System.out.println("cropPath :"+cropPath);

        // imgPath :/storage/emulated/0/pcauto4.0/userAvatar/头像.jpg
        // cropPath
        // :/storage/emulated/0/pcauto4_crop_image.0/userAvatar/头像_crop_image.jpg

        // 保存剪裁照片到sd卡
        saveDrawableToSDCard(croppedImage);

        // Uri cropUri = Uri.fromFile(new
        // File(Env.userAvatar.getPath()+File.separator+CropPhotoUtils.getCropPhotoFileName(Env.CROP_AVATAR)));
        Uri cropUri = Uri.fromFile(mFile);
        // Uri cropUri = Uri.fromFile(new File(cropPath));
        Intent intent = new Intent("inline-data");
        intent.putExtra(CropPhotoUtils.CROP_IMAGE_URI, cropUri);
        intent.putExtra(CropPhotoUtils.CROP_PHOTO_DIR_PATH, mFile.getAbsolutePath());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 保存处理文件的地址
     */
    private File mFile = null;

    /**
     * 将将剪裁后的Bitmap放入sd卡
     */
    private void saveDrawableToSDCard(Bitmap bitmap) {
        try {
            if (cropPhotoDir != null && cropPhotoDir.isDirectory()) {
                if (copyCount > 0 && !cropPhotoName.startsWith("copy_")) {
                    cropPhotoName = "copy_" + copyCount + "_" + cropPhotoName;
                }
                mFile = new File(cropPhotoDir, CropPhotoUtils.getCropPhotoFileName(cropPhotoName));
                Logs.d(TAG, "save file name = " + cropPhotoName);
                mFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(mFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
