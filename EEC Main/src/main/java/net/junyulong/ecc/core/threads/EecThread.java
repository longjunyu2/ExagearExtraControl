package net.junyulong.ecc.core.threads;

public interface EecThread {

    // 开启进程
    boolean startThread();

    // 销毁进程
    void destroyThread();

    // 暂停进程
    boolean pauseThread();

    // 继续进程
    boolean continueThread();

    // 循环方法
    void loop();
}
