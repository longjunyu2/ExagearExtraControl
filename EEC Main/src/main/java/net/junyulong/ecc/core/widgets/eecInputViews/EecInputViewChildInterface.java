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

import android.view.View;

import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;

public interface EecInputViewChildInterface {

    // 更新视图
    void viewUpdate(EecInputViewUpdateType type);

    // 编辑视图
    void viewEdit();

    // 获取Model
    EecInputViewModel getModel();

    // 获取View
    View getEecView();

    // 设置边框颜色
    void setEdgeType(EecInputViewEdgeType edgeType);
}
