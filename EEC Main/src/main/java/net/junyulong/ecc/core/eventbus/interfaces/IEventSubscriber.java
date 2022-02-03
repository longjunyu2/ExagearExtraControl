package net.junyulong.ecc.core.eventbus.interfaces;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;

public interface IEventSubscriber {

    boolean onReceiveEvent(BaseEvent event);

}
