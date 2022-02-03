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

public class ControlsInfoElem {
    public final String mDescrText;

    public final String mHeaderText;

    public final int mImageRes;

    public ControlsInfoElem(int i, String str1, String str2) {
        this.mDescrText = str2;
        this.mHeaderText = str1;
        this.mImageRes = i;
    }
}
