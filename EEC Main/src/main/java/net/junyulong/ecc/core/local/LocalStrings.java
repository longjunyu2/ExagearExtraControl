package net.junyulong.ecc.core.local;

public class LocalStrings {

    public static final String LOCAL_CHINESE = "Chinese";

    public static final String LOCAL_ENGLISH = "English";

    public static String Accept;
    public static String Cancel;
    public static String Yes;
    public static String No;
    public static String Success;
    public static String Please_Enable_Edit_Mode;

    public static void initialize(Lang lang) {
        switch (lang) {
            case Chinese:
                initForChinese();
                break;
            case English:
                initForEnglish();
                break;
        }
    }

    private static void initForChinese() {
        Accept = "确认";
        Cancel = "取消";
        Yes = "是";
        No = "否";
        Success = "成功";
        Please_Enable_Edit_Mode = "请先开启编辑模式";
    }

    private static void initForEnglish() {
        Accept = "Accept";
        Cancel = "Cancel";
        Yes = "Yes";
        No = "No";
        Success = "Success";
        Please_Enable_Edit_Mode = "Please enable edit mode";
    }

    public static String getLocalNameFromLang(Lang lang) {
        switch (lang) {
            case Chinese:
                return LOCAL_CHINESE;
            case English:
                return LOCAL_ENGLISH;
        }
        return null;
    }

    public static Lang getLangFormLocalName(String str) {
        switch (str) {
            case LOCAL_CHINESE:
                return Lang.Chinese;
            case LOCAL_ENGLISH:
                return Lang.English;
        }
        return null;
    }
}
