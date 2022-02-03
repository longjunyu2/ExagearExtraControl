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

package com.eltechs.ed.controls;

import com.eltechs.axs.activities.XServerDisplayActivityInterfaceOverlay;

import net.junyulong.ecc.uiOverlay.EecUiOverlay;

import java.util.Collections;
import java.util.List;

public class EEControls extends Controls {

    @Override
    public XServerDisplayActivityInterfaceOverlay create() {
        return new EecUiOverlay(this);
    }

    @Override
    public String getId() {
        return "eec";
    }

    @Override
    public List<ControlsInfoElem> getInfoElems() {
        return Collections.singletonList(
                new ControlsInfoElem(0, "Exagear Extra Controls", "This controls mode is used to customize the operation layout.")
        );
    }

    @Override
    public String getName() {
        return "EEC";
    }
}
