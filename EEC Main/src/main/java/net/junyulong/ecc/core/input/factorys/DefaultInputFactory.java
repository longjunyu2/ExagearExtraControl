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

package net.junyulong.ecc.core.input.factorys;

import com.eltechs.axs.xserver.ViewFacade;

import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.input.events.EecGestureEvent;
import net.junyulong.ecc.core.input.events.EecInputEvent;
import net.junyulong.ecc.core.input.events.EecKeyEvent;
import net.junyulong.ecc.core.input.events.EecMixedEvent;
import net.junyulong.ecc.core.input.events.EecPointerEvent;
import net.junyulong.ecc.core.input.events.EecWheelEvent;
import net.junyulong.ecc.core.threads.EecThread;

import java.util.ArrayList;

public class DefaultInputFactory implements EecInputFactory {

    private final ViewFacade viewFacade;

    public DefaultInputFactory(ViewFacade viewFacade) {
        this.viewFacade = viewFacade;
    }

    @Override
    public void doInputEvent(EecInputEvent event, EecThread thread) {
        thread.pauseThread();
        dispatchEvent(event);
        thread.continueThread();
    }

    private void dispatchEvent(EecInputEvent event) {
        switch (event.getEventType()) {
            case EecKeyEvent:
                doKeyEvent((EecKeyEvent) event);
                break;
            case EecPointerEvent:
                doPointerEvent((EecPointerEvent) event);
                break;
            case EecWheelEvent:
                doWheelEvent((EecWheelEvent) event);
                break;
            case EecGestureEvent:
                doGestureEvent((EecGestureEvent) event);
                break;
            case EecMixedEvent:
                new Thread(() -> {
                    ArrayList<EecInputEvent> events = new ArrayList<>(((EecMixedEvent) event).getEvents());
                    for (EecInputEvent ev : events)
                        dispatchEvent(ev);
                }).start();
                break;
            default:
                throw new EecException("Unknown input event type: " + event.getEventType().name());
        }
    }

    private void doKeyEvent(EecKeyEvent event) {
        switch (event.keyType) {
            case Press:
                viewFacade.injectKeyPress((byte) event.keyCode);
                break;
            case Release:
                viewFacade.injectKeyRelease((byte) event.keyCode);
                break;
            case Press_Release:
                if (event.keyCode >= 0 && event.keyCode <= 3) {
                    viewFacade.injectPointerButtonPress((byte) event.keyCode);
                    viewFacade.injectPointerButtonRelease((byte) event.keyCode);
                    break;
                }
                viewFacade.injectKeyPress((byte) event.keyCode);
                viewFacade.injectKeyRelease((byte) event.keyCode);
                break;
        }
    }

    private void doPointerEvent(EecPointerEvent event) {
        switch (event.pointerType) {
            case Absolute:
                viewFacade.injectPointerMove(event.x, event.y);
                break;
            case Relative:
                viewFacade.injectPointerDelta(event.x, event.y);
                break;
        }
    }

    private void doWheelEvent(EecWheelEvent event) {
        switch (event.wheelType) {
            case WheelUp:
                viewFacade.injectPointerWheelUp(event.delta);
                break;
            case WheelDown:
                viewFacade.injectPointerWheelDown(event.delta);
                break;
        }
    }

    private void doGestureEvent(EecGestureEvent event) {
        // TODO: 完成手势映射功能
    }
}
