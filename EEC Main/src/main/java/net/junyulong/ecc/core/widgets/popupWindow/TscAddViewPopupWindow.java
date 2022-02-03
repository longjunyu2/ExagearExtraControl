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

package net.junyulong.ecc.core.widgets.popupWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.model.layout.EecLayoutModelWrapper;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;
import net.junyulong.ecc.core.resource.EecColorResources;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewAlignBindType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewType;
import net.junyulong.ecc.core.widgets.eecTSController.EecTSControllerView;
import net.junyulong.ecc.core.widgets.eecTSController.TscPopupWindowAgency;

import static android.view.View.TEXT_ALIGNMENT_VIEW_START;

public class TscAddViewPopupWindow extends TscPopupWindow {

    private final TscPopupWindowAgency windowAgency;

    private final Context mContext;

    // 视图边距大小
    private final static int MarginDp = 5;

    // 文字大小
    private final static int TextSizeSp = 13;

    // 图标大小
    private final static int IconSizeDp = 35;

    // 滚动视图对象
    private ScrollView mScrollView;

    public TscAddViewPopupWindow(Context context, TscPopupWindowAgency agency) {
        super(context);
        this.windowAgency = agency;
        this.mContext = context;
        setContentView(createView());
    }

    private View createView() {
        int marginPx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginDp);

        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
        textView.setTextSize(TextSizeSp);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        textParams.gravity = Gravity.START;
        textView.setLayoutParams(textParams);
        textView.setText(LocalStrings.Add_Widget);
        layout.addView(textView);

        View view = new View(mContext);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
        viewParams.setMargins(0, marginPx, marginPx, marginPx);
        view.setLayoutParams(viewParams);
        view.setBackgroundColor(Color.parseColor(EecColorResources.ColorHintText));
        layout.addView(view);

        mScrollView = new ScrollView(mContext);
        mScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(mScrollView);

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // 添加 “键盘按键”
        linearLayout.addView(createButton(EecImageResources.ic_keyboard_white_24dp,
                LocalStrings.Virtual_Key, LocalStrings.Quick_Create_Keyboard_Button, v -> {
                    EecLayoutModelWrapper.EecLayerModelWrapper layerModelWrapper =
                            windowAgency.getLayoutWrapper().getDefaultLayer();
                    if (layerModelWrapper == null)
                        Toast.makeText(mContext, LocalStrings.Layer_Empty, Toast.LENGTH_SHORT).show();
                    else {
                        EecInputViewModel model = new EecInputViewModel(EecInputViewType.getName(EecInputViewType.EecButton));
                        model.getBind().vertical = new EecInputViewModel.Bind.BindInfo(EecTSControllerView.ParentId,
                                EecInputViewAlignBindType.getName(EecInputViewAlignBindType.Top_to_Top_of), 0);
                        model.getBind().horizontal = new EecInputViewModel.Bind.BindInfo(EecTSControllerView.ParentId,
                                EecInputViewAlignBindType.getName(EecInputViewAlignBindType.Left_to_Left_of), 0);
                        model.setParam(new EecInputViewModel.Param(5, 5));
                        if (layerModelWrapper.getViewModels().size() != 0) {
                            model.getBind().vertical.referenceId = windowAgency.getLayoutWrapper().getDefaultLayer().getViewModels().get(0).getId();
                            model.getBind().horizontal.referenceId = windowAgency.getLayoutWrapper().getDefaultLayer().getViewModels().get(0).getId();
                            model.getBind().vertical.offset = 10;
                            model.getBind().horizontal.offset = 10;
                        }
                        if (windowAgency.registerView(model)) {
                            layerModelWrapper.addViewModel(model);
                            Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
        // 添加 “鼠标按钮”
        linearLayout.addView(createButton(EecImageResources.ic_mouse_white_24dp,
                LocalStrings.Virtual_Mouse, LocalStrings.Quick_Create_Mouse_Button, v -> {
                    // TODO: 完成鼠标映射按钮
                }));
        // 添加 “虚拟摇杆”
        linearLayout.addView(createButton(EecImageResources.ic_game_pad_white_24dp,
                LocalStrings.Virtual_Joystick, LocalStrings.Quick_Create_Virtual_Joystick, v -> {
                    // TODO: 完成虚拟摇杆控件
                }));
        // 添加 “高级宏按钮”
        linearLayout.addView(createButton(EecImageResources.round_build_white_24dp,
                LocalStrings.Advanced_Button, LocalStrings.Create_Advanced_Button, v -> {
                    // TODO: 完成高级宏按钮
                }));
        // 添加 “高级宏摇杆”
        linearLayout.addView(createButton(EecImageResources.ic_game_pad_pro_white_24dp,
                LocalStrings.Advanced_Joystick, LocalStrings.Create_Advanced_Virtual_Joystick, v -> {
                    // TODO: 完成支持高级宏的虚拟摇杆控件
                }));
        // 添加 “特殊控制按钮”
        linearLayout.addView(createButton(EecImageResources.outline_eye_on_white_24dp,
                LocalStrings.Special_Button, LocalStrings.Create_Special_Button, v -> {
                    // TODO: 完成特殊控制按钮
                }));
        // 添加 “日志控件”
        linearLayout.addView(createButton(EecImageResources.outline_eye_off_white_24dp,
                LocalStrings.Log_Button, LocalStrings.Create_Log_Button, v -> {
                    // TODO: 完成日志控件
                }));
        mScrollView.addView(linearLayout);

        return layout;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 当窗口关闭时，滚动视图回到顶部
        mScrollView.scrollTo(0, 0);
    }

    // 创建设置栏按钮
    private View createButton(EecImageResources resources, String text,
                              String des, View.OnClickListener listener) {
        int pxSize = EEC.getInstance().getEecWindowManager().getPxFromDp(IconSizeDp);
        int marginPx = EEC.getInstance().getEecWindowManager().getPxFromDp(5);
        int textSizeSp = 13;

        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(0, 0, marginPx * 2, 0);
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        layout.setBackgroundResource(typedValue.resourceId);

        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(pxSize, pxSize);
        iconParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        iconParams.gravity = Gravity.CENTER;
        View icon = new View(mContext);
        icon.setLayoutParams(iconParams);
        icon.setBackground(EEC.getInstance().getEecResourcesManager().getDrawable(resources));

        LinearLayout textLayout = new LinearLayout(mContext);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        TextView textView = new TextView(mContext);
        textView.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
        textView.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
        textView.setTextSize(textSizeSp);
        textView.setText(text);
        textView.setLayoutParams(textParams);

        LinearLayout.LayoutParams desParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        desParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        TextView desView = new TextView(mContext);
        desView.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
        desView.setTextColor(Color.parseColor(EecColorResources.ColorHintText));
        desView.setTextSize(textSizeSp);
        desView.setText(des);
        desView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        desView.setLayoutParams(desParams);

        textLayout.addView(textView);
        textLayout.addView(desView);

        layout.addView(icon);
        layout.addView(textLayout);
        layout.setOnClickListener(listener);

        return layout;
    }

}
