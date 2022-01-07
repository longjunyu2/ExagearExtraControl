package net.junyulong.ecc.core.widgets.eecInputViews.joystick;

import android.content.Context;
import android.view.View;

import net.junyulong.ecc.core.widgets.eecInputViews.EecBaseInputViewConfig;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewBridge;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewType;

public class EecJoystick extends View implements EecInputViewInterface {

    private final EecInputViewBridge bridge;

    public EecJoystick(Context context, EecInputViewBridge bridge) {
        super(context);
        this.bridge = bridge;
    }

    @Override
    public void viewUpdate() {

    }

    @Override
    public void viewEdit() {

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
}
