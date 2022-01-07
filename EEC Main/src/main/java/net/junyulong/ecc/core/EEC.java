package net.junyulong.ecc.core;

import android.widget.Toast;

import net.junyulong.ecc.core.configuration.ConfigWriter;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.managers.EecInputManager;
import net.junyulong.ecc.core.managers.EecLocalManager;
import net.junyulong.ecc.core.managers.EecResourcesManager;
import net.junyulong.ecc.core.managers.EecWindowsManager;

public class EEC {

    private static EEC Eec;

    private ConfigWriter ConfigW;

    private final EecContext mContext;

    private EecInputManager inputManager;

    private EecLocalManager localManager;

    private EecWindowsManager windowsManager;

    private EecResourcesManager resourcesManager;

    private EEC(EecContext context) {
        this.mContext = context;
        this.attachManagers();
        Toast.makeText(mContext.getActivity(), LocalStrings.Success, Toast.LENGTH_SHORT).show();
    }

    public static EEC attach(EecContext context) {
        if (Eec == null) {
            Eec = new EEC(context);
        }
        return Eec;
    }

    public static EEC getInstance() {
        return Eec;
    }

    public static void detach() {
        if (Eec != null)
            Eec.innerDetach();
    }

    public EecInputManager getEecInputManager() {
        return this.inputManager;
    }

    public EecLocalManager getEecLocalManager() {
        return this.localManager;
    }

    public EecWindowsManager getEecWindowManager() {
        return this.windowsManager;
    }

    public EecResourcesManager getEecResourcesManager() {
        return this.resourcesManager;
    }

    private void innerDetach() {
        // 卸载管理器
        inputManager.detach();
        localManager.detach();
        resourcesManager.detach();
        windowsManager.detach();

        // TODO: 保存配置文件

        // 移除对象
        inputManager = null;
        localManager = null;
        windowsManager = null;
        resourcesManager = null;
        ConfigW = null;
        Eec = null;
    }

    private void attachManagers() {
        // 初始化配置信息
        ConfigW = new ConfigWriter();

        // 初始化管理器
        inputManager = new EecInputManager(ConfigW.getReader(), mContext.getXServerView().getXServerFacade());
        localManager = new EecLocalManager(ConfigW.getReader());
        windowsManager = new EecWindowsManager(mContext.getActivity().getResources().getDisplayMetrics());
        resourcesManager = new EecResourcesManager();

        // 启动管理器
        inputManager.attach();
        localManager.attach();
        windowsManager.attach();
        resourcesManager.attach();
    }

}
