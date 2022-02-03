package net.junyulong.ecc.core.input.events;

import net.junyulong.ecc.core.input.events.type.EecKeyType;

public class EecKeyEvent extends EecInputEvent {

    private static final String TAG = "EecKeyEvent";

    public final EecKeyType keyType;

    public final int keyCode;

    public EecKeyEvent(EecKeyType types, int code) {
        this.keyType = types;
        this.keyCode = code;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public EventType getEventType() {
        return EventType.EecKeyEvent;
    }

    @Override
    public String getEventInfo() {
        return super.getEventInfo() +
                " [Tag] " + TAG +
                " [Type] " + EecKeyType.getName(keyType) +
                " [Info] keyCode: " + keyCode;
    }

}
