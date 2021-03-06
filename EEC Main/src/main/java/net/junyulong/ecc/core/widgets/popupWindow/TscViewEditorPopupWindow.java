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

package net.junyulong.ecc.core.widgets.popupWindow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.dialogs.TextEditorDialogBuilder;
import net.junyulong.ecc.core.errors.EecContentViewNullException;
import net.junyulong.ecc.core.errors.EecElementRepeatException;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.eventbus.EventBus;
import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelDraggedFinishedEvent;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelDraggingEvent;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelEditRequestEvent;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelUpdatedEvent;
import net.junyulong.ecc.core.eventbus.interfaces.IEventSubscriber;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.model.layout.EecLayoutModelWrapper;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;
import net.junyulong.ecc.core.resource.EecColorResources;
import net.junyulong.ecc.core.resource.EecDrawableResources;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.widgets.buttonView.UnityThemeButton;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewDeployer;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewParentInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewUpdateType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static net.junyulong.ecc.core.eventbus.enums.EventType.ViewModelDraggedFinished;
import static net.junyulong.ecc.core.eventbus.enums.EventType.ViewModelDragging;
import static net.junyulong.ecc.core.eventbus.enums.EventType.ViewModelEditRequest;
import static net.junyulong.ecc.core.eventbus.enums.EventType.ViewModelUpdated;
import static net.junyulong.ecc.core.widgets.eecTSController.EecTSControllerView.ParentId;
import static net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewAlignBindType.*;

public class TscViewEditorPopupWindow extends TscPopupWindow implements IEventSubscriber {

    // 上下文
    private final Context context;

    // 父实例
    private final EecInputViewParentInterface parentInterface;

    // 目标控件接口的实例
    private EecInputViewChildInterface childInterface;

    // 视图是否创建完成
    private boolean uiCreated = false;

    // 窗口宽度相对值
    private final static int WindowWidthDp = 300;

    // 窗口宽度像素值
    private final int windowWidthPx;

    // 窗口高度相对值
    private final static int WindowHeightDp = 300;

    // 窗口高度像素值
    private final int windowHeightPx;

    // 字体大小
    private final static int TextSizeSp = 13;

    // 边距大小相对值
    private final static int MarginSizeDp = 5;

    // 边距大小像素值
    private final int marginSizePx;

    // 按钮大小相对值
    private final static int ButtonSizeDp = 20;

    // 按钮大小像素值
    private final int buttonSizePx;

    // 窗口位置
    private static float xPos = 0;

    private static float yPos = 0;

    // 视图创建器
    private final ViewCreator viewCreator;

    private final ViewHelper viewHelper;

    private final DialogHelper dialogHelper;

    // 更新器列表
    private final ArrayList<ViewUpdaterAndEventConsumer> updaterList;

    // 订阅的事件类型
    private final static EventType[] registerEventTypes = {
            /* 控件信息更新 */ ViewModelUpdated,
            /* 控件正在被拖拽 */ ViewModelDragging,
            /* 控件拖拽完成 */ ViewModelDraggedFinished
    };

    // 单位步长
    private final static int CellStep = 2;

    // 上一个父视图
    private View lastParentView;

