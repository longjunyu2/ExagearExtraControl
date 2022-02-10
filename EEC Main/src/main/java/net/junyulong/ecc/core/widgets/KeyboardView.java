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

package net.junyulong.ecc.core.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.input.EecKeyMap;
import net.junyulong.ecc.core.input.XServerKeyNames;

@SuppressLint("ViewConstructor")
public class KeyboardView extends FrameLayout {
    private final Context context;
    private final EecKeyMap keyMap;
    private KeySelectionChangedListener keySelectionChangedListener;

    private final static float MIN_KEY_CELL_SIZE_DP = 30;
    private final static int SCALE_TYPE_WIDTH = -1;
    private final static int SCALE_TYPE_HEIGHT = -2;

    private final KeyboardParams keyboardParams;

    public KeyboardView(Context context, EecKeyMap keyMap, float scale) {
        super(context);
        this.context = context;
        this.keyMap = keyMap;
        if (scale < 1)
            throw new EecException("Unsupported scale size: " + scale);
        keyboardParams = new KeyboardParams();
        keyboardParams.setKeyCellSize(
                (int) (EEC.getInstance().getEecWindowManager().getPxFromDp(MIN_KEY_CELL_SIZE_DP) * scale)
        );
        init();
    }

    public KeyboardView(Context context, EecKeyMap keyMap, int scaleType, int size) {
        this(context, keyMap, (scaleType == SCALE_TYPE_WIDTH) ? size / 18.75f : size / 7f);
    }

    public void setOnKeySelectionChangedListener(KeySelectionChangedListener listener) {
        this.keySelectionChangedListener = listener;
    }

