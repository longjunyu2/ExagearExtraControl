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
