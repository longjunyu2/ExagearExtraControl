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

package net.junyulong.ecc.core.managers;

import com.eltechs.axs.xserver.ViewFacade;

import net.junyulong.ecc.core.configuration.ConfigReader;
import net.junyulong.ecc.core.input.events.EecInputEvent;
import net.junyulong.ecc.core.input.factorys.DefaultInputFactory;
import net.junyulong.ecc.core.threads.EecInputThread;

public class EecInputManager {

    private EecInputThread mThread;

    private boolean enabled = false;

    private final ViewFacade viewFacade;

    public EecInputManager(ConfigReader configuration, ViewFacade viewFacade) {
        this.viewFacade = viewFacade;
    }

    public void doEvent(EecInputEvent event) {
        mThread.put(event);
    }

    public boolean attach() {
        if (enabled)
            return false;
        else {
            this.mThread = new EecInputThread(new DefaultInputFactory(viewFacade));
            mThread.startThread();
            enabled = true;
            return true;
        }
    }

    public void detach() {
        mThread.destroyThread();
        mThread = null;
    }
}
