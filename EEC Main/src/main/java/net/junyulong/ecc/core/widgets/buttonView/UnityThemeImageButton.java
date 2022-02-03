package net.junyulong.ecc.core.widgets.buttonView;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.TypedValue;
import android.widget.ImageView;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.resource.EecImageResources;

public class UnityThemeImageButton extends AppCompatImageButton {
    public UnityThemeImageButton(Context context) {
        super(context);
        // 解析属性 selectableItemBackground
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        // 设置背景
        setBackgroundResource(typedValue.resourceId);
        // 自动调整边距
        setAdjustViewBounds(true);
        // 依照控件宽高缩放
        setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public UnityThemeImageButton(Context context, EecImageResources res) {
        this(context);
        setImageDrawable(EEC.getInstance().getEecResourcesManager().getDrawable(res));
    }
}
