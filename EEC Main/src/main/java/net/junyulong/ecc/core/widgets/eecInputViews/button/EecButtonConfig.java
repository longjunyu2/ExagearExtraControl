package net.junyulong.ecc.core.widgets.eecInputViews.button;

import android.support.annotation.NonNull;

import net.junyulong.ecc.core.universal.HashMapFormatString;
import net.junyulong.ecc.core.widgets.eecInputViews.EecBaseInputViewConfig;

import java.util.HashMap;
import java.util.Map;

public class EecButtonConfig extends EecBaseInputViewConfig {

    public EecButtonConfig() {
        super();
    }

    public EecButtonConfig(EecBaseInputViewConfig config) {
        super(config);
    }

    public EecButtonConfig(EecButtonConfig config) {
        super(config);

        this.keyName = config.getKeyName();

        this.keyThemes = config.getKeyThemes();

        this.keyThemeInfo = new HashMap<>();

        keyThemeInfo.putAll(config.getKeyThemeInfo());

        this.asKeyboard = config.isAsKeyboard();

        this.keySym = config.getKeySym();

        this.keyType = config.getKeyType();

        this.asPointer = config.isAsPointer();

        this.pointerX = config.getPointerX();

        this.pointerY = config.getPointerY();

        this.pointerType = config.getPointerType();

        this.asWheel = config.isAsWheel();

        this.wheelDelta = config.getWheelDelta();

        this.wheelType = config.getWheelType();
    }

    // For Universal Config
    private String keyName;       // 按键显示的名称
    private String keyThemes;     // 按键的式样名称
    private Map<String, String> keyThemeInfo;  // 按键的主题

    // For Keyboard
    private boolean asKeyboard = false;
    private String keySym;        // 按键的键值符号
    private String keyType;       // 按键的触发类型

    // For Pointer
    private boolean asPointer = false;
    private String pointerX;      // 指针的X值
    private String pointerY;      // 指针的Y值
    private String pointerType;   // 指针的类型

    // For Wheel
    private boolean asWheel = false;
    private String wheelDelta;    // 滚轮的步长
    private String wheelType;     // 滚轮的类型

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " EecButton { [keyName] " + this.keyName + " [keyThemes] " + this.keyThemes +
                " [keyThemeInfo] " + new HashMapFormatString<>(this.keyThemeInfo).toString() +
                ((this.asKeyboard) ? " [asKeyboard] " + true + " [keySym] " + this.keySym + " [keyType] " + this.keyType :
                        ((this.asPointer) ? " [asPointer] " + true + " [pointerX] " + this.pointerX +
                                " [pointerY] " + this.pointerY + " [pointerType] " + this.pointerType :
                                ((this.asWheel) ? " [asWheel] " + true + " [wheelDelta] " + this.wheelDelta +
                                        " [wheelType] " + this.wheelType : ""))) + " }";
    }

    // getter and setter

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public boolean isAsKeyboard() {
        return asKeyboard;
    }

    public void setAsKeyboard(boolean asKeyboard) {
        this.asKeyboard = asKeyboard;
    }

    public String getKeySym() {
        return keySym;
    }

    public void setKeySym(String keySym) {
        this.keySym = keySym;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public boolean isAsPointer() {
        return asPointer;
    }

    public void setAsPointer(boolean asPointer) {
        this.asPointer = asPointer;
    }

    public String getPointerX() {
        return pointerX;
    }

    public void setPointerX(String pointerX) {
        this.pointerX = pointerX;
    }

    public String getPointerY() {
        return pointerY;
    }

    public void setPointerY(String pointerY) {
        this.pointerY = pointerY;
    }

    public String getPointerType() {
        return pointerType;
    }

    public void setPointerType(String pointerType) {
        this.pointerType = pointerType;
    }

    public boolean isAsWheel() {
        return asWheel;
    }

    public void setAsWheel(boolean asWheel) {
        this.asWheel = asWheel;
    }

    public String getWheelDelta() {
        return wheelDelta;
    }

    public void setWheelDelta(String wheelDelta) {
        this.wheelDelta = wheelDelta;
    }

    public String getWheelType() {
        return wheelType;
    }

    public void setWheelType(String wheelType) {
        this.wheelType = wheelType;
    }

    public String getKeyThemes() {
        return keyThemes;
    }

    public void setKeyThemes(String keyThemes) {
        this.keyThemes = keyThemes;
    }

    public Map<String, String> getKeyThemeInfo() {
        return keyThemeInfo;
    }

    public void setKeyThemeInfo(Map<String, String> keyThemeInfo) {
        this.keyThemeInfo = keyThemeInfo;
    }
}
