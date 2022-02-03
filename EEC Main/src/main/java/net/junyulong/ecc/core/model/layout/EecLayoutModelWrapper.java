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

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.GsonBuilder;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecElementMaximumException;
import net.junyulong.ecc.core.errors.EecElementNotFoundException;
import net.junyulong.ecc.core.errors.EecElementRepeatException;
import net.junyulong.ecc.core.model.layout.layer.EecLayerModel;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;
import net.junyulong.ecc.core.model.layout.resource.EecGlobalModel;
import net.junyulong.ecc.core.model.layout.resource.EecMacroModel;
import net.junyulong.ecc.core.model.layout.resource.EecThemeModel;

import java.util.ArrayList;
import java.util.Map;

public class EecLayoutModelWrapper {

    private final static String TAG = "LayoutWrapper";

    public final static int MaxLayerCounts = 8;

    private final EecLayoutModel layoutModel;

    private final EecLayoutResourceWrapper resourceWrapper;

    public EecLayoutModelWrapper(EecLayoutModel model) {
        this.layoutModel = model;
        resourceWrapper = new EecLayoutResourceWrapper(layoutModel.getResources());
    }

    public EecLayoutModelWrapper() {
        this(new EecLayoutModel());
    }

    // 获取Model
    public EecLayoutModel getLayoutModel() {
        return this.layoutModel;
    }

    // 获取资源
    public EecLayoutResourceWrapper getResource() {
        return this.resourceWrapper;
    }

    // 获取层
    public EecLayerModelWrapper getLayer(@NonNull String layerName) {
        for (EecLayerModel layerModel : layoutModel.getLayers())
            if (layerModel.getLayerName().equals(layerName))
                return new EecLayerModelWrapper(layerModel) {
                    @Override
                    public void setDefault(boolean asDefault) {
                        for (EecLayerModel model : layoutModel.getLayers())
                            model.setDefault(false);
                        super.setDefault(asDefault);
                    }
                };
        return null;
    }

    public ArrayList<EecLayerModelWrapper> getLayers() {
        ArrayList<EecLayerModelWrapper> layerWrappers = new ArrayList<>();
        for (EecLayerModel layerModel : layoutModel.getLayers()) {
            layerWrappers.add(new EecLayerModelWrapper(layerModel) {
                @Override
                public void setDefault(boolean asDefault) {
                    for (EecLayerModel model : layoutModel.getLayers())
                        model.setDefault(false);
                    super.setDefault(asDefault);
                }
            });
        }
        return layerWrappers;
    }

    public EecLayerModelWrapper getDefaultLayer() {
        for (EecLayerModel layerModel : layoutModel.getLayers())
            if (layerModel.isDefault())
                return new EecLayerModelWrapper(layerModel) {
                    @Override
                    public void setDefault(boolean asDefault) {
                        for (EecLayerModel model : layoutModel.getLayers())
                            model.setDefault(false);
                        super.setDefault(asDefault);
                    }
                };
        return null;
    }

    // 增加层
    public void addLayer(@NonNull EecLayerModel model) throws EecElementRepeatException, EecElementMaximumException {
        if (layoutModel.getLayers().size() == MaxLayerCounts)
            throw new EecElementMaximumException();
        else {
            for (EecLayerModel layerModel : layoutModel.getLayers()) {
                if (layerModel.getLayerName().equals(model.getLayerName()))
                    throw new EecElementRepeatException();
            }
            if (layoutModel.getLayers().size() == 0)
                model.setDefault(true);
            layoutModel.getLayers().add(model);
        }
    }

    // 删除层
    public void removeLayer(String layerName) throws EecElementNotFoundException {
        for (EecLayerModel layerModel : layoutModel.getLayers()) {
            if (layerModel.getLayerName().equals(layerName)) {
                layoutModel.getLayers().remove(layerModel);
                if (layerModel.isDefault() && layoutModel.getLayers().size() != 0)
                    layoutModel.getLayers().get(0).setDefault(true);
                return;
            }
        }
        throw new EecElementNotFoundException();
    }

    // 日志输出
    public void toLog() {
        Log.e(TAG, toString());
    }

