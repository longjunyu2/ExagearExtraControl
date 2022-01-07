package net.junyulong.ecc.core.input.factorys;

import net.junyulong.ecc.core.input.events.EecInputEvent;
import net.junyulong.ecc.core.threads.EecThread;

public interface EecInputFactory {
    void doInputEvent(EecInputEvent event, EecThread thread);
}
