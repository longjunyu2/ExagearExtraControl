package net.junyulong.ecc.core.managers;

import android.graphics.drawable.Drawable;

import net.junyulong.ecc.core.resource.ResourceHelper;
import net.junyulong.ecc.core.resource.EecImageResources;

public class EecResourcesManager {

    public EecResourcesManager() {

    }

    public void attach() {

    }

    public void detach() {

    }

    public Drawable getDrawable(EecImageResources resId) {
        return ResourceHelper.hex2Drawable(resId.getHex());
    }
}
