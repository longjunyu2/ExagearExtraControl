package net.junyulong.ecc.core.widgets.popupWindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.widgets.buttonView.UnityThemeImageButton;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewStatus;
import net.junyulong.ecc.core.widgets.eecTSController.TscPopupWindowAgency;

public class TscMainMenuPopupWindow extends TscPopupWindow {

    // TSC代理对象
    private final TscPopupWindowAgency windowAgency;

    private final Context mContext;

    // 图标大小
    private final static int IconSizeDp = 35;

    public TscMainMenuPopupWindow(Context context, TscPopupWindowAgency agency) {
        super(context);
        this.windowAgency = agency;
        this.mContext = context;
        // 创建视图
        setContentView(createView());
    }

    private View createView() {
        LinearLayout mainToolbarLayout = new LinearLayout(mContext);
        mainToolbarLayout.setOrientation(LinearLayout.VERTICAL);
        mainToolbarLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // “配置” 按钮
        mainToolbarLayout.addView(createButton(EecImageResources.round_settings_white_24dp,
                v -> {
                    // TODO: 设计配置菜单
                    windowAgency.getLayoutWrapper().toLog();
                }));
        // “编辑模式” 按钮
        mainToolbarLayout.addView(createButton(EecImageResources.baseline_code_off_white_24dp,
                v -> {
                    EecInputViewStatus status = windowAgency.getGlobalStatus();
                    switch (status) {
                        // 若当前是应用模式，则切换为编辑模式
                        case Applied:
                            windowAgency.setGlobalStatus(EecInputViewStatus.Edited);
                            ((UnityThemeImageButton) v).setImageDrawable(EEC.getInstance().getEecResourcesManager().
                                    getDrawable(EecImageResources.baseline_code_white_24dp));
                            break;
                        // 若当前是编辑模式，则切换为应用模式
                        case Edited:
                            windowAgency.setGlobalStatus(EecInputViewStatus.Applied);
                            ((UnityThemeImageButton) v).setImageDrawable(EEC.getInstance().getEecResourcesManager().
                                    getDrawable(EecImageResources.baseline_code_off_white_24dp));
                            if (windowAgency.isAddViewMenuShowing())
                                windowAgency.showAddViewMenu(false);
                            break;
                        default:
                            throw new EecException("Unsupported global status : " + EecInputViewStatus.getName(status));
                    }
                }));
        // “添加控件” 按钮
        mainToolbarLayout.addView(createButton(EecImageResources.outline_add_box_white_24dp,
                v -> {
                    // 判断当前是否是编辑模式
                    if (windowAgency.getGlobalStatus() != EecInputViewStatus.Edited)
                        // 不是编辑模式，给出提示
                        createToast(LocalStrings.Please_Enable_Edit_Mode);
                    else {
                        // 编辑模式下，判断添加控件的菜单是否已经显示
                        if (windowAgency.isAddViewMenuShowing())
                            // 若已经显示，则关闭添加控件的菜单
                            windowAgency.showAddViewMenu(false);
                        else {
                            // 若未显示，则显示添加控件的菜单
                            windowAgency.showAllSecondMenu(false);
                            windowAgency.showAddViewMenu(true);
                        }
                    }
                }));
        // “切换Layer” 按钮
        mainToolbarLayout.addView(createButton(EecImageResources.outline_view_carousel_white_24dp,
                v -> {
                    // 判断图层管理菜单是否已经显示
                    if (windowAgency.isLayerManagerMenuShowing())
                        // 若图层管理菜单已经显示，则关闭它
                        windowAgency.showLayerManagerMenu(false);
                    else {
                        // 若还未显示，则显示它
                        windowAgency.showAllSecondMenu(false);
                        windowAgency.showLayerManagerMenu(true);
                    }
                }));
        return mainToolbarLayout;
    }

    // 创建工具栏按钮
    private View createButton(EecImageResources resources, View.OnClickListener listener) {
        int buttonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(IconSizeDp);
        int marginPx = EEC.getInstance().getEecWindowManager().getPxFromDp(5);

        UnityThemeImageButton button = new UnityThemeImageButton(mContext, resources);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(buttonSizePx, buttonSizePx);
        buttonParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        button.setLayoutParams(buttonParams);
        button.setOnClickListener(listener);
        return button;
    }

    // 创建一个Toast窗口
    private void createToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }
}
