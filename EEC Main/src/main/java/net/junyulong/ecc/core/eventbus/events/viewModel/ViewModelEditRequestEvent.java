package net.junyulong.ecc.core.eventbus.events.viewModel;

import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;

// 控件需要编辑
public class ViewModelEditRequestEvent extends ViewModelBaseEvent {

    public ViewModelEditRequestEvent(EecInputViewChildInterface childInterface) {
        super(childInterface);
    }

    @Override
    public EventType getEventType() {
        return EventType.ViewModelEditRequest;
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
