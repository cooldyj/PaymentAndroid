package com.jerry.payment.mobile.module.service.zxing;

import android.app.ActionBar;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.module.service.zxing.camera.CameraManager;
import com.jerry.payment.mobile.module.service.zxing.decoding.CaptureActivityHandler;
import com.jerry.payment.mobile.module.service.zxing.decoding.InactivityTimer;
import com.jerry.payment.mobile.module.service.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * 扫一扫Activity
 * Created by jerry on 2016/8/1.
 */
public class ScanQRCodeActivity extends BaseActivity implements SurfaceHolder.Callback{

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private ImageView scanLine;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrate;

    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;

    private final float BEEP_VOLUME = 0.9f;
    private final long VIBRATE_DURATION = 200L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_scan_qrcode_activity);
        try {
            CameraManager.init(getApplication());
            hasSurface = false;
            inactivityTimer = new InactivityTimer(ScanQRCodeActivity.this);
            initView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        viewfinderView = (ViewfinderView) findViewById(R.id.scan_viewfinder);
        scanLine = (ImageView) findViewById(R.id.scan_line);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleTV.setText(getString(R.string.service_scan));
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                initAnimation();
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scan_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if(hasSurface){
            initCamera(surfaceHolder);
        }else {
            surfaceHolder.addCallback(ScanQRCodeActivity.this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(handler != null){
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (TextUtils.isEmpty(resultString) || resultString.equals("")) {
            Toast.makeText(ScanQRCodeActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        }else {
            ImageView imageView = (ImageView) findViewById(R.id.app_title_back);
            imageView.setImageBitmap(barcode);
//            Bundle bundle = new Bundle();
//            bundle.putString("result", resultString);
//            bundle.putParcelable("bitmap", barcode);
//            Intent it = new Intent();
//            it.putExtras(bundle);
//            it.setClass(ScanQRCodeActivity.this, MyQRCodeActivity.class);
//            startActivity(it);
//            IntentUtils.startActivity(ScanQRCodeActivity.this, MyQRCodeActivity.class, bundle);
            Toast.makeText(ScanQRCodeActivity.this, resultString, Toast.LENGTH_SHORT).show();
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(handler == null){
            handler = new CaptureActivityHandler(ScanQRCodeActivity.this,
                    decodeFormats, characterSet);
        }

    }

    /**
     * 扫描线循环移动动画
     */
    public void initAnimation() {
        try {
            Rect frame = CameraManager.get().getFramingRect();
            if (frame == null) {
                return;
            }
            scanLine.setVisibility(View.VISIBLE);
            scanLine.setLayoutParams(new RelativeLayout.LayoutParams(frame.right - frame.left,
                    ActionBar.LayoutParams.WRAP_CONTENT));
            // 这里是TranslateAnimation动画
            TranslateAnimation mAnimation = new TranslateAnimation(frame.left, frame.left, frame.top, frame.bottom - 30);
            mAnimation.setDuration(2500);
            scanLine.setAnimation(mAnimation);
            mAnimation.setRepeatCount(Integer.MAX_VALUE);
            mAnimation.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(!hasSurface){
            hasSurface = true;
            try {
                initCamera(holder);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView(){
        return viewfinderView;
    }

    public CaptureActivityHandler getHandler() {
        return handler;
    }

    public void drawViewfinder(){
        viewfinderView.drawViewfinder();
    }

    /**
     * 初始化扫描成功后的哔哔声
     */
    private void initBeepSound(){
        if(playBeep && mediaPlayer != null){
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.seekTo(0);
                }
            });

            try {
                AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                mediaPlayer = null;
            }
        }
    }

    /**
     * 震动并哔哔
     */
    private void playBeepSoundAndVibrate(){
        if(playBeep && mediaPlayer != null){
            mediaPlayer.start();
        }
        if(vibrate){
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }
}
