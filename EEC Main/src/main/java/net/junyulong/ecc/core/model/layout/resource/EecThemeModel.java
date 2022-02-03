package net.junyulong.ecc.core.model.layout.resource;

import java.util.HashMap;
import java.util.Map;

public class EecThemeModel {

    private Map<String, String> themeInfo;  // 主题信息表

    private String themeId;                 // 主题Id

    public EecThemeModel(Map<String, String> map) {
        this.themeInfo = new HashMap<>(map);
        this.themeId = "";
    }

    public EecThemeModel() {
        this(new HashMap<>());
    }

    public Map<String, String> getThemeInfo() {
        return themeInfo;
    }

    public void setThemeInfo(Map<String, String> themeInfo) {
        this.themeInfo = themeInfo;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
}
