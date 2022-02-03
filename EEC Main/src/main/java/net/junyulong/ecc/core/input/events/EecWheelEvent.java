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

package net.junyulong.ecc.core.input.events;

import net.junyulong.ecc.core.input.events.type.EecWheelType;

public class EecWheelEvent extends EecInputEvent {

    private static final String TAG = "EecWheelEvent";

    public final int delta;

    public final EecWheelType wheelType;

    public EecWheelEvent(EecWheelType types, int delta) {
        this.delta = delta;
        this.wheelType = types;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public EventType getEventType() {
        return EventType.EecWheelEvent;
    }

    @Override
    public String getEventInfo() {
        return super.getEventInfo() +
                " [Tag] " + TAG +
                " [Type] " + EecWheelType.getName(wheelType) +
                " [Info] delta: " + delta;
    }
}
