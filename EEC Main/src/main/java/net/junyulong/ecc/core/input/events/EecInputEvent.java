package net.junyulong.ecc.core.input.events;

import net.junyulong.ecc.core.universal.BaseEvent;

public abstract class EecInputEvent extends BaseEvent {

    private final static String TAG = "EecInputEvent";

    @Override
    public String getEventId() {
        return TAG;
    }

}
