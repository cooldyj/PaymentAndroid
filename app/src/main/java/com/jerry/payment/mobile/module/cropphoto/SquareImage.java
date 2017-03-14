package com.jerry.payment.mobile.module.cropphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class SquareImage extends MaskedImage {
    public SquareImage(Context paramContext) {
        super(paramContext);
    }

    public SquareImage(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public SquareImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    public Bitmap createMask() {
        int i = getWidth();
        int j = getHeight();
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint(1);
//		Paint localPaint = new Paint();
        localPaint.setColor(-16777216);
        float f1 = getWidth();
        float f2 = getHeight();
        RectF localRectF = new RectF(0.0F, 0.0F, f1, f2);
		localCanvas.drawRoundRect(localRectF, 10, 10, localPaint);
//        localCanvas.drawRect(localRectF, localPaint);
//		localCanvas.drawOval(localRectF, localPaint);
        return localBitmap;
    }
}
