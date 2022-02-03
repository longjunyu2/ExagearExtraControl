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

public enum EecPointerType {
    Absolute,
    Relative;

    public static final String AbsoluteName = "Absolute";

    public static final String RelativeName = "Relative";

    public static String getName(EecPointerType types) {
        switch (types) {
            case Absolute:
                return AbsoluteName;
            case Relative:
                return RelativeName;
            default:
                return null;
        }
    }

    public static EecPointerType getType(String typeName) {
        switch (typeName) {
            case AbsoluteName:
                return EecPointerType.Absolute;
            case RelativeName:
                return EecPointerType.Relative;
            default:
                return null;
        }
    }

}
