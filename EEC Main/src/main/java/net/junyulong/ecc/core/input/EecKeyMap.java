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

    public void init(Lang lang) {
        // TODO: 完成初始化键值表的功能
    }

    private void initForAmericanKeyboardLayout() {
        keyboardMap = new HashMap<>();
        for (XServerKeyNames key : XServerKeyNames.values()) {
            // TODO: 添加美式键盘按键的符号
            keyboardMap.put(key, new KeyItem(key));
        }
    }

    public HashMap<XServerKeyNames, KeyItem> getKeyboardMap() {
        if (keyboardMap == null)
            throw new EecException("Keyboard Map has not been initialized.");
        return keyboardMap;
    }

    private static class KeyItem {
        private final XServerKeyNames key;
        private final String[] symbols;

        // 符号的顺序: 上、下、左、右 最多支持四个
        public KeyItem(XServerKeyNames key, String... symbols) {
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
            return !(symbols == null);
        }

        public boolean isNone() {
            return key.value() == 0;
        }

        public String getKeyName() {
            return key.name();
        }
    }
}
