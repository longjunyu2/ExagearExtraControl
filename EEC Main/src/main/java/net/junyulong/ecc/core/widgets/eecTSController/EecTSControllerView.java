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

package net.junyulong.ecc.core.widgets.eecTSController;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.eltechs.axs.activities.StartupActivity;

import net.junyulong.ecc.core.eventbus.EventBus;
import net.junyulong.ecc.core.eventbus.base.BaseEvent;
import net.junyulong.ecc.core.eventbus.enums.EventType;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelEditRequestEvent;
import net.junyulong.ecc.core.eventbus.interfaces.IEventSubscriber;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.model.layout.EecLayoutModelWrapper;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;
import net.junyulong.ecc.core.universal.ViewUtils;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewEdgeType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewParentInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewStatus;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewDeployer;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewUpdateType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecThemeMakerAgency;
import net.junyulong.ecc.core.widgets.eecInputViews.button.GameButton;
import net.junyulong.ecc.core.widgets.popupWindow.TscAddViewPopupWindow;
import net.junyulong.ecc.core.widgets.popupWindow.TscEecGlobalPopupWindow;
import net.junyulong.ecc.core.widgets.popupWindow.TscFpsPopupWindow;
import net.junyulong.ecc.core.widgets.popupWindow.TscLayerManagerPopupWindow;
import net.junyulong.ecc.core.widgets.popupWindow.TscMainMenuPopupWindow;
import net.junyulong.ecc.core.widgets.popupWindow.TscViewEditorPopupWindow;

import java.util.ArrayList;

import static net.junyulong.ecc.core.eventbus.enums.EventType.LayerRemoved;
import static net.junyulong.ecc.core.eventbus.enums.EventType.LayerVisibilityChanged;
import static net.junyulong.ecc.core.eventbus.enums.EventType.ViewModelEditRequest;

public class EecTSControllerView extends FrameLayout implements EecInputViewParentInterface, IEventSubscriber {

    // 控件表
    private final ArrayList<EecInputViewChildInterface> mViewInterfaces;

    // 布局封装实例
    private final EecLayoutModelWrapper layoutWrapper;

    // 控件部署器
    private final EecInputViewDeployer mViewDeployer;

    // 主控件Id
    public final static String ParentId = "parent";

    // 全局状态
    private EecInputViewStatus globalStatus = EecInputViewStatus.Applied;

    // 菜单窗口代理类实例
    private final ATscPopupWindowAgency mPopupWindowAgency;

    // 控件主题生成器
    private final EecThemeMakerAgency themeMaker;

    // 控件编辑处理器
    private final ViewModelEditHandler editHandler;

    // 订阅的事件类型
    private final static EventType[] ReceiveEventTypes = {
            LayerVisibilityChanged, LayerRemoved, ViewModelEditRequest};

    public EecTSControllerView(Context context) {
        super(context);
        this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // 设置透明背景
        this.setBackgroundColor(Color.TRANSPARENT);
        // 初始化控件部署器
        this.mViewDeployer = new EecInputViewDeployer(this);
        // 初始化主题生成器
        this.themeMaker = new EecThemeMakerAgency();
        // 初始换控件表
        this.mViewInterfaces = new ArrayList<>();
        // 初始化布局封装实例
        this.layoutWrapper = new EecLayoutModelWrapper();
        // 初始化窗口代理对象
        this.mPopupWindowAgency = new ATscPopupWindowAgency();
        // 初始化编辑处理器
        this.editHandler = new ViewModelEditHandler();
        // 让FrameLayout正确响应点击事件
        this.setClickable(true);
    }

    // 更新全部控件
    public void updateAllViews() {
        for (EecInputViewChildInterface childInterface : mViewInterfaces) {
            childInterface.viewUpdate(EecInputViewUpdateType.Own);
        }
    }

    @Override
    public boolean registerInputView(@NonNull EecInputViewChildInterface childInterface) {
        if (mViewInterfaces.contains(childInterface))
            return false;
        else {
            // 更新控件部署器
            mViewInterfaces.add(childInterface);
            mViewDeployer.updateView(childInterface);
            // 添加控件
            this.addView(childInterface.getEecView());
            // 更新全部控件
            updateAllViews();
            return true;
        }
    }

    @Override
    public boolean registerInputView(@NonNull EecInputViewModel model) {
        return registerInputView(new GameButton(getContext(), model, this));
    }

