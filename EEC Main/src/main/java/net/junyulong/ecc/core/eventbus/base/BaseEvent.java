package net.junyulong.ecc.core.eventbus.base;

import net.junyulong.ecc.core.eventbus.enums.EventType;

public abstract class BaseEvent extends net.junyulong.ecc.core.universal.BaseEvent {

    private final static String TAG = "EventBus-Event";

    private boolean received = false;

    // 获取事件类型
    public abstract EventType getEventType();

    // 获取事件Flag
    public abstract int getEventFlag();

    // 获取事件消息
    public abstract String getMsg();

    // 设置状态
    public synchronized void setReceived(boolean received) {
        this.received = received;
    }

    // 获取状态
    public boolean isReceived() {
        return this.received;
    }

    @Override
    public String getEventId() {
        return TAG;
    }
}
