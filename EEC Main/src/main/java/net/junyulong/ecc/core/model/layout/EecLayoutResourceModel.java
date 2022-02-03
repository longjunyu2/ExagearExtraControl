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

package net.junyulong.ecc.core.model.layout;

import net.junyulong.ecc.core.model.layout.resource.EecGlobalModel;
import net.junyulong.ecc.core.model.layout.resource.EecMacroModel;
import net.junyulong.ecc.core.model.layout.resource.EecThemeModel;

import java.util.ArrayList;
import java.util.List;

public class EecLayoutResourceModel {

    private ArrayList<EecThemeModel> themeValues;     // 全部公共主题信息

    private ArrayList<EecMacroModel> macroValues;     // 全部公共宏信息

    private EecGlobalModel globalValues;      // 全局信息

    public EecLayoutResourceModel() {
        this.themeValues = new ArrayList<>();
        this.macroValues = new ArrayList<>();
        this.globalValues = new EecGlobalModel();
    }

    public EecLayoutResourceModel(List<EecThemeModel> themeModels, List<EecMacroModel> macroModels,
                                  EecGlobalModel globalModel) {
        this.themeValues = new ArrayList<>(themeModels);
        this.macroValues = new ArrayList<>(macroModels);
        this.globalValues = globalModel;
    }

    public ArrayList<EecThemeModel> getThemeValues() {
        return themeValues;
    }

    public void setThemeValues(ArrayList<EecThemeModel> themeValues) {
        this.themeValues = themeValues;
    }

    public ArrayList<EecMacroModel> getMacroValues() {
        return macroValues;
    }

    public void setMacroValues(ArrayList<EecMacroModel> macroValues) {
        this.macroValues = macroValues;
    }

    public EecGlobalModel getGlobalValues() {
        return globalValues;
    }

    public void setGlobalValues(EecGlobalModel globalValues) {
        this.globalValues = globalValues;
    }
}
