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

import com.eltechs.axs.activities.StartupActivity;

public class ExagearUtils {
    public final static String TAG_CONTROLS_INFO = "CONTROLS_INFO";

    // 关闭AXS程序
    public static void shutdownAXSApplication() {
        StartupActivity.shutdownAXSApplication();
    }

    // 设置XServer视图水平拉伸
    public static void setHorizontalStretchEnabled(boolean enabled) {
        EEC.getInstance().getContext().getXServerView().setHorizontalStretchEnabled(enabled);
    }

    // 显示Controls信息对话框
    public static void showControlsInfoDialog() {
        EEC.getInstance().getContext().getControls().createInfoDialog().show(
                EEC.getInstance().getContext().getActivity().getSupportFragmentManager(), TAG_CONTROLS_INFO);
    }

    // 终止XServer服务
    public static void shutdownXServerService() {
        EEC.getInstance().getContext().getXServerView().getXServerFacade().injectKeyType((byte) 0);
    }
}