    public TscViewEditorPopupWindow(Context context, EecInputViewParentInterface parentInterface) {
        super(context);
        this.context = context;
        this.parentInterface = parentInterface;
        // 初始化视图数值
        windowWidthPx = EEC.getInstance().getEecWindowManager().getPxFromDp(WindowWidthDp);
        marginSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginSizeDp);
        buttonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(ButtonSizeDp);
        windowHeightPx = EEC.getInstance().getEecWindowManager().getPxFromDp(WindowHeightDp);
        // 初始化视图更新器列表
        updaterList = new ArrayList<>();
        // 初始化试图创建器
        viewCreator = new ViewCreator();
        viewHelper = new ViewHelper();
        dialogHelper = new DialogHelper();
    }

    // 实装该对象
    public void attach(EecInputViewChildInterface childInterface) {
        this.childInterface = childInterface;
        // 注册订阅事件
        EventBus.getDefaultEventBus().register(this, registerEventTypes);
        // 创建视图
        setContentView(createView());
        // 设置窗口大小
        this.setWidth(windowWidthPx);
        this.setHeight(windowHeightPx);
        // 允许窗口在屏幕外
        this.setClippingEnabled(false);
        // 设置窗口为透明
        setBackgroundDrawable(null);
        // 设置窗口外层控件背景
        getContentView().setBackground(EecDrawableResources.getDefaultPopupWindowBackground());
    }

    // 卸载该对象
    public void detach() {
        // 注销订阅事件
        EventBus.getDefaultEventBus().unregister(this);
    }

    // 创建视图
    private View createView() {
        if (uiCreated)
            throw new EecElementRepeatException(
                    "Content view has been created. Refuse duplicate creation!");
        else {
            // 外层容器
            LinearLayout layout = new LinearLayout(context);
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout.setOrientation(LinearLayout.VERTICAL);
            // 添加窗口头部
            layout.addView(viewCreator.createWindowHeader());
            // 滚动容器
            ScrollView scrollLayout = new ScrollView(context);
            scrollLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(scrollLayout);
            // 视图容器
            LinearLayout innerLayout = new LinearLayout(context);
            innerLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            innerLayout.setOrientation(LinearLayout.VERTICAL);
            scrollLayout.addView(innerLayout);

            // 0.添加快捷操作视图
            innerLayout.addView(viewCreator.createQuickToolbarView());
            // 1.添加布局及约束视图
            innerLayout.addView(viewCreator.createLayoutEditView(RefType.Horizontal, true));
            innerLayout.addView(viewCreator.createLayoutEditView(RefType.Vertical, false));
            // 2.添加控件外观视图
            innerLayout.addView(viewCreator.createAppearanceEditView());

            // 视图更新
            innerContainerUpdate();

            uiCreated = true;

            return layout;
        }
    }

    // 获取当前接口
    public EecInputViewChildInterface getChildInterface() {
        return this.childInterface;
    }

    // 窗口内部更新
    private void innerWindowUpdate() {
        // 仅更新位置而不改变窗口大小
        update((int) xPos, (int) yPos, getWidth(), getHeight());
    }

    // 编辑窗口内容更新
    private void innerContainerUpdate() {
        for (ViewUpdaterAndEventConsumer updater : updaterList)
            updater.update();
    }

    private void appliedModify() {
        // 更新控件
        parentInterface.getDeployer().updateView(childInterface);
        childInterface.viewUpdate(EecInputViewUpdateType.WithChildren);
        // 更新编辑视图
        innerContainerUpdate();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        // 当视图未创建时显示窗口，抛出异常
        if (!uiCreated)
            throw new EecContentViewNullException("Content view has not been created!");
        else {
            super.showAtLocation(parent, gravity, x, y);
            // 窗口内部更新，以便仍显示在指定位置上
            innerWindowUpdate();
            lastParentView = parent;
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        // 当视图未创建时显示窗口，抛出异常
        if (!uiCreated)
            throw new EecContentViewNullException("Content view has not been created!");
        else {
            super.showAsDropDown(anchor, xOff, yOff, gravity);
            // 窗口内部更新，以便仍显示在指定位置上
            innerWindowUpdate();
            lastParentView = anchor;
        }
    }

    // 接收订阅信息
    @Override
    public boolean onReceiveEvent(BaseEvent event) {
        if (event.getEventType() == ViewModelUpdated
                && ((ViewModelUpdatedEvent) event).getViewInterface() == childInterface) {
            // 当事件类型为控件信息更新事件，且发起控件为当前目标控件，则更新当前视图
            for (ViewUpdaterAndEventConsumer updater : updaterList)
                updater.update();
            return true;
        } else if (event.getEventType() == ViewModelDragging
                && ((ViewModelDraggingEvent) event).getViewInterface() == childInterface) {
            // 当事件类型为控件被拖拽事件，且发起控件为当前目标控件，则将窗口背景调整为透明
            getContentView().post(() -> getContentView().setBackground(null));

            return true;
        } else if (event.getEventType() == ViewModelDraggedFinished
                && ((ViewModelDraggedFinishedEvent) event).getViewInterface() == childInterface) {
            // 当事件类型为控件拖拽完成事件，且发起控件为当前目标控件，则将窗口背景调整回灰色背景
            getContentView().post(() ->
                    getContentView().setBackground(EecDrawableResources.getDefaultPopupWindowBackground()));
            return true;
        }
        // 事件分发给视图更新器
        boolean consumed = false;
        for (ViewUpdaterAndEventConsumer updater : updaterList) {
            if (!consumed && updater.onReceiveEvent(event))
                consumed = true;
        }
        return consumed;
    }

    private interface ViewUpdaterAndEventConsumer extends IEventSubscriber {
        // 根据控件信息更新视图
        void update();
    }

    private class ViewCreator {

        // 创建窗口头部
        @SuppressLint("ClickableViewAccessibility")
        private View createWindowHeader() {
            // 外容器
            LinearLayout externalLayout = new LinearLayout(context);
            externalLayout.setOrientation(LinearLayout.VERTICAL);
            externalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            // 添加窗体拖拽功能
            externalLayout.setOnTouchListener(new View.OnTouchListener() {
                private float downX;
                private float downY;
                private float lastX;
                private float lastY;

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            downX = event.getRawX();
                            downY = event.getRawY();
                            lastX = xPos;
                            lastY = yPos;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            xPos = event.getRawX() - downX + lastX;
                            yPos = event.getRawY() - downY + lastY;
                            // 更新窗口位置
                            innerWindowUpdate();
                            break;
                    }
                    return false;
                }
            });
            // 允许LinearLayout接收Move和Up事件
            externalLayout.setLongClickable(true);

            // 内容器
            LinearLayout innerLayout = new LinearLayout(context);
            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
            innerLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            externalLayout.addView(innerLayout);

            // 标题文字
            TextView textView = new TextView(context);
            textView.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
            textView.setTextSize(TextSizeSp);
            textView.setLayoutParams(viewHelper.createParamsWithMargin(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
            textView.setText(LocalStrings.View_editor);
            innerLayout.addView(textView);

            // 间隔视图
            View intervalView = new View(context);
            LinearLayout.LayoutParams v2Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    1);
            v2Params.weight = 1;
            intervalView.setLayoutParams(v2Params);
            innerLayout.addView(intervalView);

            // 拖拽按钮
            AppCompatButton dragButton = new AppCompatButton(context);
            dragButton.setLayoutParams(viewHelper.createParamsWithMargin(buttonSizePx, buttonSizePx,
                    Gravity.CENTER_VERTICAL));
            dragButton.setBackground(EEC.getInstance().getEecResourcesManager().
                    getDrawable(EecImageResources.ic_drag_move_white_24dp));
            dragButton.setClickable(false);
            dragButton.setFocusable(false);
            innerLayout.addView(dragButton);

            // 分割线
            externalLayout.addView(viewHelper.createSplitLine(
                    Color.parseColor(EecColorResources.ColorHintText)));

            return externalLayout;
        }

        // 创建快捷操作视图
        private View createQuickToolbarView() {
            // 外容器
            LinearLayout layout = new LinearLayout(context);
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.VERTICAL);

            // 副标题: 通用操作
            layout.addView(viewHelper.createTinyHeader(LocalStrings.Universal_Operation));

            // 工具栏
            LinearLayout toolbarLayout = new LinearLayout(context);
            toolbarLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            toolbarLayout.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(toolbarLayout);

            // 按钮: 删除
            toolbarLayout.addView(viewHelper.createButton(v -> {
                // 获取控件的全部强绑定子项
                HashSet<EecInputViewChildInterface> childHashSet =
                        parentInterface.getDeployer().getBoundViewsInterface(
                                childInterface, EecInputViewDeployer.BindType.StrongBindChild);
                // 如果强绑定子项数量不为0，则停止删除该控件
                if (childHashSet.size() != 0) {
                    new AlertDialog.Builder(context, THEME_DEVICE_DEFAULT_DARK)
                            .setPositiveButton(LocalStrings.Accept, (dialog, which) -> dialog.dismiss())
                            .setTitle(LocalStrings.Error)
                            .setMessage(LocalStrings.One_or_more_strongly_bound_children)
                            .create()
                            .show();
                } else {
                    // 获取控件所在层
                    for (EecLayoutModelWrapper.EecLayerModelWrapper wrapper : parentInterface.getLayoutModelWrapper().getLayers()) {
                        if (wrapper.getViewModels().contains(childInterface.getModel())) {
                            // 从层中删除控件Model
                            wrapper.removeViewModel(childInterface.getModel());
                            // 注销控件
                            parentInterface.unregisterInputView(childInterface);
                            // 关闭编辑窗口
                            dismiss();
                        }
                    }
                }
            }, LocalStrings.Delete));

            // 按钮: 克隆
            toolbarLayout.addView(viewHelper.createButton(v -> {
                // TODO: 克隆功能
            }, LocalStrings.Clone));

            // 按钮: 归属
            toolbarLayout.addView(viewHelper.createButton(v -> {
                // TODO: 归属图层
            }, LocalStrings.Belong));

            return layout;
        }

        // 创建布局编辑视图
        private View createLayoutEditView(RefType refType, boolean withHeader) {
            class LocalViewHelper {
                LinearLayout createItemContainer(String itemName) {
                    // 容器
                    LinearLayout layoutContainer = new LinearLayout(context);
                    layoutContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    layoutContainer.setOrientation(LinearLayout.HORIZONTAL);
                    // 文本
                    TextView textView = new TextView(context);
                    textView.setText(itemName);
                    textView.setTextColor(Color.parseColor(EecColorResources.ColorItemText));
                    textView.setTextSize(TextSizeSp);
                    LinearLayout.LayoutParams textParams = viewHelper.createParamsWithMargin(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    textParams.gravity = Gravity.CENTER_VERTICAL;
                    textView.setLayoutParams(textParams);
                    layoutContainer.addView(textView);

                    return layoutContainer;
                }
            }
            LocalViewHelper localViewHelper = new LocalViewHelper();

            int offsetButtonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(40);
            EecInputViewModel.Bind.BindInfo targetBindInfo =
                    refType == RefType.Horizontal ? childInterface.getModel().getBind().horizontal
                            : childInterface.getModel().getBind().vertical;

            // 外容器
            LinearLayout layout = new LinearLayout(context);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.VERTICAL);

            if (withHeader) {
                // 副标题: 布局及约束
                layout.addView(viewHelper.createTinyHeader(LocalStrings.Layout_and_Constraints));
            }

            // 约束
            ViewGroup horContainer = viewHelper.createItemContainerView(
                    (refType == RefType.Horizontal) ?
                            LocalStrings.Horizontal_Reference : LocalStrings.Vertical_Reference);
            layout.addView(horContainer);

            //项容器
            LinearLayout layoutItem = new LinearLayout(context);
            layoutItem.setLayoutParams(viewHelper.createParamsWithMargin(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutItem.setOrientation(LinearLayout.VERTICAL);
            horContainer.addView(layoutItem);

            // 对象容器
            LinearLayout refContainer = localViewHelper.createItemContainer(LocalStrings.Object);
            // 文本: 约束对象Id
            TextView textObjectId = new TextView(context);
            textObjectId.setText(ParentId);
            textObjectId.setTextSize(TextSizeSp);
            textObjectId.setTextColor(Color.parseColor(EecColorResources.ColorHintText));
            textObjectId.setLayoutParams(viewHelper.createParamsWithMargin(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textObjectId.setGravity(Gravity.CENTER);
            textObjectId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updaterList.add(new ViewUpdaterAndEventConsumer() {
                        @Override
                        public void update() {

                        }

                        @Override
                        public boolean onReceiveEvent(BaseEvent event) {
                            if (event.getEventType() == ViewModelEditRequest) {
                                ViewModelEditRequestEvent e = (ViewModelEditRequestEvent) event;
                                if (e.getViewInterface() == childInterface)
                                    lastParentView.post(() -> Toast.makeText(context,
                                            LocalStrings.Cannot_select_itself_as_reference_object, Toast.LENGTH_SHORT).show());
                                else if (parentInterface.getDeployer().getBoundViewsInterface(childInterface,
                                        EecInputViewDeployer.BindType.WeakBindChild).contains(e.getViewInterface())) {
                                    lastParentView.post(() -> Toast.makeText(context,
                                            LocalStrings.Cannot_select_children_as_reference_object, Toast.LENGTH_SHORT).show());
                                } else {
                                    targetBindInfo.referenceId = e.getViewInterface().getModel().getId();
                                    lastParentView.post(() -> {
                                        showAtLocation(lastParentView, 0, 0, 0);
                                        appliedModify();
                                    });
                                    updaterList.remove(this);
                                }
                                return true;
                            }
                            return false;
                        }
                    });
                    dismiss();
                }
            });
            refContainer.addView(textObjectId);

            // 约束类型容器
            LinearLayout bindContainer = localViewHelper.createItemContainer(LocalStrings.Bind_Type);
            layoutItem.addView(bindContainer);
            layoutItem.addView(refContainer);
            // 列表: 约束类型
            Spinner spinnerBind = new Spinner(context);
            spinnerBind.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            List<String> bindTypeList;
            if (refType == RefType.Horizontal)
                bindTypeList = Arrays.asList(
                        Left_to_Left_of.name(),
                        Left_to_Right_of.name(),
                        Right_to_Left_of.name(),
                        Right_to_Right_of.name()
                );
            else if (refType == RefType.Vertical)
                bindTypeList = Arrays.asList(
                        Top_to_Top_of.name(),
                        Top_to_Bottom_of.name(),
                        Bottom_to_Top_of.name(),
                        Bottom_to_Bottom_of.name()
                );
            else
                throw new EecException("Unknown RefType: " + refType.name());
            spinnerBind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    targetBindInfo.referenceMode = (String) parent.getItemAtPosition(position);
                    appliedModify();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinnerBind.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, bindTypeList));
            spinnerBind.setPopupBackgroundDrawable(EecDrawableResources.getDefaultSpinnerWindowBackground());
            bindContainer.addView(spinnerBind);

            // 偏移量容器
            LinearLayout offsetContainer = localViewHelper.createItemContainer(LocalStrings.Offset);
            layoutItem.addView(offsetContainer);
            // 按钮: 减小偏移量
            offsetContainer.addView(viewHelper.createButton(v -> {
                targetBindInfo.offset -= CellStep;
                appliedModify();
            }, offsetButtonSizePx, offsetButtonSizePx, LocalStrings.Symbol_Left));
            // 编辑框: 偏移量
            EditText editOffset = viewHelper.createEditText();
            editOffset.setOnClickListener(v -> dialogHelper.showEditorDialogForInteger(
                    (dialog, editText, text) -> {
                        targetBindInfo.offset = Integer.parseInt(text);
                        appliedModify();
                    }, LocalStrings.Offset, editOffset.getText().toString()));
            offsetContainer.addView(editOffset);
            // 按钮: 增加偏移量
            offsetContainer.addView(viewHelper.createButton(v -> {
                targetBindInfo.offset += CellStep;
                appliedModify();
            }, offsetButtonSizePx, offsetButtonSizePx, LocalStrings.Symbol_Right));
            // 添加更新器
            updaterList.add(new ViewUpdaterAndEventConsumer() {
                @Override
                public void update() {
                    // 更新绑定的对象Id
                    textObjectId.post(() -> textObjectId.setText(targetBindInfo.referenceId));
                    // 更新偏移量
                    editOffset.post(() -> editOffset.setText(String.valueOf(targetBindInfo.offset)));
                    // 更新绑定类型
                    spinnerBind.post(() -> spinnerBind.setSelection(bindTypeList.indexOf(targetBindInfo.referenceMode)));
                }

                @Override
                public boolean onReceiveEvent(BaseEvent event) {
                    return false;
                }
            });
            return layout;
        }

        // 创建外观编辑视图
        private View createAppearanceEditView() {
            class LocalViewHelper {
                public final int Width = 0;
                public final int Height = 1;
                private final int offsetButtonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(40);
                public EditText editWidth;
                public EditText editHeight;

                public LinearLayout createParamEditItemContainer(int paramType) {
                    EecInputViewModel.Param targetParam = childInterface.getModel().getParam();

                    // 容器
                    String itemName = paramType == Width ? LocalStrings.Width : LocalStrings.Height;
                    LinearLayout container = viewHelper.createItemContainerView(itemName);
                    // 按钮: 减小
                    container.addView(viewHelper.createButton(v -> {
                        int paramSize = paramType == Width ? targetParam.width : targetParam.height;
                        if (paramSize >= CellStep) {
                            if (paramType == Width)
                                targetParam.width -= CellStep;
                            else
                                targetParam.height -= CellStep;
                        } else {
                            if (paramType == Width)
                                targetParam.width = 0;
                            else
                                targetParam.height = 0;
                        }
                        appliedModify();
                    }, offsetButtonSizePx, offsetButtonSizePx, LocalStrings.Symbol_Left));
                    // 编辑框: 控件宽/控件高
                    EditText editView = viewHelper.createEditText();
                    editView.setOnClickListener(v -> dialogHelper.showEditorDialogForInteger(
                            (dialog, editText, text) -> {
                                if (paramType == Width)
                                    targetParam.width = Integer.parseInt(text);
                                else if (paramType == Height)
                                    targetParam.height = Integer.parseInt(text);
                                appliedModify();
                            }, itemName, editView.getText().toString()));
                    container.addView(editView);
                    if (paramType == Width)
                        editWidth = editView;
                    else
                        editHeight = editView;
                    // 按钮: 增加
                    container.addView(viewHelper.createButton(v -> {
                        if (paramType == Width)
                            targetParam.width += CellStep;
                        else
                            targetParam.height += CellStep;
                        appliedModify();
                    }, offsetButtonSizePx, offsetButtonSizePx, LocalStrings.Symbol_Right));
                    return container;
                }
            }
            LocalViewHelper localViewHelper = new LocalViewHelper();

            // 外容器
            LinearLayout layout = new LinearLayout(context);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.VERTICAL);

            // 副标题: 控件外观
            layout.addView(viewHelper.createTinyHeader(LocalStrings.View_Appearance));

            // 项: 控件宽
            layout.addView(localViewHelper.createParamEditItemContainer(localViewHelper.Width));

            // 项: 控件高
            layout.addView(localViewHelper.createParamEditItemContainer(localViewHelper.Height));

            // 添加更新器
            updaterList.add(new ViewUpdaterAndEventConsumer() {
                @Override
                public void update() {
                    // 更新控件宽度
                    localViewHelper.editWidth.post(() -> localViewHelper.editWidth.setText(String.valueOf(
                            childInterface.getModel().getParam().width)));
                    // 更新控件高度
                    localViewHelper.editHeight.post(() -> localViewHelper.editHeight.setText(String.valueOf(
                            childInterface.getModel().getParam().height)));
                }

                @Override
                public boolean onReceiveEvent(BaseEvent event) {
                    return false;
                }
            });

            return layout;
        }
    }

    private class ViewHelper {
        // 标题长度相对值
        private final int HeaderLengthDp = WindowWidthDp / 5;

        // 标题长度像素值
        private final int headerLengthPx = EEC.getInstance().getEecWindowManager().getPxFromDp(HeaderLengthDp);

        // 创建项容器
        private LinearLayout createItemContainerView(String itemName) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(createParamsWithMargin(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView textView = new TextView(context);
            textView.setLayoutParams(createParamsWithMargin(headerLengthPx,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setText(itemName);
            textView.setTextSize(TextSizeSp);
            textView.setTextColor(Color.parseColor(EecColorResources.ColorItemText));
            layout.addView(textView);
            return layout;
        }

        // 创建带边距的params
        private LinearLayout.LayoutParams createParamsWithMargin(int width, int height) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,
                    height);
            layoutParams.setMargins(marginSizePx, marginSizePx, marginSizePx, marginSizePx);
            return layoutParams;
        }

        private LinearLayout.LayoutParams createParamsWithMargin(int width, int height, int gravity) {
            LinearLayout.LayoutParams layoutParams = createParamsWithMargin(width, height);
            layoutParams.gravity = gravity;
            return layoutParams;
        }

        // 创建副标题
        private View createTinyHeader(String header) {
            TextView textHeader = new TextView(context);
            LinearLayout.LayoutParams textHeaderParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textHeaderParams.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
            textHeaderParams.setMargins(0, marginSizePx, 0, marginSizePx);
            textHeader.setLayoutParams(textHeaderParams);
            textHeader.setTextSize(TextSizeSp);
            textHeader.setTextColor(Color.parseColor(EecColorResources.ColorViceHeader));
            textHeader.setText(header);
            return textHeader;
        }

        // 创建分割线
        private View createSplitLine(int color) {
            View splitLineView = new View(context);
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
            viewParams.setMargins(0, marginSizePx, marginSizePx, marginSizePx);
            splitLineView.setLayoutParams(viewParams);
            splitLineView.setBackgroundColor(color);
            return splitLineView;
        }

        // 创建编辑框
        private EditText createEditText() {
            EditText editView = new EditText(context);
            editView.setTextSize(TextSizeSp);
            LinearLayout.LayoutParams editParams = viewHelper.createParamsWithMargin(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            editParams.gravity = Gravity.CENTER;
            editParams.weight = 1;
            editView.setLayoutParams(editParams);
            editView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editView.setTextColor(Color.parseColor(EecColorResources.ColorHintText));
            editView.setFocusableInTouchMode(false);
            return editView;
        }

        // 创建统一式样按钮
        private UnityThemeButton createButton(View.OnClickListener clickListener, int width, int height, String text) {
            UnityThemeButton button = new UnityThemeButton(context);
            button.setTextSize(TextSizeSp);
            button.setText(text);
            LinearLayout.LayoutParams buttonParams = viewHelper.createParamsWithMargin(width, height);
            buttonParams.gravity = Gravity.CENTER;
            button.setLayoutParams(buttonParams);
            button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
            button.setOnClickListener(clickListener);
            return button;
        }

        private UnityThemeButton createButton(View.OnClickListener clickListener, String text) {
            return createButton(clickListener, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, text);
        }
    }

    private class DialogHelper {
        public void showEditorDialogForInteger(TextEditorDialogBuilder.OnTextConfirmedListener confirmedListener, String title, String text) {
            new TextEditorDialogBuilder(context)
                    .setTitle(title)
                    .setPositiveButton(LocalStrings.Accept, (dialog, editText, text1) -> {
                        try {
                            confirmedListener.onTextConfirmed(dialog, editText, text1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .setEnterAsPositive(true)
                    .setInputType(EditorInfo.TYPE_CLASS_PHONE)
                    .setText(text)
                    .create()
                    .show();
        }
    }

    private enum RefType {
        Vertical,
        Horizontal
    }
}
