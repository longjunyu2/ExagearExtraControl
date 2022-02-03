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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.adapter.LayerBeanAdapter;
import net.junyulong.ecc.core.errors.EecElementMaximumException;
import net.junyulong.ecc.core.errors.EecElementRepeatException;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.model.layout.layer.EecLayerModel;
import net.junyulong.ecc.core.resource.EecColorResources;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.widgets.buttonView.UnityThemeImageButton;
import net.junyulong.ecc.core.widgets.eecTSController.TscPopupWindowAgency;

public class TscLayerManagerPopupWindow extends TscPopupWindow {

    private final TscPopupWindowAgency windowAgency;

    private final Context mContext;

    // 边距大小
    private final static int MarginDp = 5;

    // 按钮大小
    private final static int ButtonSizeDp = 20;

    // 文字大小
    private final static int TextSizeSp = 13;

    // 窗口宽度
    private final static int WindowWidthDp = 230;

    // 适配器
    private LayerBeanAdapter mAdapter;

    public TscLayerManagerPopupWindow(Context context, TscPopupWindowAgency agency) {
        super(context);
        this.windowAgency = agency;
        this.mContext = context;
        // 创建视图
        setContentView(createView());
    }

    private View createView() {
        int marginPx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginDp);
        int buttonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(ButtonSizeDp);

        // 创建适配器
        mAdapter = new LayerBeanAdapter(mContext, windowAgency.getLayoutWrapper(), null);

        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout layout2 = new LinearLayout(mContext);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(layout2);

        // 标题文字
        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
        textView.setTextSize(TextSizeSp);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        textParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(textParams);
        textView.setText(LocalStrings.Main_Layer);
        layout2.addView(textView);

        View view2 = new View(mContext);
        LinearLayout.LayoutParams v2Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                1);
        v2Params.weight = 1;
        view2.setLayoutParams(v2Params);
        layout2.addView(view2);

        // 创建新图层按钮
        UnityThemeImageButton addButton = new UnityThemeImageButton(mContext,
                EecImageResources.baseline_add_white_24dp);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(buttonSizePx, buttonSizePx);
        buttonParams.gravity = Gravity.CENTER_VERTICAL;
        buttonParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        addButton.setLayoutParams(buttonParams);
        addButton.setOnClickListener(v -> new Object() {
            private int suffixNum = 0;

            public void addNewLayer() {
                // 尝试创建并添加新图层
                try {
                    String layerName = LocalStrings.Unnamed + (suffixNum == 0 ? "" : String.valueOf(suffixNum));
                    EecLayerModel model = new EecLayerModel();
                    model.setLayerName(layerName);
                    model.setShowing(true);
                    windowAgency.getLayoutWrapper().addLayer(model);
                    // 添加成功后更新适配器数据
                    mAdapter.notifyDataSetChanged();
                } catch (EecElementRepeatException e) {
                    // 若图层名称重复，则将后缀递增，进行递归，直到图层创建成功
                    e.printStackTrace();
                    suffixNum++;
                    addNewLayer();
                } catch (EecElementMaximumException e) {
                    // 若图层数量达到最大值，给出提醒
                    e.printStackTrace();
                    createToast(LocalStrings.Layer_Count_Maximum);
                }
            }
        }.addNewLayer());
        layout2.addView(addButton);

        View view = new View(mContext);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
        viewParams.setMargins(0, marginPx, marginPx, marginPx);
        view.setLayoutParams(viewParams);
        view.setBackgroundColor(Color.parseColor(EecColorResources.ColorHintText));
        layout.addView(view);

        // 图层列表视图
        ListView listView = new ListView(mContext);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                EEC.getInstance().getEecWindowManager().getPxFromDp(WindowWidthDp),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(mAdapter);
        layout.addView(listView);

        return layout;
    }

    // 创建一个Toast窗口
    private void createToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }
}
