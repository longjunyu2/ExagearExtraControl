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

import net.junyulong.ecc.core.model.layout.layer.EecLayerModel;

import java.util.ArrayList;
import java.util.List;

public class EecLayoutModel {

    private EecLayoutResourceModel resources;   // 公共资源

    private ArrayList<EecLayerModel> layers;               // 层

    public EecLayoutModel() {
        this.resources = new EecLayoutResourceModel();
        this.layers = new ArrayList<>();
    }

    public EecLayoutModel(EecLayoutResourceModel resourceModel, List<EecLayerModel> layerModels) {
        this.resources = resourceModel;
        this.layers = new ArrayList<>(layerModels);
    }

    public EecLayoutResourceModel getResources() {
        return resources;
    }

    public void setResources(EecLayoutResourceModel resources) {
        this.resources = resources;
    }

    public ArrayList<EecLayerModel> getLayers() {
        return layers;
    }

    public void setLayers(ArrayList<EecLayerModel> layers) {
        this.layers = layers;
    }
}
