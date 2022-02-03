package net.junyulong.ecc.core.input;

import net.junyulong.ecc.core.errors.EecException;

import java.util.HashMap;

public class KeyCodesXConvertor {

    // TODO: 使用键值对完成双向映射

    private static final HashMap<String, Integer> map;

    static {
        if (KeyCodesXWithStr.values().length != EecKeyCodesX.values().length)
            throw new EecException("XKeyMap Error.");
        else {
            map = new HashMap<>();
            for (int i = 0; i < EecKeyCodesX.values().length; i++)
                map.put(KeyCodesXWithStr.values()[i].getValue(), EecKeyCodesX.values()[i].getValue());
        }
    }

    public static int getKeyCode(String keyCodeStr) {
        Integer keyCode;
        keyCode = map.get(keyCodeStr);
        if (keyCode == null)
            return EecKeyCodesX.KEY_NONE.getValue();
        else
            return keyCode;
    }

    public enum KeyCodesXWithStr {
        KEY_NONE("None"),
        KEY_ESC("Esc"),
        KEY_1("1"),
        KEY_2("2"),
        KEY_3("3"),
        KEY_4("4"),
        KEY_5("5"),
        KEY_6("6"),
        KEY_7("7"),
        KEY_8("8"),
        KEY_9("9"),
        KEY_0("0"),
        KEY_MINUS("-"),
        KEY_EQUAL("="),
        KEY_BACKSPACE("Backspace"),
        KEY_TAB("Tab"),
        KEY_Q("Q"),
        KEY_W("W"),
        KEY_E("E"),
        KEY_R("R"),
        KEY_T("T"),
        KEY_Y("Y"),
        KEY_U("U"),
        KEY_I("I"),
        KEY_O("O"),
        KEY_P("P"),
        KEY_BRACKET_LEFT("["),
        KEY_BRACKET_RIGHT("]"),
        KEY_RETURN("Enter"),
        KEY_CONTROL_LEFT("LCtrl"),
        KEY_A("A"),
        KEY_S("S"),
        KEY_D("D"),
        KEY_F("F"),
        KEY_G("G"),
        KEY_H("H"),
        KEY_J("J"),
        KEY_K("K"),
        KEY_L("L"),
        KEY_SEMICOLON(";"),
        KEY_APOSTROPHE("'"),
        KEY_GRAVE("`"),
        KEY_SHIFT_LEFT("LShift"),
        KEY_BACKSLASH("\\"),
        KEY_Z("Z"),
        KEY_X("X"),
        KEY_C("C"),
        KEY_V("V"),
        KEY_B("B"),
        KEY_N("N"),
        KEY_M("M"),
        KEY_COMMA(","),
        KEY_PERIOD("."),
        KEY_SLASH("/"),
        KEY_SHIFT_RIGHT("RShift"),
        KEY_KP_MULTIPLY("KP*"),
        KEY_ALT_LEFT("LAlt"),
        KEY_SPACE("Space"),
        KEY_CAPS_LOCK("CapsLock"),
        KEY_F1("F1"),
        KEY_F2("F2"),
        KEY_F3("F3"),
        KEY_F4("F4"),
        KEY_F5("F5"),
        KEY_F6("F6"),
        KEY_F7("F7"),
        KEY_F8("F8"),
        KEY_F9("F9"),
        KEY_F10("F10"),
        KEY_NUM_LOCK("NumLock"),
        KEY_SCROLL_LOCK("ScrollLock"),
        KEY_KP_7("KP7"),
        KEY_KP_8("KP8"),
        KEY_KP_9("KP9"),
        KEY_KP_SUB("KP-"),
        KEY_KP_4("KP4"),
        KEY_KP_5("KP5"),
        KEY_KP_6("KP6"),
        KEY_KP_ADD("KP+"),
        KEY_KP_1("KP1"),
        KEY_KP_2("KP2"),
        KEY_KP_3("KP3"),
        KEY_KP_0("KP0"),
        KEY_KP_DEL("KP."),
        KEY_F11("F11"),
        KEY_F12("F12"),
        KEY_KP_ENTER("KPEnter"),
        KEY_CONTROL_RIGHT("RCtrl"),
        KEY_KP_DIV("KP/"),
        KEY_PRINT("PrintSc"),
        KEY_ALT_RIGHT("RAlt"),
        KEY_HOME("Home"),
        KEY_UP("Up"),
        KEY_PRIOR("PageUp"),
        KEY_LEFT("Left"),
        KEY_RIGHT("Right"),
        KEY_END("End"),
        KEY_DOWN("Down"),
        KEY_NEXT("PageDown"),
        KEY_INSERT("Insert"),
        KEY_DELETE("Delete"),
        KEY_MAX(KEY_DELETE.id);

        private final String id;

        KeyCodesXWithStr(String i) {
            this.id = i;
        }

        public String getValue() {
            return this.id;
        }
    }
}
