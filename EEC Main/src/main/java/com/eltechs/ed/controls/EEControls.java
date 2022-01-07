package com.eltechs.ed.controls;

import com.eltechs.axs.activities.XServerDisplayActivityInterfaceOverlay;

import net.junyulong.ecc.uiOverlay.EecUiOverlay;

import java.util.Arrays;
import java.util.List;

public class EEControls extends Controls {

    @Override
    public XServerDisplayActivityInterfaceOverlay create() {
        return new EecUiOverlay(this);
    }

    @Override
    public String getId() {
        return "eec";
    }

    @Override
    public List<ControlsInfoElem> getInfoElems() {
        return Arrays.asList(
                new ControlsInfoElem(0, "Exagear Extra Controls", "This controls mode is used to customize the operation layout."),
                new ControlsInfoElem(0, "Float Button", "Click this button to configure customize controls."
                ));
    }

    @Override
    public String getName() {
        return "EEC";
    }
}
