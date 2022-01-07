package net.junyulong.ecc.core.widgets.eecInputViews;

import android.view.View;

public interface EecInputViewInterface {
    void viewUpdate();

    void viewEdit();

    EecBaseInputViewConfig getEecConfig();

    EecInputViewType getEecType();

    View getEecView();

}
