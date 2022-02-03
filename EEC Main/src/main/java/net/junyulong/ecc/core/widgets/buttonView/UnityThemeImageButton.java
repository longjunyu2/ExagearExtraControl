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

package net.junyulong.ecc.core.widgets.buttonView;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.TypedValue;
import android.widget.ImageView;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.resource.EecImageResources;

public class UnityThemeImageButton extends AppCompatImageButton {
    public UnityThemeImageButton(Context context) {
        super(context);
        // 解析属性 selectableItemBackground
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        // 设置背景
        setBackgroundResource(typedValue.resourceId);
        // 自动调整边距
        setAdjustViewBounds(true);
        // 依照控件宽高缩放
        setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public UnityThemeImageButton(Context context, EecImageResources res) {
        this(context);
        setImageDrawable(EEC.getInstance().getEecResourcesManager().getDrawable(res));
    }
}
