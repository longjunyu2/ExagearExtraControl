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

public enum EecInputViewStatus {
    Applied,
    Edited,
    Error;

    public final static String AppliedName = "Applied";

    public final static String EditedName = "Edited";

    public final static String ErrorName = "Error";

    public static EecInputViewStatus getStatus(String name) {
        switch (name) {
            case AppliedName:
                return Applied;
            case EditedName:
                return Edited;
            case ErrorName:
                return Error;
            default:
                return null;
        }
    }

    public static String getName(EecInputViewStatus status) {
        switch (status) {
            case Applied:
                return AppliedName;
            case Edited:
                return EditedName;
            case Error:
                return ErrorName;
            default:
                return null;
        }
    }
}
