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

public enum EecGestureType {
    OneFinger_ShortTap,
    OneFinger_LongTap,
    OneFinger_ShortTap_Move,
    OneFinger_LongTap_Move,
    TwoFingers_ShortTap,
    TwoFingers_LongTap,
    TwoFingers_ShortTap_Move,
    TwoFingers_LongTap_Move,
    ThreeFingers_Tap,
    FourFingers_Tap;

    public static final String OneFingerShortTapName = "OneFinger_ShortTap";

    public static final String OneFingerLongTapName = "OneFinger_LongTap";

    public static final String OneFingerShortTapMoveName = "OneFinger_ShortTap_Move";

    public static final String OneFingerLongTapMoveName = "OneFinger_LongTap_Move";

    public static final String TwoFingersShortTapName = "TwoFingers_ShortTap";

    public static final String TwoFingersLongTapName = "TwoFingers_LongTap";

    public static final String TwoFingersShortTapMoveName = "TwoFingers_ShortTap_Move";

    public static final String TwoFingersLongTapMoveName = "TwoFingers_LongTap_Move";

    public static final String ThreeFingersTapName = "ThreeFingers_Tap";

    public static final String FourFingersTapName = "FourFingers_Tap";

    public static String getName(EecGestureType types) {
        switch (types) {
            case OneFinger_ShortTap:
                return OneFingerShortTapName;
            case OneFinger_ShortTap_Move:
                return OneFingerShortTapMoveName;
            case OneFinger_LongTap:
                return OneFingerLongTapName;
            case OneFinger_LongTap_Move:
                return OneFingerLongTapMoveName;
            case TwoFingers_ShortTap:
                return TwoFingersShortTapName;
            case TwoFingers_ShortTap_Move:
                return TwoFingersShortTapMoveName;
            case TwoFingers_LongTap:
                return TwoFingersLongTapName;
            case TwoFingers_LongTap_Move:
                return TwoFingersLongTapMoveName;
            case ThreeFingers_Tap:
                return ThreeFingersTapName;
            case FourFingers_Tap:
                return FourFingersTapName;
            default:
                return null;
        }
    }

    public static EecGestureType getType(String typeName) {
        switch (typeName) {
            case OneFingerShortTapName:
                return EecGestureType.OneFinger_ShortTap;
            case OneFingerShortTapMoveName:
                return EecGestureType.OneFinger_ShortTap_Move;
            case OneFingerLongTapName:
                return EecGestureType.OneFinger_LongTap;
            case OneFingerLongTapMoveName:
                return EecGestureType.OneFinger_LongTap_Move;
            case TwoFingersShortTapName:
                return EecGestureType.TwoFingers_ShortTap;
            case TwoFingersShortTapMoveName:
                return EecGestureType.TwoFingers_ShortTap_Move;
            case TwoFingersLongTapName:
                return EecGestureType.TwoFingers_LongTap;
            case TwoFingersLongTapMoveName:
                return EecGestureType.TwoFingers_LongTap_Move;
            case ThreeFingersTapName:
                return EecGestureType.ThreeFingers_Tap;
            case FourFingersTapName:
                return EecGestureType.FourFingers_Tap;
            default:
                return null;
        }
    }

}
