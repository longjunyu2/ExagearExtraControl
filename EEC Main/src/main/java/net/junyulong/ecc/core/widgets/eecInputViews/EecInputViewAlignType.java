package net.junyulong.ecc.core.widgets.eecInputViews;

public enum EecInputViewAlignType {

    Top_to_Top_of,
    Top_to_Bottom_of,
    Bottom_to_Bottom_of,
    Bottom_to_Top_of,
    Left_to_Left_of,
    Left_to_Right_of,
    Right_to_Right_of,
    Right_to_Left_of;

    public final static String TtTo = "Top_to_Top_of";

    public final static String TtBo = "Top_to_Bottom_of";

    public final static String BtBo = "Bottom_to_Bottom_of";

    public final static String BtTo = "Bottom_to_Top_of";

    public final static String LtLo = "Left_to_Left_of";

    public final static String LtRo = "Left_to_Right_of";

    public final static String RtRo = "Right_to_Right_of";

    public final static String RtLo = "Right_to_Left_of";

    public static String getName(EecInputViewAlignType type) {
        switch (type) {
            case Top_to_Top_of:
                return TtTo;
            case Top_to_Bottom_of:
                return TtBo;
            case Bottom_to_Top_of:
                return BtTo;
            case Bottom_to_Bottom_of:
                return BtBo;
            case Left_to_Left_of:
                return LtLo;
            case Left_to_Right_of:
                return LtRo;
            case Right_to_Right_of:
                return RtRo;
            case Right_to_Left_of:
                return RtLo;
            default:
                return null;
        }
    }

    public static EecInputViewAlignType getType(String name) {
        switch (name) {
            case TtTo:
                return Top_to_Top_of;
            case TtBo:
                return Top_to_Bottom_of;
            case BtBo:
                return Bottom_to_Bottom_of;
            case BtTo:
                return Bottom_to_Top_of;
            case LtLo:
                return Left_to_Left_of;
            case LtRo:
                return Left_to_Right_of;
            case RtLo:
                return Right_to_Left_of;
            case RtRo:
                return Right_to_Right_of;
            default:
                return null;
        }
    }

}
