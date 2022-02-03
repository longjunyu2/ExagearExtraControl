package net.junyulong.ecc.uiOverlay;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.eltechs.axs.activities.XServerDisplayActivity;
import com.eltechs.axs.activities.XServerDisplayActivityInterfaceOverlay;
import com.eltechs.axs.widgets.viewOfXServer.ViewOfXServer;
import com.eltechs.axs.xserver.ViewFacade;
import com.eltechs.ed.controls.Controls;
import com.eltechs.ed.controls.EEControls;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.EecContext;
import net.junyulong.ecc.core.ExagearUtils;
import net.junyulong.ecc.core.widgets.eecTSController.EecTSControllerView;

import java.lang.ref.WeakReference;

public class EecUiOverlay implements XServerDisplayActivityInterfaceOverlay {

    private final Controls mControls;

    protected XServerDisplayActivity mHostActivity;

    protected ViewOfXServer mViewOfXServer;

    protected ViewFacade mXServerFacade;

    protected EecTSControllerView mTSCView;

    protected static WeakReference<View> tscWidgetReference;

    public EecUiOverlay(Controls controls) {
        this.mControls = controls;
    }

    @Override
    public View attach(XServerDisplayActivity xServerDisplayActivity, ViewOfXServer viewOfXServer) {
        this.mHostActivity = xServerDisplayActivity;
        this.mViewOfXServer = viewOfXServer;
        this.mXServerFacade = viewOfXServer.getXServerFacade();
        // 若第一次调用，则会实例化EEC，否则不会初始化
        EEC.attach(EecContext.getContext((EEControls) mControls, this,
                xServerDisplayActivity, viewOfXServer));
        // 禁用XServer视图的水平拉伸
        ExagearUtils.setHorizontalStretchEnabled(false);
        // 创建EEC视图封装
        if (tscWidgetReference == null || tscWidgetReference.get() == null)
            tscWidgetReference = new WeakReference<>(createUi(xServerDisplayActivity));
        return tscWidgetReference.get();
    }

    @Override
    public void detach() {
        ((ViewGroup) tscWidgetReference.get().getParent()).removeView(tscWidgetReference.get());
    }

    private View createUi(Context context) {
        mTSCView = new EecTSControllerView(context);
        return mTSCView;
    }
}