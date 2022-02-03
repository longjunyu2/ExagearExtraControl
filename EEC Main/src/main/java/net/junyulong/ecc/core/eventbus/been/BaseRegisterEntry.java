package net.junyulong.ecc.core.eventbus.been;

import net.junyulong.ecc.core.eventbus.enums.EventPriority;
import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.eventbus.interfaces.IEventSubscriber;

public class BaseRegisterEntry {

    private final IEventSubscriber mEventSubscriber;
    private final EventPriority mPriority;
    private final EventType[] mEventTypes;

    public BaseRegisterEntry(IEventSubscriber subscriber, EventPriority priority, EventType[] types) {
        this.mEventSubscriber = subscriber;
        this.mPriority = priority;
        this.mEventTypes = types;
    }

    public IEventSubscriber getEventSubscriber() {
        return this.mEventSubscriber;
    }

    public EventPriority getEventPriority() {
        return this.mPriority;
    }

    public EventType[] getEventTypes() {
        return this.mEventTypes;
    }


}
