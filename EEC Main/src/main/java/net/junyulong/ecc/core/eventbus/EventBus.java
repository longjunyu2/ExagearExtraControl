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

package net.junyulong.ecc.core.eventbus;

import android.util.Log;

import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.been.BaseRegisterEntry;
import net.junyulong.ecc.core.eventbus.enums.EventFlags;
import net.junyulong.ecc.core.eventbus.enums.EventPriority;
import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.eventbus.exceptions.SubscriberNotFoundException;
import net.junyulong.ecc.core.eventbus.interfaces.IEventSubscriber;
import net.junyulong.ecc.core.threads.EecBaseLoopThread;
import net.junyulong.ecc.core.universal.ElementQueue;

import java.util.ArrayList;

public class EventBus {

    private final static int LOOP_PERIOD = 1; // 1ms

    private final static String TAG = "EventBus";

    private final ArrayList<BaseRegisterEntry> mRegisterEntries;
    private static EventBus defaultInstance;
    private final ElementQueue<BaseEvent> mEventQueue;
    private final EecBaseLoopThread mLoopThread;

    private EventBus() {
        this.mRegisterEntries = new ArrayList<>();
        this.mEventQueue = new ElementQueue<>(10);
        this.mLoopThread = new EecBaseLoopThread(LOOP_PERIOD) {
            @Override
            public void loop() {
                Log.d("fuck", "ok");
                dispenseEvent();
            }
        };
        mLoopThread.startThread();
    }

    // 静态单例，获取当前的事件总线
    public static EventBus getDefaultEventBus() {
        if (defaultInstance == null)
            defaultInstance = new EventBus();
        return defaultInstance;
    }

    // 注册订阅者
    public boolean register(IEventSubscriber subscriber, EventPriority priority, EventType... types) {
        for (BaseRegisterEntry entry : mRegisterEntries) {
            if (entry.getEventSubscriber() == subscriber) {
                return false;
            }
        }
        mRegisterEntries.add(new BaseRegisterEntry(subscriber, priority, types));
        return true;
    }

    public boolean register(IEventSubscriber subscriber, EventType... eventTypes) {
        return register(subscriber, EventPriority.NORMAL, eventTypes);
    }

    // 反注册订阅者
    public boolean unregister(IEventSubscriber subscriber) {
        for (BaseRegisterEntry entry : mRegisterEntries) {
            if (entry.getEventSubscriber() == subscriber) {
                mRegisterEntries.remove(entry);
                return true;
            }
        }
        return false;
    }

    // 获取已注册的订阅者的优先级
    public EventPriority getPriority(IEventSubscriber subscriber) {
        for (BaseRegisterEntry entry : mRegisterEntries) {
            if (entry.getEventSubscriber() == subscriber)
                return entry.getEventPriority();
        }
        throw new SubscriberNotFoundException();
    }

    // 向队列添加事件
    public boolean post(BaseEvent event) {
        return this.mEventQueue.put(event);
    }

    // 销毁事件总线
    public static void destroy() {
        if (defaultInstance != null) {
            defaultInstance.mLoopThread.destroyThread();
            defaultInstance = null;
        }
    }

    // 内部方法: 向特定订阅者发送事件
    private boolean execute(IEventSubscriber subscriber, BaseEvent event) {
        return subscriber.onReceiveEvent(event);
    }

    // 内部方法: 循环分发事件
    private void dispenseEvent() {
        BaseEvent currentEvent = mEventQueue.get();
        if (currentEvent == null) {
            Log.v("fuck", "skip");
            return;
        }
        Log.e(TAG, "Get event. Type: " + currentEvent.getEventType().name());
        boolean success = false;
        for (BaseRegisterEntry entry : mRegisterEntries) {
            for (EventType type : entry.getEventTypes()) {
                if (type == currentEvent.getEventType() && execute(entry.getEventSubscriber(), currentEvent)) {
                    success = true;
                    // 设定事件被消费
                    currentEvent.setReceived(true);
                    Log.e(TAG, "Poll finished.");
                    break;
                }
            }
            if (success && (currentEvent.getEventFlag() & EventFlags.EVENT_FLAG_POLLING) != EventFlags.EVENT_FLAG_POLLING) {
                break;
            }
        }
        if (!currentEvent.isReceived())
            Log.e(TAG, "No Fit Subscriber, the event has been abandon.");
    }

}
