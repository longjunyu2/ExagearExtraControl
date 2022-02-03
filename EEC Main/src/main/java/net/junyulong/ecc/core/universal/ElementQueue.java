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

package net.junyulong.ecc.core.universal;

import java.util.LinkedList;

public class ElementQueue<T> {

    private final static int DEFAULT_ELEMENT_COUNT = 20;
    private final static int MAX_ELEMENT_COUNT = 100;
    private final static int MIN_ELEMENT_COUNT = 1;
    private final int maxElementCount;
    private final LinkedList<T> mQueue;

    public ElementQueue(int maxQueueSize) {
        this.maxElementCount = (maxQueueSize >= MIN_ELEMENT_COUNT && maxQueueSize <= MAX_ELEMENT_COUNT)
                ? maxQueueSize : DEFAULT_ELEMENT_COUNT;
        this.mQueue = new LinkedList<>();
    }

    public ElementQueue() {
        this(DEFAULT_ELEMENT_COUNT);
    }

    // 获取队列大小
    public int getMaxElementCount() {
        return this.maxElementCount;
    }

    // 获取当前元素数量
    public int getCurrentElementCount() {
        return mQueue.size();
    }

    // 队列是否满
    public boolean isQueueFull() {
        return getMaxElementCount() == getCurrentElementCount();
    }

    // 向队列添加元素
    public synchronized boolean put(T element) {
        return (!isQueueFull() && mQueue.offer(element));
    }

    // 从队列取出元素
    public synchronized T get() {
        return mQueue.poll();
    }

    // 清空队列
    public void clear() {
        mQueue.clear();
    }

}
