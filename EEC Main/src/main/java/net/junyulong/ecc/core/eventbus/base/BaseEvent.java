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

package net.junyulong.ecc.core.eventbus.base;

import net.junyulong.ecc.core.eventbus.enums.EventType;

public abstract class BaseEvent extends net.junyulong.ecc.core.universal.BaseEvent {

    private final static String TAG = "EventBus-Event";

    private boolean received = false;

    // 获取事件类型
    public abstract EventType getEventType();

    // 获取事件Flag
    public abstract int getEventFlag();

    // 获取事件消息
    public abstract String getMsg();

    // 设置状态
    public synchronized void setReceived(boolean received) {
        this.received = received;
    }

    // 获取状态
    public boolean isReceived() {
        return this.received;
    }

    @Override
    public String getEventId() {
        return TAG;
    }
}
