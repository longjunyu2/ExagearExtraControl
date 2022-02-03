package net.junyulong.ecc.core.eventbus.events;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.model.layout.layer.EecLayerModel;

import static net.junyulong.ecc.core.eventbus.enums.EventType.LayerRemoved;

// 控件层被移除
public class LayerRemovedEvent extends BaseEvent {

    private final EecLayerModel layerModel;

    public LayerRemovedEvent(EecLayerModel model) {
        this.layerModel = model;
    }

    @Override
    public EventType getEventType() {
        return LayerRemoved;
    }

    @Override
    public int getEventFlag() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }

    public EecLayerModel getLayer() {
        return this.layerModel;
    }
}
