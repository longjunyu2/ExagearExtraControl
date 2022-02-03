package net.junyulong.ecc.core;

import com.eltechs.axs.activities.StartupActivity;

public class ExagearUtils {

    // 关闭AXS程序
    public static void shutdownAXSApplication() {
        StartupActivity.shutdownAXSApplication();
    }

    // 设置XServer视图水平拉伸
    public static void setHorizontalStretchEnabled(boolean enabled) {
        EEC.getInstance().getContext().getXServerView().setHorizontalStretchEnabled(enabled);
    }
}
