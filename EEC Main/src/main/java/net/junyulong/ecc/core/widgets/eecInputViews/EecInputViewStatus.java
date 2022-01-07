package net.junyulong.ecc.core.widgets.eecInputViews;

public enum EecInputViewStatus {
    Applied,
    Edited,
    Selected,
    Error;

    public final static String AppliedName = "Applied";

    public final static String EditedName = "Edited";

    public final static String SelectedName = "SelectedName";

    public final static String ErrorName = "Error";

    public EecInputViewStatus getStatus(String name) {
        switch (name) {
            case AppliedName:
                return Applied;
            case EditedName:
                return Edited;
            case SelectedName:
                return Selected;
            case ErrorName:
                return Error;
            default:
                return null;
        }
    }

    public String getName(EecInputViewStatus status) {
        switch (status) {
            case Applied:
                return AppliedName;
            case Edited:
                return EditedName;
            case Selected:
                return SelectedName;
            case Error:
                return ErrorName;
            default:
                return null;
        }
    }
}
