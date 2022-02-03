package net.junyulong.ecc.core.widgets.eecInputViews;

import net.junyulong.ecc.core.model.layout.EecInputViewKey;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;

public class EecThemeMakerAgency {

    // 获取控件文字大小信息
    public int getTextSizeSp(EecInputViewModel model) {
        return 13;
    }

    // 获取控件文字信息
    public String getText(EecInputViewModel model) {
        return model.getInfo().get(EecInputViewKey.Key_Name);
    }

    // 获取背景

}
