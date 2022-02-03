package net.junyulong.ecc.core.eventbus.events.viewModel;

import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;

// 控件信息被更新
public class ViewModelUpdatedEvent extends ViewModelBaseEvent {

    public ViewModelUpdatedEvent(EecInputViewChildInterface childInterface) {
        super(childInterface);
    }

    @Override
    public EventType getEventType() {
        return EventType.ViewModelUpdated;
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
