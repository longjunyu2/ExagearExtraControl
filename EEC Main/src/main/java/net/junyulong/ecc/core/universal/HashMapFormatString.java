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

package net.junyulong.ecc.core.universal;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class HashMapFormatString<K, V> extends HashMap<K, V> {

    public HashMapFormatString(Map<? extends K, ? extends V> m) {
        super(m);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append("HashMap { ");
        boolean f = false;
        for (Map.Entry<K, V> entry : this.entrySet()) {
            if (f)
                stb.append(", ");
            else
                f = true;
            stb.append(entry.getKey().toString()).append(" : ").append(entry.getValue().toString());
        }
        stb.append(" }");
        return stb.toString();
    }
}
