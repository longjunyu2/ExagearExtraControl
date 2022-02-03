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

package net.junyulong.ecc.core;

import com.eltechs.axs.activities.XServerDisplayActivity;
import com.eltechs.axs.widgets.viewOfXServer.ViewOfXServer;
import com.eltechs.ed.controls.EEControls;

import net.junyulong.ecc.uiOverlay.EecUiOverlay;

public class EecContext {

    private final EEControls controls;

    private final EecUiOverlay overlay;

    private final XServerDisplayActivity hostActivity;

    private final ViewOfXServer viewOfXServer;

    private EecContext(EEControls eeControls, EecUiOverlay eecUiOverlay,
                       XServerDisplayActivity hostActivity, ViewOfXServer viewOfXServer) {
        this.controls = eeControls;
        this.overlay = eecUiOverlay;
        this.hostActivity = hostActivity;
        this.viewOfXServer = viewOfXServer;
    }

    public static EecContext getContext(EEControls eeControls, EecUiOverlay eecUiOverlay,
                                        XServerDisplayActivity hostActivity, ViewOfXServer viewOfXServer) {
        return new EecContext(eeControls, eecUiOverlay, hostActivity, viewOfXServer);
    }

    public EEControls getControls() {
        return this.controls;
    }

    public EecUiOverlay getUiOverlay() {
        return this.overlay;
    }

    public XServerDisplayActivity getActivity() {
        return this.hostActivity;
    }

    public ViewOfXServer getXServerView() {
        return this.viewOfXServer;
    }
}
