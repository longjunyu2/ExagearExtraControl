package net.junyulong.ecc.core.widgets.eecInputViews.button;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.input.events.EecKeyEvent;
import net.junyulong.ecc.core.input.events.type.EecKeyType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecBaseInputViewConfig;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewBridge;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewType;
import net.junyulong.ecc.core.widgets.eecInputViews.utils.EecInputViewDeployer;

public class EecButton extends AppCompatButton implements EecInputViewInterface {

    private final EecButtonConfig mConfig;

    private final EecInputViewBridge mBridge;

    public EecButton(Context context, EecButtonConfig config, EecInputViewBridge bridge) {
        super(context);
        this.mConfig = config;
        this.mBridge = bridge;
    }

    @Override
    public void viewUpdate() {
        // set layout param
        this.setLayoutParams(new FrameLayout.LayoutParams(EecInputViewDeployer.getRealWidth(mConfig),
                EecInputViewDeployer.getRealHeight(mConfig)));
        // set text
        this.setText(((EecButtonConfig) mConfig).getKeyName());
        // set position
        EecInputViewDeployer.RealViewPos pos = mBridge.getViewDeployer().getRealPos(this);
        this.setX(pos.x);
        this.setY(pos.y);
        // set listener
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EEC.getInstance().getEecInputManager().doEvent(new EecKeyEvent(EecKeyType.Press_Release, 3));
                Toast.makeText(getContext(), "Test Button.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void viewEdit() {

    }

    @Override
    public EecBaseInputViewConfig getEecConfig() {
        return mConfig;
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
