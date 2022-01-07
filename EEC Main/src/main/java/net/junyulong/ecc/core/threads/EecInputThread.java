package net.junyulong.ecc.core.threads;

import net.junyulong.ecc.core.input.events.EecInputEvent;
import net.junyulong.ecc.core.input.factorys.EecInputFactory;
import net.junyulong.ecc.core.universal.ElementQueue;

public class EecInputThread extends EecBaseThread {

    private static final int DEFAULT_SLEEP_TIME = 1; // ms

    private static final int MAX_EVENT_COUNT = 50;

    private EecInputFactory mInputFactory;

    private ElementQueue<EecInputEvent> mQueue;

    private EecInputEvent currentEvent;

    public EecInputThread(int sleep, EecInputFactory inputFactory) {
        super(sleep);
        this.mInputFactory = inputFactory;
        this.mQueue = new ElementQueue<>(MAX_EVENT_COUNT);
    }

    public EecInputThread(EecInputFactory inputFactory) {
        this(DEFAULT_SLEEP_TIME, inputFactory);
    }

    public EecInputThread() {
        this(DEFAULT_SLEEP_TIME, null);
    }

    public void setInputFactory(EecInputFactory factory) {
        this.mInputFactory = factory;
    }

    @Override
    public void loop() {
        currentEvent = mQueue.get();
        if (currentEvent != null && mInputFactory != null)
            mInputFactory.doInputEvent(currentEvent, this);
    }

    public void put(EecInputEvent event) {
        mQueue.put(event);
    }

    @Override
    public void destroyThread() {
        super.destroyThread();
        this.mInputFactory = null;
        this.mQueue.clear();
        this.mQueue = null;
        this.currentEvent = null;
    }
}
