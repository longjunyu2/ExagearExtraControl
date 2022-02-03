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