    @Override
    public boolean unregisterInputView(@NonNull EecInputViewChildInterface childInterface) {
        if (mViewInterfaces.contains(childInterface)) {
            mViewInterfaces.remove(childInterface);
            removeView(childInterface.getEecView());
            updateAllViews();
            return true;
        } else
            return false;
    }

    @Override
    public boolean unregisterInputView(@NonNull String inputViewId) {
        for (EecInputViewChildInterface childInterface : mViewInterfaces) {
            if (childInterface.getModel().getId().equals(inputViewId))
                return unregisterInputView(childInterface);
        }
        return false;
    }

    @Override
    public void removeAllInputViews() {
        // TODO: 实现清空控件方法
    }

    @Override
    public ArrayList<EecInputViewChildInterface> getEecInputViews() {
        return mViewInterfaces;
    }

    @Override
    public EecInputViewChildInterface getEecInputViewById(String id) {
        for (EecInputViewChildInterface childInterface : mViewInterfaces) {
            if (childInterface.getModel().getId().equals(id))
                return childInterface;
        }
        return null;
    }

    @Override
    public int getCounts() {
        return mViewInterfaces.size();
    }

    @Override
    public void setGlobalStatus(EecInputViewStatus status) {
        this.globalStatus = status;
    }

    @Override
    public EecInputViewStatus getGlobalStatus() {
        return this.globalStatus;
    }

    @Override
    public EecInputViewDeployer getDeployer() {
        return mViewDeployer;
    }

    @Override
    public EecThemeMakerAgency getThemeMarker() {
        return this.themeMaker;
    }

