package net.junyulong.ecc.core.input.events;

import net.junyulong.ecc.core.input.events.type.EecPointerType;

public class EecPointerEvent extends EecInputEvent {

    private static final String TAG = "EecPointerEvent";

    public final int x;

    public final int y;

    public final EecPointerType pointerType;

    public EecPointerEvent(EecPointerType types, int x, int y) {
        this.x = x;
        this.y = y;
        this.pointerType = types;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public EventType getEventType() {
        return EventType.EecPointerEvent;
    }

    @Override
    public String getEventInfo() {
        return super.getEventInfo() +
                " [Tag] " + TAG +
                " [Type] " + EecPointerType.getName(pointerType) +
                " [Info] x: " + x + " y: " + y;
    }


}
