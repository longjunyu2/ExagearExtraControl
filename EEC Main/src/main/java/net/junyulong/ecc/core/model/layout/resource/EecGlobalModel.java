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

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.EecVersion;
import net.junyulong.ecc.core.local.LocalStrings;

public class EecGlobalModel {
    private String layoutName;        // 布局名称

    private int eecVersion;           // EEC版本

    private Resolution resolution;    // 屏幕分辨率

    private String layoutDescription; // 布局描述

    public EecGlobalModel() {
        this(LocalStrings.Unnamed, EecVersion.EEC_VERSION, new Resolution(EEC.getInstance().getEecWindowManager().getScreenWidth(),
                EEC.getInstance().getEecWindowManager().getScreenHeight()), "");
    }

    public EecGlobalModel(String layoutName, String layoutDescription) {
        this(layoutName, EecVersion.EEC_VERSION, new Resolution(EEC.getInstance().getEecWindowManager().getScreenWidth(),
                EEC.getInstance().getEecWindowManager().getScreenHeight()), layoutDescription);
    }

    private EecGlobalModel(String layoutName, int version, Resolution resolution, String layoutDescription) {
        this.layoutName = layoutName;
        this.eecVersion = version;
        this.resolution = resolution;
        this.layoutDescription = layoutDescription;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public int getEecVersion() {
        return eecVersion;
    }

    public void setEecVersion(int eecVersion) {
        this.eecVersion = eecVersion;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public String getLayoutDescription() {
        return layoutDescription;
    }

    public void setLayoutDescription(String layoutDescription) {
        this.layoutDescription = layoutDescription;
    }

    public static class Resolution {

        public Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int width;
        public int height;
    }
}
