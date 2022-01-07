package com.eltechs.ed.controls;

import android.support.v4.app.DialogFragment;

import com.eltechs.axs.activities.XServerDisplayActivityInterfaceOverlay;

import java.util.List;

public abstract class Controls {

    public static Controls find(String str) {
        // stub
        return null;
    }

    public static Controls getDefault() {
        // stub
        return null;
    }

    public static List<Controls> getList() {
        // stub
        return null;
    }

    public abstract XServerDisplayActivityInterfaceOverlay create();

    public DialogFragment createInfoDialog() {
        // stub
        return null;
    }

    public abstract String getId();

    public abstract List<ControlsInfoElem> getInfoElems();

    public int getInfoImage() {
        return 0;
    }

    public abstract String getName();

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        return stb.append(getId()).append(" (").append(getName()).append(")").toString();
    }
}
