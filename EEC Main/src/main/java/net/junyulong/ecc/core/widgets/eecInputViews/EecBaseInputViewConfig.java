package net.junyulong.ecc.core.widgets.eecInputViews;

import android.support.annotation.NonNull;

import java.util.UUID;

import static net.junyulong.ecc.core.widgets.eecTSController.EecTSControllerView.ParentId;

public class EecBaseInputViewConfig {

    public EecBaseInputViewConfig() {
        super();
        this.id = UUID.randomUUID().toString();
    }

    public EecBaseInputViewConfig(EecBaseInputViewConfig config) {
        super();

        this.id = config.getId();

        this.viewStatus = config.getViewStatus();

        this.viewHR = config.getViewHR();

        this.viewVR = config.getViewVR();

        this.viewHRM = config.getViewHRM();

        this.viewVRM = config.getViewVRM();

        this.viewHRD = config.getViewHRD();

        this.viewVRD = config.getViewVRD();

        this.viewWidth = config.getViewWidth();

        this.viewHeight = config.getViewHeight();

    }

    private String id;          // 控件唯一标识

    private String viewStatus;  // 控件状态

    private String viewHR = ParentId;      // 控件水平参考对象

    private String viewVR = ParentId;      // 控件垂直参考对象

    private String viewHRM = EecInputViewAlignType.getName(EecInputViewAlignType.Left_to_Left_of);     // 控件水平参考模式

    private String viewVRM = EecInputViewAlignType.getName(EecInputViewAlignType.Top_to_Top_of);     // 控件垂直参考模式

    private int viewHRD = 50;        // 控件水平参考增量

    private int viewVRD = 50;        // 控件垂直参考增量

    private int viewWidth = 50;      // 控件宽度

    private int viewHeight = 50;     // 控件高度

    @NonNull
    @Override
    public String toString() {
        return "BaseConfig { [id] " + this.id + " [viewStatus] " + this.viewStatus + " [viewHR] " + this.viewHR +
                " [viewVR] " + this.viewVR + " [viewHRM] " + this.viewHRM + " [viewVRM] " + this.viewVRM +
                " [viewHRD] " + this.viewHRD + " [viewVRD] " + this.viewVRD + " [viewWidth] " + this.viewWidth +
                " [viewHeight] " + this.viewHeight + " }";
    }

    // getter and setter

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewHR() {
        return viewHR;
    }

    public void setViewHR(String viewHR) {
        this.viewHR = viewHR;
    }

    public String getViewVR() {
        return viewVR;
    }

    public void setViewVR(String viewVR) {
        this.viewVR = viewVR;
    }

    public String getViewHRM() {
        return viewHRM;
    }

    public void setViewHRM(String viewHRM) {
        this.viewHRM = viewHRM;
    }

    public String getViewVRM() {
        return viewVRM;
    }

    public void setViewVRM(String viewVRM) {
        this.viewVRM = viewVRM;
    }

    public int getViewHRD() {
        return viewHRD;
    }

    public void setViewHRD(int viewHRD) {
        this.viewHRD = viewHRD;
    }

    public int getViewVRD() {
        return viewVRD;
    }

    public void setViewVRD(int viewVRD) {
        this.viewVRD = viewVRD;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }
}
