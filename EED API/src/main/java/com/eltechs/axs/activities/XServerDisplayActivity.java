package com.eltechs.axs.activities;

import android.content.Intent;

import com.eltechs.axs.applicationState.ApplicationStateBase;
import com.eltechs.axs.applicationState.PurchasableComponentsCollectionAware;
import com.eltechs.axs.applicationState.SelectedExecutableFileAware;
import com.eltechs.axs.applicationState.XServerDisplayActivityConfigurationAware;
import com.eltechs.axs.widgets.actions.Action;

import java.util.List;

public class XServerDisplayActivity<StateClass extends ApplicationStateBase<StateClass> &
        PurchasableComponentsCollectionAware &
        XServerDisplayActivityConfigurationAware &
        SelectedExecutableFileAware<StateClass>> extends FrameworkActivity<StateClass> {

    public void addDefaultPopupMenu(List<? extends Action> paramList) {
        // stub
    }

    public void freezeXServerScene() {
        // stub
    }

    public void hideDecor() {
        // stub
    }

    public boolean isHorizontalStretchEnabled() {
        // stub
        return true;
    }

    public void placeViewOfXServer(int p1, int p2, int p3, int p4) {
        // stub;
    }

    public void setHorizontalStretchEnabled(boolean enabled) {
        // stub
    }

    public void showPopupMenu() {
        // stub
    }

    public void startInformerActivity(Intent intent) {
        // stub
    }

    public void unfreezeXServerScene() {
        // stub
    }
}
