package net.junyulong.ecc.core.eventbus.events;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// 游戏启动
public class GameLaunchedEvent extends BaseEvent {

    @Override
    public EventType getEventType() {
        return EventType.GameLaunched;
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
