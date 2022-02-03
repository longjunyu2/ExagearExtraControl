package net.junyulong.ecc.core.eventbus.events;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// 控件层列表发生更新
public class LayerListUpdatedEvent extends BaseEvent {
    @Override
    public EventType getEventType() {
        return EventType.LayerListUpdated;
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
