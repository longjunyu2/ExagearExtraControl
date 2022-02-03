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

package net.junyulong.ecc.core.input.events.type;

public enum EecKeyType {
    Press,
    Release,
    Press_Release;

    public static final String PressName = "Press";

    public static final String ReleaseName = "Release";

    public static final String PressReleaseName = "Press_Release";

    public static String getName(EecKeyType types) {
        switch (types) {
            case Press:
                return PressName;
            case Release:
                return ReleaseName;
            case Press_Release:
                return PressReleaseName;
            default:
                return null;
        }
    }

    public static EecKeyType getType(String typeName) {
        switch (typeName) {
            case PressName:
                return EecKeyType.Press;
            case ReleaseName:
                return EecKeyType.Release;
            case PressReleaseName:
                return EecKeyType.Press_Release;
            default:
                return null;
        }
    }
}
