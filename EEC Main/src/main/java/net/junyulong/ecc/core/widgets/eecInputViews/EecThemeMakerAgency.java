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

package net.junyulong.ecc.core.widgets.eecInputViews;

import net.junyulong.ecc.core.model.layout.EecInputViewKey;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;

public class EecThemeMakerAgency {

    // 获取控件文字大小信息
    public int getTextSizeSp(EecInputViewModel model) {
        return 13;
    }

    // 获取控件文字信息
    public String getText(EecInputViewModel model) {
        return model.getInfo().get(EecInputViewKey.Key_Name);
    }

    // 获取背景

}
