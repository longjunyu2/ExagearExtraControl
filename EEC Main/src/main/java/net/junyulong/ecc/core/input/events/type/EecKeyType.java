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
