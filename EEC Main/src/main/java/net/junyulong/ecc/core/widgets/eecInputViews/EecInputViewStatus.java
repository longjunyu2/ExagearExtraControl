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
