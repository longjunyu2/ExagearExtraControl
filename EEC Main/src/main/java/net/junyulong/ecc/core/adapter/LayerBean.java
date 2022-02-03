package net.junyulong.ecc.core.adapter;

public class LayerBean {
    private final String layerName;   // 层名称

    private final boolean showing;  // 层是否在显示

    private final boolean selected; // 被选中

    public LayerBean(String name, boolean showing, boolean selected) {
        this.layerName = name;
        this.showing = showing;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getLayerName() {
        return layerName;
    }

    public boolean isShowing() {
        return showing;
    }

}
