package net.junyulong.ecc.core.model.layout.layer;

import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;

import java.util.ArrayList;

public class EecLayerModel {
    private String layerName;           // 层名称

    private String layerDescription;    // 层描述

    private ArrayList<EecInputViewModel> views;      // 全部控件信息

    private boolean asDefault;              // 作为默认

    private boolean showing;                // 默认显示

    public EecLayerModel() {
        this.layerName = "";
        this.layerDescription = "";
        this.views = new ArrayList<>();
    }

    public boolean isDefault() {
        return asDefault;
    }

    public void setDefault(boolean asDefault) {
        this.asDefault = asDefault;
    }

    public ArrayList<EecInputViewModel> getViews() {
        return views;
    }

    public void setViews(ArrayList<EecInputViewModel> views) {
        this.views = views;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerDescription() {
        return layerDescription;
    }

    public void setLayerDescription(String layerDescription) {
        this.layerDescription = layerDescription;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }
}