    @Override
    public EecLayoutModelWrapper getLayoutModelWrapper() {
        return this.layoutWrapper;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 调用代理类管理菜单的窗口
        mPopupWindowAgency.onAttachedToWindow();
        // 调用编辑处理器管理窗口
        editHandler.onAttachedToWindow();
        // 注册事件订阅
        EventBus.getDefaultEventBus().register(this, ReceiveEventTypes);
        // 创建扫描码测试窗口
        /*new ScanCodePostTestPopupWindow(getContext()).
                showAtLocation(this, Gravity.START | Gravity.CENTER, 0, 0);*/
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 代理类卸载菜单窗口
        mPopupWindowAgency.onDetachedFromWindow();
        // 编辑处理器卸载窗口
        editHandler.onDetachedFromWindow();
        // 注销事件订阅
        EventBus.getDefaultEventBus().unregister(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 当触摸菜单外部时关闭全部二级菜单
        mPopupWindowAgency.showAllSecondMenu(false);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE && event.getAction() == KeyEvent.ACTION_DOWN) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton(LocalStrings.Accept, (dialog, which) ->
                            StartupActivity.shutdownAXSApplication(true))
                    .setNegativeButton(LocalStrings.Cancel, (dialog, which) ->
                            dialog.dismiss())
                    .create()
                    .show();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onReceiveEvent(BaseEvent event) {
        switch (event.getEventType()) {
            case LayerVisibilityChanged:
                // TODO: 当图层可见性发生变化时，同时控制图层内控件的可见性
                Log.e("fuck", "visChanged");
                return true;
            case LayerRemoved:
                // TODO: 当图层被删除时，同时从屏幕上移除图层内的控件
                Log.e("fuck", "layRemoved");
                return true;
            case ViewModelEditRequest:
                // 分发给编辑处理器处理
                return editHandler.onReceiveEvent(event);
            default:
                return false;
        }
    }

    // 窗口代理
    private class ATscPopupWindowAgency implements TscPopupWindowAgency {
        // EEC全局窗口
        private TscEecGlobalPopupWindow tscEecGlobalPopupWindow;
        // 主菜单
        private TscMainMenuPopupWindow tscMainMenuPopupWindow;
        // 添加控件菜单
        private TscAddViewPopupWindow tscAddViewPopupWindow;
        // 图层管理菜单
        private TscLayerManagerPopupWindow tscLayerManagerPopupWindow;
        // Fps菜单
        private TscFpsPopupWindow tscFpsPopupWindow;
        // 窗口是否生成完成
        public boolean windowCreated = false;
        // EEC窗口的可见性
        private boolean isEecMenuShowed = false;
        // 主菜单的可见性
        private boolean isMainMenuShowed = false;
        // FPS窗口可见性
        private boolean isFpsWindowShowed = false;
        // 添加控件菜单可见性
        private boolean isAddViewMenuShowed = false;
        // 图层管理菜单可见性
        private boolean isLayerManagerShowed = false;

        @Override
        public void attach() {
            if (!windowCreated) {
                // 生成窗口实例
                tscFpsPopupWindow = new TscFpsPopupWindow(getContext());
                tscEecGlobalPopupWindow = new TscEecGlobalPopupWindow(getContext(), this);
                tscMainMenuPopupWindow = new TscMainMenuPopupWindow(getContext(), this);
                tscAddViewPopupWindow = new TscAddViewPopupWindow(getContext(), this);
                tscLayerManagerPopupWindow = new TscLayerManagerPopupWindow(getContext(), this);
                // 显示全局窗口和主菜单
                this.showEecMenu(true);
                this.showMainMenu(true);
                showFpsMonitorWindow(true);
                windowCreated = true;
            }
        }

        @Override
        public void detach() {
            if (windowCreated) {
                // 关闭全部窗口
                showAllSecondMenu(false);
                showMainMenu(false);
                showEecMenu(false);
                showFpsMonitorWindow(false);
                // 还原状态
                windowCreated = false;
                isEecMenuShowed = false;
                isMainMenuShowed = false;
                isFpsWindowShowed = false;
                tscEecGlobalPopupWindow = null;
                tscAddViewPopupWindow = null;
                tscMainMenuPopupWindow = null;
                tscLayerManagerPopupWindow = null;
            }
        }

        @Override
        public EecLayoutModelWrapper getLayoutWrapper() {
            return EecTSControllerView.this.getLayoutModelWrapper();
        }

        @Override
        public EecInputViewStatus getGlobalStatus() {
            return EecTSControllerView.this.getGlobalStatus();
        }

        @Override
        public void setGlobalStatus(EecInputViewStatus status) {
            EecTSControllerView.this.setGlobalStatus(status);
        }

        @Override
        public boolean isAddViewMenuShowing() {
            return tscAddViewPopupWindow.isShowing();
        }

        @Override
        public void showAddViewMenu(boolean show) {
            if (show)
                showSecondMenu(tscAddViewPopupWindow);
            else
                tscAddViewPopupWindow.dismiss();
        }

        @Override
        public void showAllSecondMenu(boolean show) {
            showAddViewMenu(show);
            showLayerManagerMenu(show);
        }

        @Override
        public boolean isLayerManagerMenuShowing() {
            return tscLayerManagerPopupWindow.isShowing();
        }

        @Override
        public void showLayerManagerMenu(boolean show) {
            if (show)
                // 将图层控制菜单显示在主菜单左侧
                showSecondMenu(tscLayerManagerPopupWindow);
            else
                tscLayerManagerPopupWindow.dismiss();
        }

        @Override
        public boolean isMainMenuShowing() {
            return tscMainMenuPopupWindow.isShowing();
        }

        @Override
        public void showMainMenu(boolean show) {
            if (show)
                // 将主菜单显示在屏幕最右侧中央
                tscMainMenuPopupWindow.showAtLocation(EecTSControllerView.this,
                        Gravity.END, 0, 0);
            else
                tscMainMenuPopupWindow.dismiss();
        }

        @Override
        public void showEecMenu(boolean show) {
            if (show)
                // 将EEC窗口显示在屏幕右上角
                tscEecGlobalPopupWindow.showAtLocation(EecTSControllerView.this,
                        Gravity.TOP | Gravity.END, 0, 0);
            else
                tscEecGlobalPopupWindow.dismiss();
        }

        @Override
        public boolean registerView(EecInputViewModel model) {
            return registerInputView(model);
        }

        @Override
        public void showFpsMonitorWindow(boolean show) {
            if (show)
                // 将Fps监控窗口显示在屏幕左上角
                tscFpsPopupWindow.showAtLocation(EecTSControllerView.this,
                        Gravity.START | Gravity.TOP, 0, 0);
            else
                tscFpsPopupWindow.dismiss();
        }

        @Override
        public boolean isFpsMonitorWindowShowing() {
            return tscFpsPopupWindow.isShowing();
        }

        // 在主菜单左侧显示二级菜单
        private void showSecondMenu(PopupWindow window) {
            int windowWidth = window.getContentView().getWidth();
            if (windowWidth == 0) {
                int[] size = ViewUtils.getUndisplayedViewSize(window.getContentView());
                windowWidth = size[0];
            }
            window.showAsDropDown(tscMainMenuPopupWindow.getContentView(),
                    -windowWidth - 12, -tscMainMenuPopupWindow.getContentView().getHeight() - 5);
        }

        public void onDetachedFromWindow() {
            // 记录当前窗口状态
            isEecMenuShowed = tscEecGlobalPopupWindow.isShowing();
            isMainMenuShowed = isMainMenuShowing();
            isFpsWindowShowed = isFpsMonitorWindowShowing();
            isAddViewMenuShowed = isAddViewMenuShowing();
            isLayerManagerShowed = isLayerManagerMenuShowing();
            // 关闭全部窗口
            showAllSecondMenu(false);
            showMainMenu(false);
            showEecMenu(false);
            showFpsMonitorWindow(false);
        }

        public void onAttachedToWindow() {
            if (windowCreated) {
                // 如果窗体初始化完成，则恢复卸载前的状态
                showEecMenu(isEecMenuShowed);
                showMainMenu(isMainMenuShowed);
                showFpsMonitorWindow(isFpsWindowShowed);
                tscMainMenuPopupWindow.getContentView().post(() -> {
                    // 等待主菜单创建完成后显示二级菜单
                    showAddViewMenu(isAddViewMenuShowed);
                    showLayerManagerMenu(isLayerManagerShowed);
                });
            } else {
                // 当窗体没有初始化时，进行初始化
                attach();
            }
        }
    }

    private class ViewModelEditHandler implements IEventSubscriber {

        // 控件编辑窗口
        private TscViewEditorPopupWindow tscViewEditorPopupWindow;

        // 销毁窗口
        private void destroyWindow(Runnable callback) {
            EecTSControllerView.this.post(() -> {
                // 多线程安全
                if (tscViewEditorPopupWindow == null)
                    return;
                // 销毁并隐藏编辑窗口
                tscViewEditorPopupWindow.detach();
                tscViewEditorPopupWindow.dismiss();
                tscViewEditorPopupWindow = null;
                // 执行额外的调用
                if (callback != null)
                    callback.run();
            });
        }

        @Override
        public boolean onReceiveEvent(BaseEvent event) {
            // 处理编辑请求事件
            if (event.getEventType() == ViewModelEditRequest) {
                if (tscViewEditorPopupWindow != null) {
                    // 如果窗口已经创建，尝试分发给编辑窗口
                    if (!tscViewEditorPopupWindow.onReceiveEvent(event)) {
                        // 编辑窗口未消费该事件
                        if (((ViewModelEditRequestEvent) event).getViewInterface()
                                == tscViewEditorPopupWindow.getChildInterface()) {
                            // 事件发起者为窗口目标，则关闭编辑窗口
                            destroyWindow(null);
                            // 恢复发起者的边框状态为正常
                            ((ViewModelEditRequestEvent) event).getViewInterface()
                                    .setEdgeType(EecInputViewEdgeType.ViewNormal);
                        } else {
                            // 事件发起者不是窗口目标，则销毁当前编辑窗口，并创建新的
                            destroyWindow(() -> onReceiveEvent(event));
                            // 恢复窗口目标的边框状态为正常
                            tscViewEditorPopupWindow.getChildInterface()
                                    .setEdgeType(EecInputViewEdgeType.ViewNormal);
                        }
                    }
                } else {
                    // 如果窗口未创建，初始化窗口实例
                    tscViewEditorPopupWindow = new TscViewEditorPopupWindow(EecTSControllerView.this.getContext(),
                            EecTSControllerView.this);
                    // 实装窗口
                    tscViewEditorPopupWindow.attach(((ViewModelEditRequestEvent) event).getViewInterface());
                    // 视图显示等待UI进程
                    EecTSControllerView.this.post(() -> {
                        // 多线程安全
                        if (tscViewEditorPopupWindow == null)
                            return;
                        // 显示窗口，初始位置为屏幕左上角
                        tscViewEditorPopupWindow.showAtLocation(
                                EecTSControllerView.this,
                                Gravity.START | Gravity.TOP,
                                0, 0);
                    });
                    // 设置边框状态为选中
                    ((ViewModelEditRequestEvent) event).getViewInterface()
                            .setEdgeType(EecInputViewEdgeType.ViewSelected);
                }
                return true;
            }
            return false;
        }

        public void onAttachedToWindow() {
            if (tscViewEditorPopupWindow != null && !tscViewEditorPopupWindow.isShowing()) {
                // 窗体装载时,如果窗口存在且被隐藏, 则显示被隐藏的编辑窗口
                tscViewEditorPopupWindow.showAtLocation(EecTSControllerView.this,
                        Gravity.START | Gravity.TOP, 0, 0);
            }
        }

        public void onDetachedFromWindow() {
            if (tscViewEditorPopupWindow != null) {
                // 当编辑窗口存在时，隐藏编辑窗口
                tscViewEditorPopupWindow.dismiss();
            }
        }
    }
}
