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

package net.junyulong.ecc.core.model.layout.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EecMacroModel {
    private ArrayList<Map<String, String>> macroInfo;    // 宏信息表

    private String macroId;                              // 宏Id

    public EecMacroModel(List<HashMap<String, String>> list) {
        this.macroInfo = new ArrayList<>(list);
        this.macroId = "";
    }

    public EecMacroModel() {
        this(new ArrayList<>());
    }

    public ArrayList<Map<String, String>> getMacroInfo() {
        return macroInfo;
    }

    public void setMacroInfo(ArrayList<Map<String, String>> macroInfo) {
        this.macroInfo = macroInfo;
    }

    public String getMacroId() {
        return macroId;
    }

    public void setMacroId(String macroId) {
        this.macroId = macroId;
    }
}
