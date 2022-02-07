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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.input.events.EecKeyEvent;
import net.junyulong.ecc.core.input.events.type.EecKeyType;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.resource.EecColorResources;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.universal.ViewUtils;
import net.junyulong.ecc.core.widgets.buttonView.UnityThemeButton;
import net.junyulong.ecc.core.widgets.popupWindow.TscPopupWindow;

public class ScanCodePostTestPopupWindow extends TscPopupWindow {

    private final Context context;
    private float xPos;
    private float yPos;
    private final static int ButtonSizeDp = 20;
    private final int buttonSizePx;
    private final static int MarginSizePDp = 5;
    private final int marginSizePx;
    private final static int TextSizeSp = 13;
    private final static int WindowWidthDp = 100;

    public ScanCodePostTestPopupWindow(Context context) {
        super(context);
        this.context = context;
        this.buttonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(ButtonSizeDp);
        this.marginSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginSizePDp);
        int windowWidthPx = EEC.getInstance().getEecWindowManager().getPxFromDp(WindowWidthDp);
        setContentView(createView());
        this.setWidth(windowWidthPx);
        this.setHeight(ViewUtils.getUndisplayedViewSize(getContentView())[1]);
        this.setFocusable(true);
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
        layout.addView(createScanCodeReportView());
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
                        // 更新窗口位置
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
        textView.setText(LocalStrings.Scan_Code_Test);
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

    private View createScanCodeReportView() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText editScanCode = new EditText(context);
        editScanCode.setLayoutParams(createParamsWithMargin(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        editScanCode.setTextSize(TextSizeSp);
        editScanCode.setTextColor(Color.parseColor(EecColorResources.ColorItemText));
        editScanCode.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editScanCode.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        layout.addView(editScanCode);

        Button buttonPost = createButton(v -> {
            try {
                int code = Integer.parseInt(editScanCode.getText().toString());
                EEC.getInstance().getEecInputManager().doEvent(
                        new EecKeyEvent(EecKeyType.Press_Release, code));
                Toast.makeText(context, "Post Succeed: " + code, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Format Error", Toast.LENGTH_SHORT).show();
            }
        }, LocalStrings.Post);
        layout.addView(buttonPost);

        layout.addView(createButton(v -> {
            try {
                editScanCode.setText(String.valueOf(Integer.parseInt(editScanCode.getText().toString()) + 1));
                buttonPost.performClick();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Format Error", Toast.LENGTH_SHORT).show();
            }
        }, LocalStrings.Next));

        return layout;
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

    private UnityThemeButton createButton(View.OnClickListener clickListener, String text) {
        UnityThemeButton button = new UnityThemeButton(context);
        button.setTextSize(TextSizeSp);
        button.setText(text);
        LinearLayout.LayoutParams buttonParams = createParamsWithMargin(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.gravity = Gravity.CENTER;
        button.setLayoutParams(buttonParams);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
        button.setOnClickListener(clickListener);
        return button;
    }
}
