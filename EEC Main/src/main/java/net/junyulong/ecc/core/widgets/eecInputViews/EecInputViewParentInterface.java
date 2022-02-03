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

import android.support.annotation.NonNull;

import net.junyulong.ecc.core.model.layout.EecLayoutModelWrapper;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;

import java.util.ArrayList;

public interface EecInputViewParentInterface {

    // 注册控件
    boolean registerInputView(@NonNull EecInputViewChildInterface overlay);

    boolean registerInputView(@NonNull EecInputViewModel model);

    // 注销控件
    boolean unregisterInputView(@NonNull EecInputViewChildInterface overlay);

    boolean unregisterInputView(@NonNull String inputViewId);

    // 清除全部控件
    void removeAllInputViews();

    // 获取全部控件
    ArrayList<EecInputViewChildInterface> getEecInputViews();

    // 通过Id获取一个控件
    EecInputViewChildInterface getEecInputViewById(String id);

    // 获取控件数量
    int getCounts();

    // 设置全局状态
    void setGlobalStatus(EecInputViewStatus status);

    // 获取全局状态
    EecInputViewStatus getGlobalStatus();

    // 获取控件部署器
    EecInputViewDeployer getDeployer();

    // 获取主题生成器
    EecThemeMakerAgency getThemeMarker();

    // 获取当前的Layout的Wrapper
    EecLayoutModelWrapper getLayoutModelWrapper();
}
