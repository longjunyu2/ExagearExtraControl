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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.input.EecKeyMap;
import net.junyulong.ecc.core.input.XServerKeyNames;
import net.junyulong.ecc.core.universal.ViewUtils;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint("ViewConstructor")
public class KeyboardView extends FrameLayout {
    private final Context context;
    private final EecKeyMap keyMap;
    private final KeyboardParams keyboardParams;

    @SuppressWarnings("unused")
    private final static int SCALE_TYPE_HEIGHT = -1;
    private final static int SCALE_TYPE_WIDTH = -2;
    public final static int PERFORM_CLICK = -1;
    public final static int PERFORM_SELECT = -2;
    private final static int BackgroundColor = Color.parseColor("#E0E0E0");
    private final static float MIN_KEY_CELL_SIZE_DP = 30;

    private KeySelectionChangedListener keySelectionChangedListener;
    private KeyClickListener keyClickListener;
    private int performType;

    public KeyboardView(Context context, EecKeyMap keyMap, float scale, int performType) {
        super(context);
        this.context = context;
        this.keyMap = keyMap;
        this.performType = performType;
        if (scale < 1)
            throw new EecException("Unsupported scale size: " + scale);
        keyboardParams = new KeyboardParams();
        keyboardParams.setKeyCellSize(
                (int) (EEC.getInstance().getEecWindowManager().getPxFromDp(MIN_KEY_CELL_SIZE_DP) * scale)
        );
        setBackgroundColor(BackgroundColor);
        init();
    }

    public KeyboardView(Context context, EecKeyMap keyMap, float scale) {
        this(context, keyMap, scale, PERFORM_SELECT);
    }

    @SuppressWarnings("unused")
    public KeyboardView(Context context, EecKeyMap keyMap, int performType, int scaleType, int size) {
        this(context, keyMap, (scaleType == SCALE_TYPE_WIDTH) ? size / 18.75f : size / 7f, performType);
    }

    @SuppressWarnings("unused")
    public KeyboardView(Context context, EecKeyMap keyMap, int scaleType, int size) {
        this(context, keyMap, (scaleType == SCALE_TYPE_WIDTH) ? size / 18.75f : size / 7f);
    }

    public void setOnKeySelectionChangedListener(KeySelectionChangedListener listener) {
        this.keySelectionChangedListener = listener;
    }

    public void setOnKeyClickListener(KeyClickListener listener) {
        this.keyClickListener = listener;
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
        initReserved();
    }

