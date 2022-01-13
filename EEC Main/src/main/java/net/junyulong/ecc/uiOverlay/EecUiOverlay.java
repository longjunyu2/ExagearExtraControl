package net.junyulong.ecc.uiOverlay;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.eltechs.axs.activities.XServerDisplayActivity;
import com.eltechs.axs.activities.XServerDisplayActivityInterfaceOverlay;
import com.eltechs.axs.widgets.viewOfXServer.ViewOfXServer;
import com.eltechs.axs.xserver.ViewFacade;
import com.eltechs.ed.controls.Controls;
import com.eltechs.ed.controls.EEControls;

import net.junyulong.ecc.R;
import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.EecContext;
import net.junyulong.ecc.core.resource.ResourceHelper;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.widgets.eecInputViews.button.EecButtonConfig;
import net.junyulong.ecc.core.widgets.eecInputViews.utils.EecInputViewCreator;
import net.junyulong.ecc.core.widgets.eecTSController.EecTSControllerView;

public class EecUiOverlay implements XServerDisplayActivityInterfaceOverlay {

    private final Controls mControls;

    protected XServerDisplayActivity mHostActivity;

    protected ViewOfXServer mViewOfXServer;

    protected ViewFacade mXServerFacade;

    protected EecTSControllerView mTSCView;

    protected static View tscWidget;

    public EecUiOverlay(Controls controls) {
        this.mControls = controls;
    }

    @Override
    public View attach(XServerDisplayActivity xServerDisplayActivity, ViewOfXServer viewOfXServer) {
        this.mHostActivity = xServerDisplayActivity;
        this.mViewOfXServer = viewOfXServer;
        this.mXServerFacade = viewOfXServer.getXServerFacade();
        // start EEC
        EEC.attach(EecContext.getContext((EEControls) mControls, this, xServerDisplayActivity, viewOfXServer));
        viewOfXServer.setHorizontalStretchEnabled(false);
        // Create EEC UiOverlay
        if (tscWidget == null)
            tscWidget = createUi(xServerDisplayActivity);
        return tscWidget;
    }

    @Override
    public void detach() {

    }

    private View createUi(Context context) {
        mTSCView = new EecTSControllerView(context);

        mTSCView.registerEecInputView(EecInputViewCreator.createNewButtonOverlay(mHostActivity, new EecButtonConfig() {
            @Override
            public String getKeyName() {
                return "Test1";
            }
        }, mTSCView));

        //mTSCView.viewUpdate();
        // Test
        /*AppCompatButton button = new AppCompatButton(context);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.e("Hex", ResourceHelper.drawable2Hex(mHostActivity.getDrawable(R.drawable.outline_build_circle_white_24dp)));
            }
        });
        button.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setBackgroundDrawable(EEC.getInstance().getEecResourcesManager().getDrawable(EecImageResources.outline_build_circle_white_24dp));
        mTSCView.addView(button);*/
        return mTSCView;
    }
}