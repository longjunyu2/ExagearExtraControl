package net.junyulong.ecc.core.input.events.type;

public enum EecPointerType {
    Absolute,
    Relative;

    public static final String AbsoluteName = "absolute";

    public static final String RelativeName = "relative";

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
