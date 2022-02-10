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

package net.junyulong.ecc.core.widgets.popupWindow.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.resource.EecColorResources;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.universal.ViewUtils;
import net.junyulong.ecc.core.widgets.KeyboardView;
import net.junyulong.ecc.core.widgets.popupWindow.TscPopupWindow;

public class KeyboardViewTestPopupWindow extends TscPopupWindow {
    private final Context context;
    private float xPos;
    private float yPos;
    private final static int ButtonSizeDp = 20;
    private final int buttonSizePx;
    private final static int MarginSizeDp = 5;
    private final int marginSizePx;
    private final static int TextSizeSp = 13;

    public KeyboardViewTestPopupWindow(Context context) {
        super(context);
        this.context = context;
        this.buttonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(ButtonSizeDp);
        this.marginSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginSizeDp);
        setContentView(createView());
        this.setWidth(ViewUtils.getUndisplayedViewSize(getContentView())[0]);
        this.setHeight(ViewUtils.getUndisplayedViewSize(getContentView())[1]);
        this.setFocusable(true);
        this.setClippingEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.setTouchModal(false);
        }
    }

    private View createView() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(createWindowHeader());
        layout.addView(createKeyboardView());
        return layout;
    }

    @SuppressLint("ClickableViewAccessibility")
    private View createWindowHeader() {
        LinearLayout externalLayout = new LinearLayout(context);
        externalLayout.setOrientation(LinearLayout.VERTICAL);
        externalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        externalLayout.setOnTouchListener(new View.OnTouchListener() {
            private float downX;
            private float downY;
            private float lastX;
            private float lastY;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getRawX();
                        downY = event.getRawY();
                        lastX = xPos;
                        lastY = yPos;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        xPos = event.getRawX() - downX + lastX;
                        yPos = event.getRawY() - downY + lastY;
                        innerWindowUpdate();
                        break;
                }
                return false;
            }
        });

        externalLayout.setLongClickable(true);

        LinearLayout innerLayout = new LinearLayout(context);
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        externalLayout.addView(innerLayout);

        TextView textView = new TextView(context);
        textView.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
        textView.setTextSize(TextSizeSp);
        textView.setLayoutParams(createParamsWithMargin(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        textView.setText(LocalStrings.Keyboard_View_Test);
        innerLayout.addView(textView);

        View intervalView = new View(context);
        LinearLayout.LayoutParams v2Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                1);
        v2Params.weight = 1;
        intervalView.setLayoutParams(v2Params);
        innerLayout.addView(intervalView);

        AppCompatButton dragButton = new AppCompatButton(context);
        dragButton.setLayoutParams(createParamsWithMargin(buttonSizePx, buttonSizePx,
                Gravity.CENTER_VERTICAL));
        dragButton.setBackground(EEC.getInstance().getEecResourcesManager().
                getDrawable(EecImageResources.ic_drag_move_white_24dp));
        dragButton.setClickable(false);
        dragButton.setFocusable(false);
        innerLayout.addView(dragButton);

        externalLayout.addView(createSplitLine(
                Color.parseColor(EecColorResources.ColorHintText)));

        return externalLayout;
    }

    private View createKeyboardView() {
        KeyboardView keyboardView = new KeyboardView(context,
                EEC.getInstance().getEecLocalManager().getLocalKeyMap(), 1);
        keyboardView.setOnKeySelectionChangedListener((keyNames, selected) -> {
            Toast.makeText(context, "Key: " + keyNames.name() + " Selected: " + selected, Toast.LENGTH_SHORT).show();
        });
        return keyboardView;
    }

    private LinearLayout.LayoutParams createParamsWithMargin(int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,
                height);
        layoutParams.setMargins(marginSizePx, marginSizePx, marginSizePx, marginSizePx);
        return layoutParams;
    }

    private LinearLayout.LayoutParams createParamsWithMargin(int width, int height, int gravity) {
        LinearLayout.LayoutParams layoutParams = createParamsWithMargin(width, height);
        layoutParams.gravity = gravity;
        return layoutParams;
    }

    private View createSplitLine(int color) {
        View splitLineView = new View(context);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
        viewParams.setMargins(0, marginSizePx, marginSizePx, marginSizePx);
        splitLineView.setLayoutParams(viewParams);
        splitLineView.setBackgroundColor(color);
        return splitLineView;
    }

    private void innerWindowUpdate() {
        update((int) xPos, (int) yPos, getWidth(), getHeight());
    }
}
