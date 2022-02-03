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
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.widgets.eecTSController.TscPopupWindowAgency;

public class TscEecGlobalPopupWindow extends TscPopupWindow {

    private final TscPopupWindowAgency windowAgency;

    private final Context mContext;

    // 图标大小
    private final static int IconSizeDp = 35;

    public TscEecGlobalPopupWindow(Context context, TscPopupWindowAgency agency) {
        super(context);
        this.windowAgency = agency;
        this.mContext = context;
        // 创建视图
        setContentView(createView());
        // 透明背景
        this.setBackgroundDrawable(null);
    }

    private View createView() {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(createButton(v -> {
            // 判断主菜单是否正在显示
            if (windowAgency.isMainMenuShowing()) {
                // 若主菜单正在显示，则关闭全部的菜单
                windowAgency.showMainMenu(false);
                windowAgency.showAllSecondMenu(false);
            } else
                // 若主菜单不再显示，则显示主菜单
                windowAgency.showMainMenu(true);
        }));
        return layout;
    }

    // 创建按钮
    private View createButton(View.OnClickListener listener) {
        AppCompatButton button = new AppCompatButton(mContext);
        int pxSize = EEC.getInstance().getEecWindowManager().getPxFromDp(IconSizeDp);
        button.setLayoutParams(new LinearLayout.LayoutParams(pxSize, pxSize));
        button.setBackgroundDrawable(EEC.getInstance().getEecResourcesManager().getDrawable(EecImageResources.round_build_white_24dp));
        button.setOnClickListener(listener);
        return button;
    }
}
