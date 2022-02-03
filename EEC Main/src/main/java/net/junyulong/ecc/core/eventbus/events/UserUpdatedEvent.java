package net.junyulong.ecc.core.eventbus.events;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// 用户发生更新
public class UserUpdatedEvent extends BaseEvent {

    @Override
    public EventType getEventType() {
        return EventType.UserUpdated;
    }

    @Override
    public int getEventFlag() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
