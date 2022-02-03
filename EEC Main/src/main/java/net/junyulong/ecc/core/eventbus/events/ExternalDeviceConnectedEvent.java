package net.junyulong.ecc.core.eventbus.events;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// 外部设备连接(OTG)
public class ExternalDeviceConnectedEvent extends BaseEvent {

    @Override
    public EventType getEventType() {
        return EventType.ExternalDeviceConnected;
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
