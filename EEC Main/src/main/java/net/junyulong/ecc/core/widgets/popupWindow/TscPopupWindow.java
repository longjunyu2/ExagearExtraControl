package net.junyulong.ecc.core.widgets.popupWindow;

import android.content.Context;
import android.widget.PopupWindow;

import net.junyulong.ecc.core.resource.EecDrawableResources;

public class TscPopupWindow extends PopupWindow {
    public TscPopupWindow(Context context) {
        super(context);
        // 设置背景为预设背景
        setBackgroundDrawable(EecDrawableResources.getDefaultPopupWindowBackground());
    }
}
