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
