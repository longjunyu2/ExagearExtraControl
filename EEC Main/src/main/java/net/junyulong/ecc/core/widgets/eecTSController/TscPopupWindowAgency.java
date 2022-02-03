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

package net.junyulong.ecc.core.widgets.eecTSController;

import net.junyulong.ecc.core.model.layout.EecLayoutModelWrapper;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewStatus;

public interface TscPopupWindowAgency {

    // 启动
    void attach();

    // 卸载
    void detach();

    // 获取布局封装实例
    EecLayoutModelWrapper getLayoutWrapper();

    // 获取全局状态
    EecInputViewStatus getGlobalStatus();

    // 设定全局状态
    void setGlobalStatus(EecInputViewStatus status);

    // 添加控件菜单是否显示
    boolean isAddViewMenuShowing();

    // 设定添加控件菜单可见性
    void showAddViewMenu(boolean show);

    // 设定全部二级菜单可见性
    void showAllSecondMenu(boolean show);

    // 图层管理菜单是否显示
    boolean isLayerManagerMenuShowing();

    // 设定图层管理菜单可见性
    void showLayerManagerMenu(boolean show);

    // 主菜单是否显示
    boolean isMainMenuShowing();

    // 设定主菜单可见性
    void showMainMenu(boolean show);

    // 设定全局窗口可见性
    void showEecMenu(boolean show);

    // 设定Fps窗口可见性
    void showFpsMonitorWindow(boolean show);

    // Fps窗口是否可见
    boolean isFpsMonitorWindowShowing();

    // 注册控件
    boolean registerView(EecInputViewModel model);

}
