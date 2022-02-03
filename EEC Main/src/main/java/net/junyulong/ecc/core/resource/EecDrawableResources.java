package net.junyulong.ecc.core.resource;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class EecDrawableResources {
    public static Drawable getDefaultPopupWindowBackground() {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#606060"));
        BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
        drawable.setAlpha(150);
        return drawable;
    }

    public static Drawable getDefaultSpinnerWindowBackground() {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#555555"));
        BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
        drawable.setAlpha(255);
        return drawable;
    }
}
