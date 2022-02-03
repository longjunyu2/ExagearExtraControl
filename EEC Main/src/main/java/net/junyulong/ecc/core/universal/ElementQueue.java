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