    private void init() {
        if (getChildCount() != 0)
            removeAllViews();
        for (XServerKeyNames keyNames : XServerKeyNames.values()) {
            if (keyNames.enabled()) {
                try {
                    addView(new KeyboardButton(keyboardParams.getKeyParams(keyNames)));
                } catch (UnsupportedKeyException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        params.width = keyboardParams.getKeyboardViewWidth();
        params.height = keyboardParams.getKeyboardViewHeight();
        super.setLayoutParams(params);
    }

    private class KeyboardButton extends AppCompatButton {
        private XServerKeyNames keyNames;
        private boolean selected;

        public KeyboardButton(KeyParams keyParams) {
            super(context);
            this.setLayoutParams(new FrameLayout.LayoutParams(keyParams.width, keyParams.height));
            this.setX(keyParams.x);
            this.setY(keyParams.y);
            this.setClickable(true);
            this.setOnClickListener(v -> {
                KeyboardView.this.keySelectionChangedListener.onKeySelectionChanged(keyNames, !selected);
                selected = !selected;
            });
            // TODO: 使用FrameLayout重写组合控件
        }
    }

    public interface KeySelectionChangedListener {
        void onKeySelectionChanged(XServerKeyNames keyNames, boolean selected);
    }

    private static class KeyboardParams {
        public int symbolTextSizeSp;
        public int keyCellSizePx;
        public int boundMarginSizePx;
        public int lineMarginSizePx;
        public int keyFunctionMarginSizePx;
        public int backspaceKeyWidthPx;
        public int tabKeyWidthPx;
        public int capslockKeyWidthPx;
        public int enterKeyWidthPx;
        public int shiftLKeyWidthPx;
        public int shiftRKeyWidthPx;
        public int ctrlKeyWidthPx;
        public int spaceKeyWidthPx;

        public void setKeyCellSize(int pixel) {
            this.keyCellSizePx = pixel;
            init();
        }

        private void init() {
            symbolTextSizeSp = 13;
            boundMarginSizePx = keyCellSizePx / 4;
            lineMarginSizePx = keyCellSizePx / 2;
            keyFunctionMarginSizePx = keyCellSizePx / 2;
            backspaceKeyWidthPx = keyCellSizePx * 2;
            tabKeyWidthPx = (int) (keyCellSizePx * 1.5f);
            capslockKeyWidthPx = (int) (keyCellSizePx * 1.75f);
            enterKeyWidthPx = (int) (keyCellSizePx * 2.25f);
            shiftLKeyWidthPx = (int) (keyCellSizePx * 2.25f);
            shiftRKeyWidthPx = (int) (keyCellSizePx * 2.75f);
            ctrlKeyWidthPx = (int) (keyCellSizePx * 1.25f);
            spaceKeyWidthPx = (int) (keyCellSizePx * 6.25f);
        }

        public int getKeyboardViewWidth() {
            return (int) (keyCellSizePx * 18.75);
        }

        public int getKeyboardViewHeight() {
            return keyCellSizePx * 7;
        }

        @SuppressWarnings(/* weak-warnings */ "all")
        public KeyParams getKeyParams(XServerKeyNames keyNames) throws UnsupportedKeyException {
            int x = 0;
            int y = 0;
            int width = 0;
            int height = keyCellSizePx;

            switch (keyNames) {
                // line 1
                case KEY_Pause:
                    x += keyCellSizePx;
                case KEY_ScrollLock:
                    x += keyCellSizePx;
                case KEY_Print:
                    x += keyCellSizePx + boundMarginSizePx;
                case KEY_F12:
                    x += keyCellSizePx;
                case KEY_F11:
                    x += keyCellSizePx;
                case KEY_F10:
                    x += keyCellSizePx;
                case KEY_F9:
                    x += keyCellSizePx + keyFunctionMarginSizePx;
                case KEY_F8:
                    x += keyCellSizePx;
                case KEY_F7:
                    x += keyCellSizePx;
                case KEY_F6:
                    x += keyCellSizePx;
                case KEY_F5:
                    x += keyCellSizePx + keyFunctionMarginSizePx;
                case KEY_F4:
                    x += keyCellSizePx;
                case KEY_F3:
                    x += keyCellSizePx;
                case KEY_F2:
                    x += keyCellSizePx;
                case KEY_F1:
                    x += 2 * keyCellSizePx;
                case KEY_Escape:
                    x += boundMarginSizePx;
                    y += boundMarginSizePx;
                    width = keyCellSizePx;
                    break;
                // line2
                case KEY_PgUp:
                    x += keyCellSizePx;
                case KEY_Home:
                    x += keyCellSizePx;
                case KEY_Insert:
                    x += backspaceKeyWidthPx + boundMarginSizePx;
                    width = keyCellSizePx;
                case KEY_BackSpace:
                    x += keyCellSizePx;
                    if (width == 0)
                        width = backspaceKeyWidthPx;
                case KEY_Equal:
                    x += keyCellSizePx;
                case KEY_Minus:
                    x += keyCellSizePx;
                case KEY_0:
                    x += keyCellSizePx;
                case KEY_9:
                    x += keyCellSizePx;
                case KEY_8:
                    x += keyCellSizePx;
                case KEY_7:
                    x += keyCellSizePx;
                case KEY_6:
                    x += keyCellSizePx;
                case KEY_5:
                    x += keyCellSizePx;
                case KEY_4:
                    x += keyCellSizePx;
                case KEY_3:
                    x += keyCellSizePx;
                case KEY_2:
                    x += keyCellSizePx;
                case KEY_1:
                    x += keyCellSizePx;
                case KEY_Tilde:
                    x += boundMarginSizePx;
                    y += getKeyParams(XServerKeyNames.KEY_Escape).y + keyCellSizePx + lineMarginSizePx;
                    if (width == 0)
                        width = keyCellSizePx;
                    break;
                // line 3
                case KEY_PgDown:
                    x += keyCellSizePx;
                case KEY_End:
                    x += keyCellSizePx;
                case KEY_Delete:
                    x += tabKeyWidthPx + boundMarginSizePx;
                    width = keyCellSizePx;
                case KEY_BSlash:
                    x += keyCellSizePx;
                    if (width == 0)
                        width = tabKeyWidthPx;
                case KEY_RBrace:
                    x += keyCellSizePx;
                case KEY_LBrace:
                    x += keyCellSizePx;
                case KEY_P:
                    x += keyCellSizePx;
                case KEY_O:
                    x += keyCellSizePx;
                case KEY_I:
                    x += keyCellSizePx;
                case KEY_U:
                    x += keyCellSizePx;
                case KEY_Y:
                    x += keyCellSizePx;
                case KEY_T:
                    x += keyCellSizePx;
                case KEY_R:
                    x += keyCellSizePx;
                case KEY_E:
                    x += keyCellSizePx;
                case KEY_W:
                    x += keyCellSizePx;
                case KEY_Q:
                    x += tabKeyWidthPx;
                    if (width == 0)
                        width = keyCellSizePx;
                case KEY_Tab:
                    x += boundMarginSizePx;
                    y += getKeyParams(XServerKeyNames.KEY_Tilde).y + keyCellSizePx;
                    if (width == 0)
                        width = tabKeyWidthPx;
                    break;
                // line 4
                case KEY_Enter:
                    x += keyCellSizePx;
                    width = enterKeyWidthPx;
                case KEY_Quote:
                    x += keyCellSizePx;
                case KEY_SemiColon:
                    x += keyCellSizePx;
                case KEY_L:
                    x += keyCellSizePx;
                case KEY_K:
                    x += keyCellSizePx;
                case KEY_J:
                    x += keyCellSizePx;
                case KEY_H:
                    x += keyCellSizePx;
                case KEY_G:
                    x += keyCellSizePx;
                case KEY_F:
                    x += keyCellSizePx;
                case KEY_D:
                    x += keyCellSizePx;
                case KEY_S:
                    x += keyCellSizePx;
                case KEY_A:
                    x += capslockKeyWidthPx;
                    if (width == 0)
                        width = keyCellSizePx;
                case KEY_CapsLock:
                    x += boundMarginSizePx;
                    y += getKeyParams(XServerKeyNames.KEY_Tab).y + keyCellSizePx;
                    if (width == 0)
                        width = capslockKeyWidthPx;
                    break;
                //line 5
                case KEY_Up:
                    x += shiftRKeyWidthPx + boundMarginSizePx + keyCellSizePx;
                    width = keyCellSizePx;
                case KEY_ShiftR:
                    x += keyCellSizePx;
                    if (width == 0)
                        width = shiftRKeyWidthPx;
                case KEY_Slash:
                    x += keyCellSizePx;
                case KEY_Period:
                    x += keyCellSizePx;
                case KEY_Comma:
                    x += keyCellSizePx;
                case KEY_M:
                    x += keyCellSizePx;
                case KEY_N:
                    x += keyCellSizePx;
                case KEY_B:
                    x += keyCellSizePx;
                case KEY_V:
                    x += keyCellSizePx;
                case KEY_C:
                    x += keyCellSizePx;
                case KEY_X:
                    x += keyCellSizePx;
                case KEY_Z:
                    x += shiftLKeyWidthPx;
                    if (width == 0)
                        width = keyCellSizePx;
                case KEY_ShiftL:
                    x += boundMarginSizePx;
                    y += getKeyParams(XServerKeyNames.KEY_CapsLock).y + keyCellSizePx;
                    if (width == 0)
                        width = shiftLKeyWidthPx;
                    break;
                // line 6
                case KEY_Right:
                    x += keyCellSizePx;
                case KEY_Down:
                    x += keyCellSizePx;
                case KEY_Left:
                    x += ctrlKeyWidthPx + boundMarginSizePx;
                    width = keyCellSizePx;
                case KEY_RCtrl:
                    x += ctrlKeyWidthPx;
                case KEY_Menu:
                    x += ctrlKeyWidthPx;
                case KEY_RMeta:
                    x += ctrlKeyWidthPx;
                case KEY_AltLang:
                    x += spaceKeyWidthPx;
                    if (width == 0)
                        width = ctrlKeyWidthPx;
                case KEY_Space:
                    x += ctrlKeyWidthPx;
                    if (width == 0)
                        width = spaceKeyWidthPx;
                case KEY_Alt:
                    x += ctrlKeyWidthPx;
                case KEY_LMeta:
                    x += ctrlKeyWidthPx;
                case KEY_LCtrl:
                    x += boundMarginSizePx;
                    y += getKeyParams(XServerKeyNames.KEY_ShiftR).y + keyCellSizePx;
                    if (width == 0)
                        width = ctrlKeyWidthPx;
                    break;
                default:
                    throw new UnsupportedKeyException(keyNames);
            }
            return new KeyParams(width, height, x, y, keyNames);
        }
    }

    private static class KeyParams {
        public int height;
        public int width;
        public int x;
        public int y;
        public XServerKeyNames keyNames;

        public KeyParams(int width, int height, int x, int y, XServerKeyNames keyNames) {
            this.height = height;
            this.width = width;
            this.x = x;
            this.y = y;
            this.keyNames = keyNames;
        }
    }

    public static class UnsupportedKeyException extends EecException {
        public UnsupportedKeyException(XServerKeyNames keyNames) {
            super("Unsupported Key: " + keyNames.name());
        }
    }
}
