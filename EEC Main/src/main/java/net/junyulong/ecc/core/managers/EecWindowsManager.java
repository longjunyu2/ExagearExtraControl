package net.junyulong.ecc.core.managers;

import android.util.DisplayMetrics;

public class EecWindowsManager {

    private DisplayMetrics displayMetrics;

    public static int CellCounts = 50;

    private int widthPx;

    private int heightPx;

    public EecWindowsManager(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    public void attach() {
        widthPx = displayMetrics.widthPixels;
        heightPx = displayMetrics.heightPixels;
    }

    public void detach() {
        this.displayMetrics = null;
    }

    public int getScreenHeight() {
        return this.heightPx;
    }

    public int getScreenWidth() {
        return this.widthPx;
    }

    // 获取最小块数量
    public int getMinimalCellCounts() {
        return CellCounts;
    }

    // 获取最大块数量
    public int getMaxCellCounts() {
        return getScreenWidth() / getRealBaseCl();
    }

    // 获取实际单位块长
    public int getRealBaseCl() {
        return heightPx / getMinimalCellCounts();
    }

    // Px值转Dp值
    public int getPxFromDp(float dpValue) {
        final float scale = displayMetrics.density;
        return (int) (dpValue * scale);
    }

    // Dp转Px
    public float getDpFromPx(float pxValue) {
        final float scale = displayMetrics.density;
        return (pxValue / scale);
    }

    // Sp转Px
    public int getPxFromSp(float spValue) {
        final float fontScale = displayMetrics.scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 获取屏幕比列
    public String getWindowRadio(int width, int height) {
        int gcd = getGCD(width, height);
        return width / gcd + " : " + height / gcd;
    }

    public String getWindowRadio() {
        return getWindowRadio(widthPx, heightPx);
    }

    //最大公约数
    private static int getGCD(int m, int n) {
        int result;
        while (n != 0) {
            result = m % n;
            m = n;
            n = result;
        }
        return m;
    }

}
