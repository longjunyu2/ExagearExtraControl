package net.junyulong.ecc.core.eventbus.events;


import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// App切换到后台
public class AppSwitchBackgroundEvent extends BaseEvent {
    @Override
    public EventType getEventType() {
        return EventType.AppSwitchBackground;
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
