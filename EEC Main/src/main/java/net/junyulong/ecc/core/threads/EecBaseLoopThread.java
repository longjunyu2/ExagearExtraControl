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
                    }
                }
            }
        }
    }
}
