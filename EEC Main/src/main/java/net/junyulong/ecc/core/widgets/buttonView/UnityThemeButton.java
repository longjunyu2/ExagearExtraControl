package net.junyulong.ecc.core.widgets.buttonView;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;

public class UnityThemeButton extends AppCompatButton {
    public UnityThemeButton(Context context) {
        super(context);
        // 解析属性 selectableItemBackground
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        // 设置背景
        setBackgroundResource(typedValue.resourceId);
    }
}
