package net.junyulong.ecc.core.input.events;

import net.junyulong.ecc.core.input.events.type.EecGestureType;

public class EecGestureEvent extends EecInputEvent {

    private static final String TAG = "EecGestureEvent";

    public final EecGestureType gestureType;

    public EecGestureEvent(EecGestureType types) {
        this.gestureType = types;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public EventType getEventType() {
        return EventType.EecGestureEvent;
    }

    @Override
    public String getEventInfo() {
        return super.getEventInfo() +
                " [Tag] " + TAG +
                " [Type] " + EecGestureType.getName(gestureType);
    }
}
