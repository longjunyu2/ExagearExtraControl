package net.junyulong.ecc.core.input.events;

import net.junyulong.ecc.core.input.events.type.EecWheelType;

public class EecWheelEvent extends EecInputEvent {

    private static final String TAG = "EecWheelEvent";

    public final int delta;

    public final EecWheelType wheelType;

    public EecWheelEvent(EecWheelType types, int delta) {
        this.delta = delta;
        this.wheelType = types;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public String getEventInfo() {
        return super.getEventInfo() +
                " [Tag] " + TAG +
                " [Type] " + EecWheelType.getName(wheelType) +
                " [Info] delta: " + delta;
    }
}