    private void initReserved() {
        XServerKeyNames[] keyNames = {
                XServerKeyNames.KEY_Print,
                XServerKeyNames.KEY_ScrollLock,
                XServerKeyNames.KEY_Pause,
                XServerKeyNames.KEY_LMeta,
                XServerKeyNames.KEY_RMeta,
                XServerKeyNames.KEY_Menu
        };
        for (XServerKeyNames keyName : keyNames) {
            try {
                addView(new KeyboardButton(keyboardParams.getKeyParams(keyName)));
            } catch (UnsupportedKeyException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPerformType(int type) {
        this.performType = type;
        init();
    }

    public void setPreSelectedKeys(XServerKeyNames[] preSelectedKeys) {
        ArrayList<XServerKeyNames> keyNamesList = new ArrayList<>(Arrays.asList(preSelectedKeys));
        if (performType == PERFORM_SELECT) {
            for (int i = 0; i < getChildCount(); i++) {
                View v = getChildAt(i);
                if (v instanceof KeyboardButton &&
                        keyNamesList.contains(((KeyboardButton) v).keyNames))
                    ((KeyboardButton) v).setKeySelected(true);
            }
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        params.width = keyboardParams.getKeyboardViewWidth();
        params.height = keyboardParams.getKeyboardViewHeight();
        super.setLayoutParams(params);
    }

    private class KeyboardButton extends FrameLayout {
        private final int[] DEFAULT_INSET_MARGINS = {15, 5, 15, 25};
        private final static float DEFAULT_OUTER_RADIUS = 10;
        private final static float DEFAULT_INNER_RADIUS = 5;
        private final static int DEFAULT_EDGE_SIZE = 2;
        private final static int DEFAULT_EDGE_COLOR = Color.BLACK;
        private final static int DEFAULT_INNER_COLOR = Color.WHITE;
        private final static int DEFAULT_INNER_PRESSED_COLOR = 0xFFB3B3B3;
        private final static int DEFAULT_OUTER_COLOR = 0xFFB3B3B3;
        private final static int DEFAULT_OUTER_PRESSED_COLOR = 0xFF737373;
        private final static int DEFAULT_SYMBOL_COLOR = Color.BLACK;
        private final static int DEFAULT_MAIN_SYMBOL_SIZE = 7;
        private final static int DEFAULT_VICE_SYMBOL_SIZE = DEFAULT_MAIN_SYMBOL_SIZE - 1;
        private final static int DEFAULT_SYMBOL_OFFSET = 5;

        private final XServerKeyNames keyNames;
        private final int height;
        private boolean selected = false;
        int[] insetMargins = DEFAULT_INSET_MARGINS;
        float outerRadius = DEFAULT_OUTER_RADIUS;
        float innerRadius = DEFAULT_INNER_RADIUS;
        int edgeSize = DEFAULT_EDGE_SIZE;
        int edgeColor = DEFAULT_EDGE_COLOR;
        int innerColor = DEFAULT_INNER_COLOR;
        int innerPressedColor = DEFAULT_INNER_PRESSED_COLOR;
        int outerColor = DEFAULT_OUTER_COLOR;
        int outerPressedColor = DEFAULT_OUTER_PRESSED_COLOR;
        int symbolColor = DEFAULT_SYMBOL_COLOR;
        int mainSymbolSizeSp = DEFAULT_MAIN_SYMBOL_SIZE;
        int viceSymbolSizeSp = DEFAULT_VICE_SYMBOL_SIZE;
        int symbolOffset = DEFAULT_SYMBOL_OFFSET;

        public KeyboardButton(KeyParams keyParams) {
            super(context);
            this.keyNames = keyParams.keyNames;
            this.height = keyParams.height;
            this.setLayoutParams(new FrameLayout.LayoutParams(keyParams.width, keyParams.height));
            this.setX(keyParams.x);
            this.setY(keyParams.y);
            this.setClickable(true);
            this.setOnClickListener(v -> {
                if (performType == PERFORM_CLICK) {
                    KeyClickListener listener = keyClickListener;
                    if (listener != null)
                        listener.onKeyClick(keyNames);
                } else if (performType == PERFORM_SELECT) {
                    KeySelectionChangedListener listener = keySelectionChangedListener;
                    if (listener != null) {
                        setKeySelected(!selected);
                        keySelectionChangedListener.onKeySelectionChanged(keyNames, selected);
                    }
                } else
                    throw new EecException("Unknown Perform Type: " + performType);
            });
            this.setBackground(createKeyboardButtonDrawable());
            addSymbol();
        }

        public void setKeySelected(boolean selected) {
            if (performType == PERFORM_SELECT) {
                this.selected = selected;
                if (selected) {
                    edgeColor = Color.RED;
                    symbolColor = Color.RED;
                    edgeSize = 5;
                } else {
                    edgeColor = DEFAULT_EDGE_COLOR;
                    symbolColor = DEFAULT_SYMBOL_COLOR;
                    edgeSize = DEFAULT_EDGE_SIZE;
                }
                setBackground(createKeyboardButtonDrawable());
                addSymbol();
            }
        }

        private void addSymbol() {
            if (getChildCount() != 0)
                removeAllViews();
            EecKeyMap.KeyItem keyItem =
                    keyMap.getKeyboardMap().get(keyNames);
            if (keyItem != null && keyItem.hasSymbol()) {
                String[] symbols = keyItem.getSymbols();
                if (symbols.length == 1) {
                    TextView symbolText = createKeyboardSymbolView(symbols[0], mainSymbolSizeSp);
                    symbolText.setX(insetMargins[0] + symbolOffset);
                    symbolText.setY(insetMargins[1]);
                    addView(symbolText);
                } else if (symbols.length == 2) {
                    TextView viceSymbolText = createKeyboardSymbolView(symbols[0], viceSymbolSizeSp);
                    viceSymbolText.setX(insetMargins[0] + symbolOffset);
                    viceSymbolText.setY(insetMargins[1]);
                    addView(viceSymbolText);

                    TextView mainSymbolText = createKeyboardSymbolView(symbols[1], viceSymbolSizeSp);
                    mainSymbolText.setX(insetMargins[0] + symbolOffset);
                    mainSymbolText.setY(height - insetMargins[3]
                            - ViewUtils.getUndisplayedViewSize(mainSymbolText)[1]);
                    addView(mainSymbolText);
                }
            } else {
                setClickable(false);
            }
        }

        private Drawable createKeyboardButtonDrawable() {
            Paint paint;

            float[] outerR = new float[8];
            Arrays.fill(outerR, outerRadius);
            float[] innerR = new float[8];
            Arrays.fill(innerR, innerRadius);
            RectF insetRect = new RectF(insetMargins[0], insetMargins[1], insetMargins[2], insetMargins[3]);

            // released
            ShapeDrawable shapeDrawableEdge = new ShapeDrawable(new RoundRectShape(outerR, null, innerR));
            paint = shapeDrawableEdge.getPaint();
            paint.setColor(edgeColor);
            paint.setStyle(Paint.Style.FILL);

            ShapeDrawable shapeDrawableInner = new ShapeDrawable(new RoundRectShape(outerR, null, innerR));
            paint = shapeDrawableInner.getPaint();
            paint.setColor(innerColor);
            paint.setStyle(Paint.Style.FILL);

            ShapeDrawable shapeDrawableOuter = new ShapeDrawable(new RoundRectShape(outerR, insetRect, innerR));
            paint = shapeDrawableOuter.getPaint();
            paint.setColor(outerColor);
            paint.setStyle(Paint.Style.FILL);

            LayerDrawable layerDrawable = new LayerDrawable(
                    new Drawable[]{shapeDrawableEdge, shapeDrawableInner, shapeDrawableOuter});
            layerDrawable.setLayerInset(1, edgeSize, edgeSize, edgeSize, edgeSize);
            layerDrawable.setLayerInset(2, edgeSize, edgeSize, edgeSize, edgeSize);

            // pressed
            ShapeDrawable shapeDrawableInnerPressed = new ShapeDrawable(new RoundRectShape(outerR, null, innerR));
            paint = shapeDrawableInnerPressed.getPaint();
            paint.setColor(innerPressedColor);
            paint.setStyle(Paint.Style.FILL);

            ShapeDrawable shapeDrawableOuterPressed = new ShapeDrawable(new RoundRectShape(outerR, insetRect, innerR));
            paint = shapeDrawableOuterPressed.getPaint();
            paint.setColor(outerPressedColor);
            paint.setStyle(Paint.Style.FILL);

            LayerDrawable layerDrawablePressed = new LayerDrawable(
                    new Drawable[]{shapeDrawableEdge, shapeDrawableInnerPressed, shapeDrawableOuterPressed});
            layerDrawable.setLayerInset(1, edgeSize, edgeSize, edgeSize, edgeSize);
            layerDrawable.setLayerInset(2, edgeSize, edgeSize, edgeSize, edgeSize);

            // selector
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{/* released */-android.R.attr.state_pressed}, layerDrawable);
            stateListDrawable.addState(new int[]{/* pressed  */ android.R.attr.state_pressed}, layerDrawablePressed);

            return stateListDrawable;
        }

        private TextView createKeyboardSymbolView(String symbol, int textSizeSp) {
            TextView textView = new TextView(context);
            textView.setText(symbol);
            textView.setTextSize(textSizeSp);
            textView.setTextColor(symbolColor);
            textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return textView;
        }
    }

    public interface KeySelectionChangedListener {
        void onKeySelectionChanged(XServerKeyNames keyNames, boolean selected);
    }

    public interface KeyClickListener {
        void onKeyClick(XServerKeyNames keyNames);
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
