package net.junyulong.ecc.core.eventbus.events.viewModel;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;

// 控件相关事件
public abstract class ViewModelBaseEvent extends BaseEvent {

    private final EecInputViewChildInterface childInterface;

    public ViewModelBaseEvent(EecInputViewChildInterface childInterface) {
        this.childInterface = childInterface;
    }

    public EecInputViewChildInterface getViewInterface() {
        return this.childInterface;
    }
}
