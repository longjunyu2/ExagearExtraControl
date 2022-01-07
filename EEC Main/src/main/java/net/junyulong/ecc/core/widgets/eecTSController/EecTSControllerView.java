package net.junyulong.ecc.core.widgets.eecTSController;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.junyulong.ecc.core.dialogs.EecButtonConfigDialog;
import net.junyulong.ecc.core.widgets.eecInputViews.EecBaseInputViewConfig;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewParentInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewType;
import net.junyulong.ecc.core.widgets.eecInputViews.utils.EecInputViewDeployer;

import java.util.ArrayList;

public class EecTSControllerView extends FrameLayout implements EecInputViewInterface,
        EecInputViewParentInterface {

    private final ArrayList<EecInputViewInterface> mViewOverlays;

    private EecInputViewInterface focusedOverlay;

    private final EecInputViewDeployer mViewDeployer;

    public final static String ParentId = "parent";

    public EecTSControllerView(Context context) {
        super(context);
        this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.TRANSPARENT);
        this.mViewDeployer = new EecInputViewDeployer(this);
        mViewOverlays = new ArrayList<>();
    }

    @Override
    public void viewUpdate() {
        this.removeAllViews();
        for (EecInputViewInterface overlay : mViewOverlays) {
            overlay.viewUpdate();
            this.addView(overlay.getEecView());
        }
    }

    @Override
    public void viewEdit() {
        // do not implement this method
    }

    @Override
    public EecBaseInputViewConfig getEecConfig() {
        return null;
    }

    @Override
    public EecInputViewType getEecType() {
        return null;
    }

    @Override
    public View getEecView() {
        return this;
    }

    @Override
    public boolean registerEecInputView(EecInputViewInterface overlay) {
        if (mViewOverlays.contains(overlay))
            return false;
        else {
            mViewOverlays.add(overlay);
            mViewDeployer.updateView(overlay);
            viewUpdate();
            return true;
        }
    }

    @Override
    public boolean unregisterEecInputView(EecInputViewInterface overlay) {
        if (mViewOverlays.contains(overlay)) {
            mViewOverlays.remove(overlay);
            viewUpdate();
            return true;
        } else
            return false;
    }

    @Override
    public void clearEecInputViews() {

    }

    @Override
    public ArrayList<EecInputViewInterface> getEecInputViews() {
        return mViewOverlays;
    }

    @Override
    public EecInputViewInterface getEecInputViewById(String id) {
        for (EecInputViewInterface overlay : mViewOverlays) {
            if (overlay.getEecConfig().getId().equals(id))
                return overlay;
        }
        return null;
    }

    @Override
    public int getCounts() {
        return mViewOverlays.size();
    }

    @Override
    public EecInputViewInterface getFocusedView() {
        return this.focusedOverlay;
    }

    @Override
    public void setFocusedView(EecInputViewInterface overlay) {
        this.focusedOverlay = overlay;
    }

    @Override
    public boolean setSelectedView(EecInputViewInterface overlay) {
        return false;
    }

    @Override
    public boolean removeSelectedView(EecInputViewInterface overlay) {
        return false;
    }

    @Override
    public EecInputViewDeployer getViewDeployer() {
        return mViewDeployer;
    }

    @Override
    public void editView(EecInputViewInterface inputViewInterface) {
        new EecButtonConfigDialog(this.getContext(), this).editView(inputViewInterface);
    }

    private void createRightToolbar() {
    }
}
