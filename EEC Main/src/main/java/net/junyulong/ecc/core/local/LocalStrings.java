/*
 * Copyright 2022 Junyu Long
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.junyulong.ecc.core.local;

public class LocalStrings {
    public static String Accept;
    public static String Cancel;
    public static String Yes;
    public static String No;
    public static String Success;
    public static String Please_Enable_Edit_Mode;
    public static String Please_Disable_Edit_Mode;
    public static String Virtual_Key;
    public static String Horizontal_Reference;
    public static String Vertical_Reference;
    public static String Object;
    public static String Choose;
    public static String Offset;
    public static String Mode;
    public static String Name;
    public static String Keyboard;
    public static String Pointer;
    public static String Wheel;
    public static String Macro;
    public static String Function;
    public static String Immediate;
    public static String Trigger;
    public static String Serial;
    public static String Unnamed;
    public static String Layout_Description;
    public static String Layer_Description;
    public static String Virtual_Joystick;
    public static String Virtual_Mouse;
    public static String Quick_Create_Keyboard_Button;
    public static String Quick_Create_Mouse_Button;
    public static String Quick_Create_Virtual_Joystick;
    public static String Create_Advanced_Button;
    public static String Create_Advanced_Virtual_Joystick;
    public static String Create_Special_Button;
    public static String Advanced_Button;
    public static String Advanced_Joystick;
    public static String Special_Button;
    public static String Log_Button;
    public static String Create_Log_Button;
    public static String Main_Layer;
    public static String Add_Widget;
    public static String Layer_Count_Maximum;
    public static String View_editor;
    public static String Layout_and_Constraints;
    public static String View_Appearance;
    public static String Width;
    public static String Height;
    public static String Layer_Name;
    public static String Layer_Information;
    public static String Shortcut;
    public static String Delete;
    public static String Belong;
    public static String Clone;
    public static String Universal_Operation;
    public static String Error;
    public static String Warning;
    public static String Note;
    public static String One_or_more_strongly_bound_children;
    public static String Layer_Empty;
    public static String Bind_Type;
    public static String Symbol_Left;
    public static String Symbol_Right;
    public static String Scan_Code_Test;
    public static String Post;
    public static String Next;
    public static String Keyboard_View_Test;
    public static String Cannot_select_itself_as_reference_object;
    public static String Cannot_select_children_as_reference_object;

    public static void initialize(Lang lang) {
        switch (lang) {
            case zh_cn:
                initForChinese();
                break;
            case en:
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
        Please_Disable_Edit_Mode = "请先关闭编辑模式";
        Virtual_Key = "虚拟按键";
        Horizontal_Reference = "水平参考";
        Vertical_Reference = "垂直参考";
        Object = "对象";
        Choose = "选择";
        Offset = "偏移量";
        Mode = "模式";
        Name = "名称";
        Keyboard = "键盘";
        Pointer = "指针";
        Wheel = "滚轮";
        Macro = "宏";
        Function = "功能";
        Immediate = "实时";
        Trigger = "触发";
        Serial = "连发";
        Unnamed = "未命名";
        Layout_Description = "布局描述";
        Layer_Description = "图层描述";
        Virtual_Joystick = "虚拟摇杆";
        Virtual_Mouse = "虚拟鼠标";
        Quick_Create_Keyboard_Button = "快速创建键盘按钮";
        Quick_Create_Mouse_Button = "快速创建鼠标按钮";
        Quick_Create_Virtual_Joystick = "快速创建虚拟摇杆";
        Create_Advanced_Button = "支持高级宏的按钮";
        Create_Advanced_Virtual_Joystick = "支持高级宏的虚拟摇杆";
        Create_Special_Button = "控制EEC的特殊按钮";
        Advanced_Button = "高级按钮";
        Advanced_Joystick = "高级摇杆";
        Special_Button = "特殊按钮";
        Log_Button = "日志按钮";
        Create_Log_Button = "显示一段文本";
        Main_Layer = "主图层";
        Add_Widget = "添加控件";
        Layer_Count_Maximum = "自定义图层已满";
        View_editor = "控件编辑器";
        Layout_and_Constraints = "布局及约束";
        View_Appearance = "控件外观";
        Width = "宽度";
        Height = "高度";
        Layer_Name = "图层名称";
        Layer_Information = "图层信息";
        Shortcut = "快捷";
        Delete = "移除";
        Belong = "归属";
        Clone = "克隆";
        Universal_Operation = "通用操作";
        Error = "错误";
        Warning = "警告";
        Note = "提示";
        One_or_more_strongly_bound_children = "该控件存在一个或多个强绑定子项，删除该控件前请清空该控件的强绑定引用。";
        Layer_Empty = "图层不存在";
        Bind_Type = "约束类型";
        Symbol_Left = "<-";
        Symbol_Right = "->";
        Scan_Code_Test = "扫描码测试";
        Post = "发送";
        Next = "下一个";
        Keyboard_View_Test = "键盘控件测试";
        Cannot_select_itself_as_reference_object = "不能选择自身为参考对象";
        Cannot_select_children_as_reference_object = "不能选择存在参考关系的子对象为参考对象";
    }

    private static void initForEnglish() {
        Accept = "Accept";
        Cancel = "Cancel";
        Yes = "Yes";
        No = "No";
        Success = "Success";
        Please_Enable_Edit_Mode = "Please enable edit mode";
        Please_Disable_Edit_Mode = "Please disable edit mode";
        Virtual_Key = "Virtual Key";
        Horizontal_Reference = "Horizontal Reference";
        Vertical_Reference = "Vertical Reference";
        Object = "Object";
        Choose = "Choose";
        Offset = "Offset";
        Mode = "Mode";
        Name = "Name";
        Keyboard = "Keyboard";
        Pointer = "Pointer";
        Wheel = "Wheel";
        Macro = "Macro";
        Function = "Function";
        Immediate = "Immediate";
        Trigger = "Trigger";
        Serial = "Serial";
        Unnamed = "Unnamed";
        Layout_Description = "Layout description";
        Layer_Description = "Layer description";
        Virtual_Joystick = "Virtual Joystick";
        Virtual_Mouse = "Virtual Mouse";
        Quick_Create_Keyboard_Button = "Quick create keyboard button";
        Quick_Create_Mouse_Button = "Quick create mouse button";
        Quick_Create_Virtual_Joystick = "Quick create virtual joystick";
        Create_Advanced_Button = "support advanced macro";
        Create_Advanced_Virtual_Joystick = "support advanced macro";
        Create_Special_Button = "special function";
        Advanced_Button = "Advanced Button";
        Advanced_Joystick = "Advanced Joystick";
        Special_Button = "Special Button";
        Log_Button = "Log Button";
        Create_Log_Button = "Create log button";
        Main_Layer = "Main layer";
        Add_Widget = "Add Widget";
        Layer_Count_Maximum = "Layer counts maximum";
        View_editor = "View Editor";
        Layout_and_Constraints = "Layout and Constraints";
        View_Appearance = "View Appearance";
        Width = "Width";
        Height = "Height";
        Layer_Name = "Layer Name";
        Layer_Information = "Layer Information";
        Shortcut = "Shortcut";
        Delete = "Delete";
        Belong = "Belong";
        Clone = "Clone";
        Universal_Operation = "Universal Operation";
        Error = "Error";
        Warning = "Warning";
        Note = "Note";
        One_or_more_strongly_bound_children = "There are one or more strongly bound subitems in the control. Clear the strongly bound reference of the control before deleting it.";
        Layer_Empty = "Layer is empty";
        Bind_Type = "Bind Type";
        Symbol_Left = "<-";
        Symbol_Right = "->";
        Scan_Code_Test = "ScanCodeTest";
        Post = "Post";
        Next = "Next";
        Keyboard_View_Test = "Keyboard View Test";
        Cannot_select_itself_as_reference_object = "Cannot select itself as reference object";
        Cannot_select_children_as_reference_object = "Cannot select children as reference object";
    }

    public static Lang getLangFormLocalName(String str) {
        for (Lang lang : Lang.values()) {
            if (str.equals(lang.name()))
                return lang;
        }
        return null;
    }
}
