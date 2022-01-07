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
