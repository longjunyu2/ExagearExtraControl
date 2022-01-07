package net.junyulong.ecc.core.managers;

import com.eltechs.axs.xserver.ViewFacade;

import net.junyulong.ecc.core.configuration.ConfigReader;
import net.junyulong.ecc.core.input.events.EecInputEvent;
import net.junyulong.ecc.core.input.factorys.DefaultInputFactory;
import net.junyulong.ecc.core.threads.EecInputThread;

public class EecInputManager {

    private EecInputThread mThread;

    private boolean enabled = false;

    private final ViewFacade viewFacade;

    public EecInputManager(ConfigReader configuration, ViewFacade viewFacade) {
        this.viewFacade = viewFacade;
    }

    public void doEvent(EecInputEvent event) {
        mThread.put(event);
    }

    public boolean attach() {
        if (enabled)
            return false;
        else {
            this.mThread = new EecInputThread(new DefaultInputFactory(viewFacade));
            mThread.startThread();
            enabled = true;
            return true;
        }
    }

    public void detach() {
        mThread.destroyThread();
        mThread = null;
    }
}
