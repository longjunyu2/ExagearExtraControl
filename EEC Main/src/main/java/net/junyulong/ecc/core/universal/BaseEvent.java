package net.junyulong.ecc.core.universal;

public abstract class BaseEvent implements Event {

    private final long createdTime;

    public BaseEvent() {
        this.createdTime = System.currentTimeMillis();
    }

    @Override
    public long getEventCreatedTime() {
        return createdTime;
    }

    @Override
    public String getEventInfo() {
        return "[CreatedTime] " + createdTime;
    }
}
