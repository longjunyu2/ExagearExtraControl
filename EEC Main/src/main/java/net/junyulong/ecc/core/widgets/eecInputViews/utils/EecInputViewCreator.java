package net.junyulong.ecc.core.widgets.eecInputViews.utils;

import android.content.Context;

import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewBridge;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.button.EecButton;
import net.junyulong.ecc.core.widgets.eecInputViews.button.EecButtonConfig;

public class EecInputViewCreator {

    public static EecInputViewInterface createNewButtonOverlay(Context context, EecButtonConfig config, EecInputViewBridge bridge) {
        return new EecButton(context, config, bridge);
    }

    public static EecInputViewInterface createNewButtonOverlay(Context context, EecInputViewBridge bridge) {
        return new EecButton(context, new EecButtonConfig(), bridge);
    }
}
