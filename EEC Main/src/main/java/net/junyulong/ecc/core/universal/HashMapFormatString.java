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
