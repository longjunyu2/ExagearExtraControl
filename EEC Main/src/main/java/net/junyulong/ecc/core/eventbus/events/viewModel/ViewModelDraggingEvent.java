package net.junyulong.ecc.core.eventbus.events.viewModel;

import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;

import static net.junyulong.ecc.core.eventbus.enums.EventType.ViewModelDragging;

// 控件正在被拖拽
public class ViewModelDraggingEvent extends ViewModelBaseEvent {

    public ViewModelDraggingEvent(EecInputViewChildInterface childInterface) {
        super(childInterface);
    }

    @Override
    public EventType getEventType() {
        return ViewModelDragging;
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
