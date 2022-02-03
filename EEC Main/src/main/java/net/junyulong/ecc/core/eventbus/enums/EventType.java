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

package net.junyulong.ecc.core.eventbus.enums;

public enum EventType {
    // 全局事件
    ExternalDeviceConnected("ExternalDeviceConnected"),
    GameLaunched("GameLaunched"),
    SettingUpdated("SettingUpdated"),
    UserUpdated("UserUpdated"),
    AppSwitchForeground("AppSwitchForeground"),
    AppSwitchBackground("AppSwitchBackground"),
    // 层事件
    LayerListUpdated("LayerListUpdated"),
    LayerVisibilityChanged("LayerVisibilityChanged"),
    LayerRemoved("LayerRemoved"),
    // 控件事件
    ViewModelUpdated("ViewModelUpdated"),
    ViewModelEditRequest("ViewModelEditRequest"),
    ViewModelDragging("ViewModelDragging"),
    ViewModelDraggedFinished("ViewModelDraggedFinished");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
