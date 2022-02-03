package net.junyulong.ecc.core.widgets.eecInputViews;

public enum EecInputViewEdgeType {
    // 正常控件的边框
    ViewNormal,
    // 错误控件的边框
    ViewError,
    // 被选择的控件的边框
    ViewSelected,
    // 正在移动的控件
    ViewMoving,
    // 控件的直接父控件
    ViewDirectParent,
    // 控件的间接父控件
    ViewIndirectParent,
    // 控件的直接子控件
    ViewDirectChildren,
    // 控件的间接子控件
    ViewIndirectChildren
}
