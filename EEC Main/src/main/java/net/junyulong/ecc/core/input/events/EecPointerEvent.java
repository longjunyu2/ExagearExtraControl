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

import net.junyulong.ecc.core.input.events.type.EecPointerType;

public class EecPointerEvent extends EecInputEvent {

    private static final String TAG = "EecPointerEvent";

    public final int x;

    public final int y;

    public final EecPointerType pointerType;

    public EecPointerEvent(EecPointerType types, int x, int y) {
        this.x = x;
        this.y = y;
        this.pointerType = types;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public EventType getEventType() {
        return EventType.EecPointerEvent;
    }

    @Override
    public String getEventInfo() {
        return super.getEventInfo() +
                " [Tag] " + TAG +
                " [Type] " + EecPointerType.getName(pointerType) +
                " [Info] x: " + x + " y: " + y;
    }


}
