package net.junyulong.ecc.core;

import com.eltechs.axs.activities.XServerDisplayActivity;
import com.eltechs.axs.widgets.viewOfXServer.ViewOfXServer;
import com.eltechs.ed.controls.EEControls;

import net.junyulong.ecc.uiOverlay.EecUiOverlay;

public class EecContext {

    private final EEControls controls;

    private final EecUiOverlay overlay;

    private final XServerDisplayActivity hostActivity;

    private final ViewOfXServer viewOfXServer;

    private EecContext(EEControls eeControls, EecUiOverlay eecUiOverlay,
                       XServerDisplayActivity hostActivity, ViewOfXServer viewOfXServer) {
        this.controls = eeControls;
        this.overlay = eecUiOverlay;
        this.hostActivity = hostActivity;
        this.viewOfXServer = viewOfXServer;
    }

    public static EecContext getContext(EEControls eeControls, EecUiOverlay eecUiOverlay,
                                        XServerDisplayActivity hostActivity, ViewOfXServer viewOfXServer) {
        return new EecContext(eeControls, eecUiOverlay, hostActivity, viewOfXServer);
    }

    public EEControls getControls() {
        return this.controls;
    }

    public EecUiOverlay getUiOverlay() {
        return this.overlay;
    }

    public XServerDisplayActivity getActivity() {
        return this.hostActivity;
    }

    public ViewOfXServer getXServerView() {
        return this.viewOfXServer;
    }
}
