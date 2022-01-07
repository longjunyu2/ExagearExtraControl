package net.junyulong.ecc.core.threads;

public interface EecThread {

    boolean startThread();

    void destroyThread();

    boolean pauseThread();

    boolean continueThread();

    void loop();
}
