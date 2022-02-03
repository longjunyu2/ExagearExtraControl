package net.junyulong.ecc.core.universal;

import android.view.View;

public class ViewUtils {
    // 计算未显示的View的大小
    public static int[] getUndisplayedViewSize(View v) {
        int[] size = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        v.measure(width, height);
        size[0] = v.getMeasuredWidth();
        size[1] = v.getMeasuredHeight();
        return size;
    }
}
