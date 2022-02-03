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

import android.support.v4.app.DialogFragment;

import com.eltechs.axs.activities.XServerDisplayActivityInterfaceOverlay;

import java.util.List;

public abstract class Controls {

    public static Controls find(String str) {
        // stub
        return null;
    }

    public static Controls getDefault() {
        // stub
        return null;
    }

    public static List<Controls> getList() {
        // stub
        return null;
    }

    public abstract XServerDisplayActivityInterfaceOverlay create();

    public DialogFragment createInfoDialog() {
        // stub
        return null;
    }

    public abstract String getId();

    public abstract List<ControlsInfoElem> getInfoElems();

    public int getInfoImage() {
        return 0;
    }

    public abstract String getName();

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        return stb.append(getId()).append(" (").append(getName()).append(")").toString();
    }
}
