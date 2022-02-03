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

public enum EecInputViewType {
    EecButton,
    EecJoystick;

    public final static String EecButtonName = "EecButton";

    public final static String EecJoystickName = "EecJoystick";

    public static EecInputViewType getType(String name) {
        switch (name) {
            case EecButtonName:
                return EecButton;
            case EecJoystickName:
                return EecJoystick;
            default:
                return null;
        }
    }

    public static String getName(EecInputViewType type) {
        switch (type) {
            case EecButton:
                return EecButtonName;
            case EecJoystick:
                return EecJoystickName;
            default:
                return null;
        }
    }

}
