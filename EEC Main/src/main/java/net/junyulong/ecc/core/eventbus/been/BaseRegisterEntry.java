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

package net.junyulong.ecc.core.eventbus.been;

import net.junyulong.ecc.core.eventbus.enums.EventPriority;
import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.eventbus.interfaces.IEventSubscriber;

public class BaseRegisterEntry {

    private final IEventSubscriber mEventSubscriber;
    private final EventPriority mPriority;
    private final EventType[] mEventTypes;

    public BaseRegisterEntry(IEventSubscriber subscriber, EventPriority priority, EventType[] types) {
        this.mEventSubscriber = subscriber;
        this.mPriority = priority;
        this.mEventTypes = types;
    }

    public IEventSubscriber getEventSubscriber() {
        return this.mEventSubscriber;
    }

    public EventPriority getEventPriority() {
        return this.mPriority;
    }

    public EventType[] getEventTypes() {
        return this.mEventTypes;
    }


}