    @NonNull
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this.layoutModel);
    }

    // EecLayoutResourceModel 封装
    public static class EecLayoutResourceWrapper {

        private final EecLayoutResourceModel resourceModel;

        public EecLayoutResourceWrapper(EecLayoutResourceModel resourceModel) {
            this.resourceModel = resourceModel;
        }

        public EecLayoutResourceWrapper() {
            this(new EecLayoutResourceModel());
        }

        // GlobalModel访问
        public String getLayoutName() {
            return resourceModel.getGlobalValues().getLayoutName();
        }

        public void setLayoutName(@NonNull String layoutName) {
            resourceModel.getGlobalValues().setLayoutName(layoutName);
        }

        public String getLayoutDes() {
            return resourceModel.getGlobalValues().getLayoutDescription();
        }

        public void setLayoutDes(@NonNull String layoutDes) {
            resourceModel.getGlobalValues().setLayoutDescription(layoutDes);
        }

        public int getSupportedVersion() {
            return resourceModel.getGlobalValues().getEecVersion();
        }

        public String getSupportedWindowRatio() {
            EecGlobalModel.Resolution resolution = resourceModel.getGlobalValues().getResolution();
            return EEC.getInstance().getEecWindowManager().getWindowRadio(resolution.width, resolution.height);
        }

        // 获取主题
        public EecThemeModelWrapper getTheme(@NonNull String id) {
            for (EecThemeModel model : resourceModel.getThemeValues())
                if (model.getThemeId().equals(id))
                    return new EecThemeModelWrapper(model);
            return null;
        }

        public ArrayList<EecThemeModelWrapper> getThemes() {
            ArrayList<EecThemeModelWrapper> wrappers = new ArrayList<>();
            for (EecThemeModel model : resourceModel.getThemeValues())
                wrappers.add(new EecThemeModelWrapper(model));
            return wrappers;
        }

        // 添加主题
        public void addTheme(@NonNull EecThemeModel model) throws EecElementRepeatException {
            for (EecThemeModel themeModel : resourceModel.getThemeValues()) {
                if (themeModel.getThemeId().equals(model.getThemeId()))
                    throw new EecElementRepeatException();
            }
            resourceModel.getThemeValues().add(model);
        }

        public void addTheme(@NonNull EecThemeModelWrapper wrapper) throws EecElementRepeatException {
            addTheme(wrapper.getThemeModel());
        }

        // 删除主题
        public void removeTheme(@NonNull String themeId) throws EecElementNotFoundException {
            for (EecThemeModel themeModel : resourceModel.getThemeValues()) {
                if (themeModel.getThemeId().equals(themeId)) {
                    resourceModel.getThemeValues().remove(themeModel);
                    return;
                }
            }
            throw new EecElementNotFoundException();
        }

        // 获取宏
        public EecMacroModelWrapper getMacro(@NonNull String id) {
            for (EecMacroModel model : resourceModel.getMacroValues())
                if (model.getMacroId().equals(id))
                    return new EecMacroModelWrapper(model);
            return null;
        }

        public ArrayList<EecMacroModelWrapper> getMacros() {
            ArrayList<EecMacroModelWrapper> wrappers = new ArrayList<>();
            for (EecMacroModel model : resourceModel.getMacroValues())
                wrappers.add(new EecMacroModelWrapper(model));
            return wrappers;
        }

        // 添加宏
        public void addMacro(@NonNull EecMacroModel model) throws EecElementRepeatException {
            for (EecMacroModel macroModel : resourceModel.getMacroValues()) {
                if (macroModel.getMacroId().equals(model.getMacroId()))
                    throw new EecElementRepeatException();
            }
            resourceModel.getMacroValues().add(model);
        }

        public void addMacro(@NonNull EecMacroModelWrapper wrapper) throws EecElementRepeatException {
            addMacro(wrapper.macroModel);
        }

        // 删除宏
        public void removeMacro(@NonNull String macroId) throws EecElementNotFoundException {
            for (EecMacroModel macroModel : resourceModel.getMacroValues()) {
                if (macroModel.getMacroId().equals(macroId)) {
                    resourceModel.getMacroValues().remove(macroModel);
                    return;
                }
            }
            throw new EecElementNotFoundException();
        }

        // EecThemeModel 封装
        public static class EecThemeModelWrapper {

            private final EecThemeModel themeModel;

            public EecThemeModelWrapper(@NonNull EecThemeModel model) {
                this.themeModel = model;
            }

            public EecThemeModelWrapper() {
                this(new EecThemeModel());
            }

            public EecThemeModel getThemeModel() {
                return this.themeModel;
            }

            // 获取主题Id
            public String getThemeId() {
                return themeModel.getThemeId();
            }

            // 设定主题Id
            public void setThemeId(@NonNull String id) {
                themeModel.setThemeId(id);
            }

            // 增加键
            public void addInfo(@NonNull String key, @NonNull String value) throws EecElementRepeatException {
                if (themeModel.getThemeInfo().containsKey(key))
                    throw new EecElementRepeatException();
                else
                    themeModel.getThemeInfo().put(key, value);
            }

            // 删除键
            public void removeInfo(@NonNull String key) throws EecElementNotFoundException {
                if (!themeModel.getThemeInfo().containsKey(key))
                    throw new EecElementNotFoundException();
                else
                    themeModel.getThemeInfo().remove(key);
            }

            // 修改键
            public void changeInfo(@NonNull String key, @NonNull String value) throws EecElementNotFoundException {
                removeInfo(key);
                addInfo(key, value);
            }
        }

        // EecMacroModel 封装
        public static class EecMacroModelWrapper {

            private final EecMacroModel macroModel;

            public EecMacroModelWrapper(EecMacroModel macroModel) {
                this.macroModel = macroModel;
            }

            public EecMacroModelWrapper() {
                this(new EecMacroModel());
            }

            // 获取宏Id
            public String getMacroId() {
                return macroModel.getMacroId();
            }

            // 设定宏Id
            public void setMacroId(@NonNull String id) {
                macroModel.setMacroId(id);
            }

            // 获取宏列表
            public ArrayList<Map<String, String>> getMacroList() {
                return macroModel.getMacroInfo();
            }

        }
    }

    // EecLayer 访问封装
    public static class EecLayerModelWrapper {

        private final EecLayerModel layerModel;

        public EecLayerModelWrapper(EecLayerModel model) {
            this.layerModel = model;
        }

        public EecLayerModelWrapper() {
            this(new EecLayerModel());
        }

        // Global 访问
        public String getLayerName() {
            return layerModel.getLayerName();
        }

        public void setLayerName(String layerName) {
            layerModel.setLayerName(layerName);
        }

        public String getLayerDes() {
            return layerModel.getLayerDescription();
        }

        public void setLayerDes(String layerDes) {
            layerModel.setLayerDescription(layerDes);
        }

        public boolean isDefault() {
            return layerModel.isDefault();
        }

        public void setDefault(boolean asDefault) {
            layerModel.setDefault(asDefault);
        }

        public boolean isShowing() {
            return layerModel.isShowing();
        }

        public void setShowing(boolean showing) {
            layerModel.setShowing(showing);
        }

        public EecLayerModel getLayerModel() {
            return this.layerModel;
        }

        // 获取控件
        public EecInputViewModel getViewModel(String id) throws EecElementNotFoundException {
            for (EecInputViewModel model : layerModel.getViews())
                if (model.getId().equals(id))
                    return model;
            return null;
        }

        public ArrayList<EecInputViewModel> getViewModels() {
            return layerModel.getViews();
        }

        // 增加控件
        public void addViewModel(@NonNull EecInputViewModel model) throws EecElementRepeatException {
            for (EecInputViewModel viewModel : layerModel.getViews()) {
                if (viewModel.getId().equals(model.getId()))
                    throw new EecElementRepeatException();
            }
            layerModel.getViews().add(model);
        }

        // 删除控件
        public void removeViewModel(@NonNull EecInputViewModel model) throws EecElementNotFoundException {
            if (!layerModel.getViews().contains(model))
                throw new EecElementNotFoundException();
            else
                layerModel.getViews().remove(model);
        }

        public void removeViewModel(@NonNull String id) throws EecElementNotFoundException {
            removeViewModel(getViewModel(id));
        }
    }

}
