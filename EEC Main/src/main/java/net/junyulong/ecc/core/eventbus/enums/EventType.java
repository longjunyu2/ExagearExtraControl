package net.junyulong.ecc.core.eventbus.enums;

public enum EventType {
    // 全局事件
    ExternalDeviceConnected("ExternalDeviceConnected"),
    GameLaunched("GameLaunched"),
    SettingUpdated("SettingUpdated"),
    UserUpdated("UserUpdated"),
    AppSwitchForeground("AppSwitchForeground"),
    AppSwitchBackground("AppSwitchBackground"),
    // 层事件
    LayerListUpdated("LayerListUpdated"),
    LayerVisibilityChanged("LayerVisibilityChanged"),
    LayerRemoved("LayerRemoved"),
    // 控件事件
    ViewModelUpdated("ViewModelUpdated"),
    ViewModelEditRequest("ViewModelEditRequest"),
    ViewModelDragging("ViewModelDragging"),
    ViewModelDraggedFinished("ViewModelDraggedFinished");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
