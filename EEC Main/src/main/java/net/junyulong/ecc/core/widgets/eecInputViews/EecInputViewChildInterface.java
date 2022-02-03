package net.junyulong.ecc.core.widgets.eecInputViews;

import android.view.View;

import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;

public interface EecInputViewChildInterface {

    // 更新视图
    void viewUpdate(EecInputViewUpdateType type);

    // 编辑视图
    void viewEdit();

    // 获取Model
    EecInputViewModel getModel();

    // 获取View
    View getEecView();

    // 设置边框颜色
    void setEdgeType(EecInputViewEdgeType edgeType);
}
