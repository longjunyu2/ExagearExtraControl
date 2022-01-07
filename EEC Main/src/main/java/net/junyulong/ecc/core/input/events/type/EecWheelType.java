package net.junyulong.ecc.core.input.events.type;

public enum EecWheelType {
    WheelUp,
    WheelDown;

    public static final String WheelUpName = "WheelUp";

    public static final String WheelDownName = "WheelDown";

    public static String getName(EecWheelType types) {
        switch (types) {
            case WheelUp:
                return WheelUpName;
            case WheelDown:
                return WheelDownName;
            default:
                return null;
        }
    }

    public static EecWheelType getType(String typeName) {
        switch (typeName) {
            case WheelUpName:
                return EecWheelType.WheelUp;
            case WheelDownName:
                return EecWheelType.WheelDown;
            default:
                return null;
        }
    }

}
