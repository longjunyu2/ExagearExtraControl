package net.junyulong.ecc.core.input.events;

import net.junyulong.ecc.core.universal.Event;

import java.util.List;

public class EecMixedEvent extends EecInputEvent {

    private static final String TAG = "EecMixedEvent";

    public final List<EecInputEvent> list;

    public EecMixedEvent(List<EecInputEvent> list) {
        this.list = list;
    }

    public List<EecInputEvent> getEvents() {
        return this.list;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public EventType getEventType() {
        return EventType.EecMixedEvent;
    }

    @Override
    public String getEventInfo() {
        StringBuilder stb = new StringBuilder();
        for (Event event : list)
            stb.append(event.getEventInfo()).append("\n");
        return stb.toString();
    }
}
