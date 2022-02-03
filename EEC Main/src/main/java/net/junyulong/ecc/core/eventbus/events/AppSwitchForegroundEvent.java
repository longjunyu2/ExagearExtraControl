package net.junyulong.ecc.core.eventbus.events;

import android.support.annotation.Nullable;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// App切换到前台
public class AppSwitchForegroundEvent extends BaseEvent {

    private final String msg;

    public AppSwitchForegroundEvent(@Nullable String msg) {
        this.msg = msg == null ? "" : msg;
    }

    @Override
    public EventType getEventType() {
        return EventType.AppSwitchForeground;
    }

    @Override
    public int getEventFlag() {
        return 0;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
