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

public enum EecInputViewEdgeType {
    // 正常控件的边框
    ViewNormal,
    // 错误控件的边框
    ViewError,
    // 被选择的控件的边框
    ViewSelected,
    // 正在移动的控件
    ViewMoving,
    // 控件的直接父控件
    ViewDirectParent,
    // 控件的间接父控件
    ViewIndirectParent,
    // 控件的直接子控件
    ViewDirectChildren,
    // 控件的间接子控件
    ViewIndirectChildren
}
