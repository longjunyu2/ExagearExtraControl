package net.junyulong.ecc.core.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewEditableInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewParentInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewType;
import net.junyulong.ecc.core.widgets.eecInputViews.button.EecButton;
import net.junyulong.ecc.core.widgets.eecInputViews.joystick.EecJoystick;

public class EecButtonConfigDialog extends EecBaseDialog implements EecInputViewEditableInterface {

    private final EecInputViewParentInterface mParent;

    private final Context mContext;

    private EecInputViewInterface targetInterface;

    private EecInputViewType overlayType;

    private final Type dialogType;

    public EecButtonConfigDialog(Context context, EecInputViewParentInterface parent, Type type) {
        super(context);
        mParent = parent;
        this.mContext = context;
        this.dialogType = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void editView(EecInputViewInterface inputViewInterface) {
        targetInterface = inputViewInterface;
        if (inputViewInterface instanceof EecButton)
            this.overlayType = EecInputViewType.EecButton;
        else if (inputViewInterface instanceof EecJoystick)
            this.overlayType = EecInputViewType.EecJoystick;
        this.show();
    }

    public enum Type {
        Edit,
        Create
    }

}
