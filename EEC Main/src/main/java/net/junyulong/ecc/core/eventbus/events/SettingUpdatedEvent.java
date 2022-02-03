package net.junyulong.ecc.core.eventbus.events;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// 设置发生更新
public class SettingUpdatedEvent extends BaseEvent {

    @Override
    public EventType getEventType() {
        return EventType.SettingUpdated;
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
