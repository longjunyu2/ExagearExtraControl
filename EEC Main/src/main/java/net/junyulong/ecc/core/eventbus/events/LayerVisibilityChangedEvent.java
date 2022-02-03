package net.junyulong.ecc.core.eventbus.events;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;

// 控件层的可见性发生改变
public class LayerVisibilityChangedEvent extends BaseEvent {

    private final String layerName;

    private final boolean visibility;

    public LayerVisibilityChangedEvent(String layerName, boolean visibility) {
        this.layerName = layerName;
        this.visibility = visibility;
    }

    public String getLayerName() {
        return this.layerName;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    @Override
    public EventType getEventType() {
        return EventType.LayerVisibilityChanged;
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
