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
        Accept = "??????";
        Cancel = "??????";
        Yes = "???";
        No = "???";
        Success = "??????";
        Please_Enable_Edit_Mode = "????????????????????????";
        Please_Disable_Edit_Mode = "????????????????????????";
        Virtual_Key = "????????????";
        Horizontal_Reference = "????????????";
        Vertical_Reference = "????????????";
        Object = "??????";
        Choose = "??????";
        Offset = "?????????";
        Mode = "??????";
        Name = "??????";
        Keyboard = "??????";
        Pointer = "??????";
        Wheel = "??????";
        Macro = "???";
        Function = "??????";
        Immediate = "??????";
        Trigger = "??????";
        Serial = "??????";
        Unnamed = "?????????";
        Layout_Description = "????????????";
        Layer_Description = "????????????";
        Virtual_Joystick = "????????????";
        Virtual_Mouse = "????????????";
        Quick_Create_Keyboard_Button = "????????????????????????";
        Quick_Create_Mouse_Button = "????????????????????????";
        Quick_Create_Virtual_Joystick = "????????????????????????";
        Create_Advanced_Button = "????????????????????????";
        Create_Advanced_Virtual_Joystick = "??????????????????????????????";
        Create_Special_Button = "??????EEC???????????????";
        Advanced_Button = "????????????";
        Advanced_Joystick = "????????????";
        Special_Button = "????????????";
        Log_Button = "????????????";
        Create_Log_Button = "??????????????????";
        Main_Layer = "?????????";
        Add_Widget = "????????????";
        Layer_Count_Maximum = "?????????????????????";
        View_editor = "???????????????";
        Layout_and_Constraints = "???????????????";
        View_Appearance = "????????????";
        Width = "??????";
        Height = "??????";
        Layer_Name = "????????????";
        Layer_Information = "????????????";
        Shortcut = "??????";
        Delete = "??????";
        Belong = "??????";
        Clone = "??????";
        Universal_Operation = "????????????";
        Error = "??????";
        Warning = "??????";
        Note = "??????";
        One_or_more_strongly_bound_children = "?????????????????????????????????????????????????????????????????????????????????????????????????????????";
        Layer_Empty = "???????????????";
        Bind_Type = "????????????";
        Symbol_Left = "<-";
        Symbol_Right = "->";
        Scan_Code_Test = "???????????????";
        Post = "??????";
        Next = "?????????";
        Keyboard_View_Test = "??????????????????";
        Cannot_select_itself_as_reference_object = "?????????????????????????????????";
        Cannot_select_children_as_reference_object = "?????????????????????????????????????????????????????????";
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
