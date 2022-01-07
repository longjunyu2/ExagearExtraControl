package net.junyulong.ecc.core.widgets.eecInputViews;

import net.junyulong.ecc.core.widgets.eecInputViews.utils.EecInputViewDeployer;

public interface EecInputViewBridge extends EecInputViewEditableInterface {

    void setFocusedView(EecInputViewInterface overlay);

    boolean setSelectedView(EecInputViewInterface overlay);

    boolean removeSelectedView(EecInputViewInterface overlay);

    EecInputViewDeployer getViewDeployer();
}
