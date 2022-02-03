package net.junyulong.ecc.core.threads;

public abstract class EecBaseLoopThread implements EecThread {

    private EecImplThread mThread;

    private final int sleepTime;

    public EecBaseLoopThread(int sleep) {
        this.sleepTime = sleep;
    }

    @Override
    public boolean startThread() {
        if (mThread != null)
            return false;
        mThread = new EecImplThread();
        mThread.start();
        return true;
    }

    @Override
    public void destroyThread() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }

    @Override
    public boolean pauseThread() {
        if (mThread != null) {
            mThread.pause = true;
            return true;
        } else
            return false;
    }

    @Override
    public boolean continueThread() {
        if (mThread != null) {
            mThread.pause = false;
            return true;
        } else
            return false;
    }

    abstract public void loop();

    private class EecImplThread extends Thread {

        private boolean pause = false;

        @Override
        public void run() {
            while (!isInterrupted()) {
                if (!pause) {
                    EecBaseLoopThread.this.loop();
                    try {
                        sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage());
                    }
                }
            }
        }
    }
}
