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

import net.junyulong.ecc.core.input.events.type.EecKeyType;

public class EecKeyEvent extends EecInputEvent {

    private static final String TAG = "EecKeyEvent";

    public final EecKeyType keyType;

    public final int keyCode;

    public EecKeyEvent(EecKeyType types, int code) {
        this.keyType = types;
        this.keyCode = code;
    }

    @Override
    public String getEventId() {
        return TAG;
    }

    @Override
    public EventType getEventType() {
        return EventType.EecKeyEvent;
    }

    @Override
    public String getEventInfo() {
        return super.getEventInfo() +
                " [Tag] " + TAG +
                " [Type] " + EecKeyType.getName(keyType) +
                " [Info] keyCode: " + keyCode;
    }

}
