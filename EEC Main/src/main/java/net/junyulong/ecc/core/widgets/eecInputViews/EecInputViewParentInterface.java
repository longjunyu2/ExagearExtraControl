package net.junyulong.ecc.core.widgets.eecInputViews;

import java.util.ArrayList;

public interface EecInputViewParentInterface extends EecInputViewBridge {

    boolean registerEecInputView(EecInputViewInterface overlay);

    boolean unregisterEecInputView(EecInputViewInterface overlay);

    void clearEecInputViews();

    ArrayList<EecInputViewInterface> getEecInputViews();

    EecInputViewInterface getEecInputViewById(String id);

    int getCounts();

    EecInputViewInterface getFocusedView();

}
