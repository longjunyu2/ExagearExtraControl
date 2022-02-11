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

package net.junyulong.ecc.core.input;

import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.local.Lang;

import java.util.HashMap;

public class EecKeyMap {

    private HashMap<XServerKeyNames, KeyItem> keyboardMap;
    private Lang lang;

    public EecKeyMap() {
        super();
    }

    public EecKeyMap(Lang lang) {
        this();
        init(lang);
    }

    public void init(Lang lang) {
        switch (lang) {
            case zh_cn:
            case en:
                initForAmericanKeyboardLayout();
                break;
            default:
                throw new EecException("Lang is not supported: " + lang.name());
        }
        this.lang = lang;
    }

    public Lang getLang() {
        return this.lang;
    }

    private void initForAmericanKeyboardLayout() {
        keyboardMap = new HashMap<>();
        for (XServerKeyNames key : XServerKeyNames.values()) {
            if (!key.enabled())
                continue;
            String[] symbols = null;
            switch (key) {
                case KEY_Escape:
                    symbols = new String[]{"Esc"};
                    break;
                case KEY_F1:
                    symbols = new String[]{"F1"};
                    break;
                case KEY_F2:
                    symbols = new String[]{"F2"};
                    break;
                case KEY_F3:
                    symbols = new String[]{"F3"};
                    break;
                case KEY_F4:
                    symbols = new String[]{"F4"};
                    break;
                case KEY_F5:
                    symbols = new String[]{"F5"};
                    break;
                case KEY_F6:
                    symbols = new String[]{"F6"};
                    break;
                case KEY_F7:
                    symbols = new String[]{"F7"};
                    break;
                case KEY_F8:
                    symbols = new String[]{"F8"};
                    break;
                case KEY_F9:
                    symbols = new String[]{"F9"};
                    break;
                case KEY_F10:
                    symbols = new String[]{"F10"};
                    break;
                case KEY_F11:
                    symbols = new String[]{"F11"};
                    break;
                case KEY_F12:
                    symbols = new String[]{"F12"};
                    break;
                case KEY_Tilde:
                    symbols = new String[]{"~", "`"};
                    break;
                case KEY_1:
                    symbols = new String[]{"!", "1"};
                    break;
                case KEY_2:
                    symbols = new String[]{"@", "2"};
                    break;
                case KEY_3:
                    symbols = new String[]{"#", "3"};
                    break;
                case KEY_4:
                    symbols = new String[]{"$", "4"};
                    break;
                case KEY_5:
                    symbols = new String[]{"%", "5"};
                    break;
                case KEY_6:
                    symbols = new String[]{"^", "6"};
                    break;
                case KEY_7:
                    symbols = new String[]{"&", "7"};
                    break;
                case KEY_8:
                    symbols = new String[]{"*", "8"};
                    break;
                case KEY_9:
                    symbols = new String[]{"(", "9"};
                    break;
                case KEY_0:
                    symbols = new String[]{")", "0"};
                    break;
                case KEY_Minus:
                    symbols = new String[]{"_", "-"};
                    break;
                case KEY_Equal:
                    symbols = new String[]{"+", "="};
                    break;
                case KEY_BackSpace:
                    symbols = new String[]{"Backspace"};
                    break;
                case KEY_Tab:
                    symbols = new String[]{"Tab"};
                    break;
                case KEY_Q:
                    symbols = new String[]{"Q"};
                    break;
                case KEY_W:
                    symbols = new String[]{"W"};
                    break;
                case KEY_E:
                    symbols = new String[]{"E"};
                    break;
                case KEY_R:
                    symbols = new String[]{"R"};
                    break;
                case KEY_T:
                    symbols = new String[]{"T"};
                    break;
                case KEY_Y:
                    symbols = new String[]{"Y"};
                    break;
                case KEY_U:
                    symbols = new String[]{"U"};
                    break;
                case KEY_I:
                    symbols = new String[]{"I"};
                    break;
                case KEY_O:
                    symbols = new String[]{"O"};
                    break;
                case KEY_P:
                    symbols = new String[]{"P"};
                    break;
                case KEY_LBrace:
                    symbols = new String[]{"{", "["};
                    break;
                case KEY_RBrace:
                    symbols = new String[]{"}", "]"};
                    break;
                case KEY_BSlash:
                    symbols = new String[]{"|", "\\"};
                    break;
                case KEY_CapsLock:
                    symbols = new String[]{"Caps Lock"};
                    break;
                case KEY_A:
                    symbols = new String[]{"A"};
                    break;
                case KEY_S:
                    symbols = new String[]{"S"};
                    break;
                case KEY_D:
                    symbols = new String[]{"D"};
                    break;
                case KEY_F:
                    symbols = new String[]{"F"};
                    break;
                case KEY_G:
                    symbols = new String[]{"G"};
                    break;
                case KEY_H:
                    symbols = new String[]{"H"};
                    break;
                case KEY_J:
                    symbols = new String[]{"J"};
                    break;
                case KEY_K:
                    symbols = new String[]{"K"};
                    break;
                case KEY_L:
                    symbols = new String[]{"L"};
                    break;
                case KEY_SemiColon:
                    symbols = new String[]{":", ";"};
                    break;
                case KEY_Quote:
                    symbols = new String[]{"\"", "'"};
                    break;
                case KEY_Enter:
                    symbols = new String[]{"Enter"};
                    break;
                case KEY_ShiftL:
                    symbols = new String[]{"Shift"};
                    break;
                case KEY_Z:
                    symbols = new String[]{"Z"};
                    break;
                case KEY_X:
                    symbols = new String[]{"X"};
                    break;
                case KEY_C:
                    symbols = new String[]{"C"};
                    break;
                case KEY_V:
                    symbols = new String[]{"V"};
                    break;
                case KEY_B:
                    symbols = new String[]{"B"};
                    break;
                case KEY_N:
                    symbols = new String[]{"N"};
                    break;
                case KEY_M:
                    symbols = new String[]{"M"};
                    break;
                case KEY_Comma:
                    symbols = new String[]{"<", ","};
                    break;
                case KEY_Period:
                    symbols = new String[]{">", "."};
                    break;
                case KEY_Slash:
                    symbols = new String[]{"?", "/"};
                    break;
                case KEY_ShiftR:
                    symbols = new String[]{"Shift"};
                    break;
                case KEY_LCtrl:
                    symbols = new String[]{"Ctrl"};
                    break;
                case KEY_Alt:
                    symbols = new String[]{"Alt"};
                    break;
                case KEY_Space:
                    symbols = new String[]{"Space"};
                    break;
                case KEY_AltLang:
                    symbols = new String[]{"Alt"};
                    break;
                case KEY_RCtrl:
                    symbols = new String[]{"Ctrl"};
                    break;
                case KEY_Insert:
                    symbols = new String[]{"Insert"};
                    break;
                case KEY_Home:
                    symbols = new String[]{"Home"};
                    break;
                case KEY_PgUp:
                    symbols = new String[]{"PgUp"};
                    break;
                case KEY_Delete:
                    symbols = new String[]{"Delete"};
                    break;
                case KEY_End:
                    symbols = new String[]{"End"};
                    break;
                case KEY_PgDown:
                    symbols = new String[]{"PgDn"};
                    break;
                case KEY_Up:
                    symbols = new String[]{"↑"};
                    break;
                case KEY_Left:
                    symbols = new String[]{"←"};
                    break;
                case KEY_Down:
                    symbols = new String[]{"↓"};
                    break;
                case KEY_Right:
                    symbols = new String[]{"→"};
                    break;
            }
            keyboardMap.put(key, new KeyItem(key, symbols));
        }
    }

    public HashMap<XServerKeyNames, KeyItem> getKeyboardMap() {
        if (keyboardMap == null)
            throw new EecException("Keyboard Map has not been initialized.");
        return keyboardMap;
    }

    public static class KeyItem {
        private final XServerKeyNames key;
        private final String[] symbols;

        // 符号的顺序: 上、下、左、右 最多支持四个
        private KeyItem(XServerKeyNames key, String... symbols) {
            this.key = key;
            this.symbols = symbols;
        }

        public KeyItem(XServerKeyNames key) {
            this(key, (String) null);
        }

        public XServerKeyNames getKey() {
            return key;
        }

        public boolean hasSymbol() {
            return symbols != null && symbols.length != 0;
        }

        public boolean isNone() {
            return key.value() == 0;
        }

        public String getKeyName() {
            return key.name();
        }

        public String[] getSymbols() {
            return this.symbols;
        }
    }
}
