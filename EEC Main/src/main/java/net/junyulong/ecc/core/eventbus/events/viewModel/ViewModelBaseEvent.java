/*
 * Copyright 2022 Junyu Long
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
