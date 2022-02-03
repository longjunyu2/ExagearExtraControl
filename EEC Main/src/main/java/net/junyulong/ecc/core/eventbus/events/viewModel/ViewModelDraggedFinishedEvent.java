package net.junyulong.ecc.core.eventbus.events.viewModel;

import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;

import static net.junyulong.ecc.core.eventbus.enums.EventType.ViewModelDraggedFinished;

// 控件拖拽完成
public class ViewModelDraggedFinishedEvent extends ViewModelBaseEvent {

    public ViewModelDraggedFinishedEvent(EecInputViewChildInterface childInterface) {
        super(childInterface);
    }

    @Override
    public EventType getEventType() {
        return ViewModelDraggedFinished;
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
