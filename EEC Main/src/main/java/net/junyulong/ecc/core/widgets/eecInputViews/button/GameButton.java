package net.junyulong.ecc.core.widgets.eecInputViews.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.eventbus.EventBus;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelDraggedFinishedEvent;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelDraggingEvent;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelEditRequestEvent;
import net.junyulong.ecc.core.eventbus.events.viewModel.ViewModelUpdatedEvent;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewChildInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewDeployer;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewEdgeType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewParentInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewUpdateType;

// 忽略视图构造器警告
@SuppressLint("ViewConstructor")
public class GameButton extends AppCompatButton implements EecInputViewChildInterface {

    private final static String TAG = "GameButton";

    // 控件Model实例
    private final EecInputViewModel model;

    // 控件父实例
    private final EecInputViewParentInterface parent;

    // 画笔
    private final Paint mPaint;

    // 画笔宽度
    private final static int PaintWidthPx = 10;

    // 初始画笔透明度
    private final static int PaintAlpha = 0;

    // 触摸事件处理器实例
    private final MotionConsumer motionConsumer;

    // 当前控件边缘类型
    private EecInputViewEdgeType edgeType = EecInputViewEdgeType.ViewNormal;

    // 忽略覆盖Click警告
    @SuppressLint("ClickableViewAccessibility")
    public GameButton(Context context, EecInputViewModel model,
                      EecInputViewParentInterface parentInterface) {
        super(context);
        this.model = model;
        this.parent = parentInterface;
        // 初始化画笔
        this.mPaint = new Paint();
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(PaintWidthPx);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAlpha(PaintAlpha);
        // 初始化触摸事件处理器
        motionConsumer = new MotionConsumer();
        this.setOnTouchListener((v, event) -> motionConsumer.onMotion(event));

    }

    @Override
    public void viewUpdate(EecInputViewUpdateType type) {
        // 设定控件大小
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                parent.getDeployer().getRealWidth(model),
                parent.getDeployer().getRealHeight(model)
        );
        this.setLayoutParams(layoutParams);
        // 设定控件文字
        this.setText(parent.getThemeMarker().getText(model));
        // 设定控件文字大小
        this.setTextSize(parent.getThemeMarker().getTextSizeSp(model));
        // 设定控件位置
        EecInputViewDeployer.RealViewPos pos = parent.getDeployer().getRealPos(this);
        this.setX(pos.x);
        this.setY(pos.y);
        if (type == EecInputViewUpdateType.WithChildren
                && parent.getDeployer().getBoundViewsCounts(this, EecInputViewDeployer.BindType.StrongBindChild) != 0) {
            // 如果更新类型为同绑定子项更新并且子项数量不为0，则对当前枝进行更新
            for (EecInputViewChildInterface childInterface :
                    parent.getDeployer().getBoundViewsInterface(model.getId(), EecInputViewDeployer.BindType.StrongBindChild)) {
                childInterface.viewUpdate(EecInputViewUpdateType.WithChildren);
            }
        }
    }

    @Override
    public void viewEdit() {
        EventBus.getDefaultEventBus().post(new ViewModelEditRequestEvent(this));
    }

    @Override
    public EecInputViewModel getModel() {
        return this.model;
    }

    @Override
    public View getEecView() {
        return this;
    }

    @Override
    public void setEdgeType(EecInputViewEdgeType edgeType) {
        // 边框状态不需要改变
        if (edgeType == this.edgeType)
            return;

        int alpha;
        int color;
        switch (edgeType) {
            // 边缘为正常时
            case ViewNormal:
                alpha = 0;
                color = Color.TRANSPARENT;
                this.edgeType = edgeType;
                break;
            // 边缘为被选中时
            case ViewSelected:
                alpha = 128;
                color = Color.BLUE;
                this.edgeType = edgeType;
                break;
            // 边缘为被移动时
            case ViewMoving:
                alpha = 128;
                color = Color.RED;
                this.edgeType = edgeType;
                break;
            default:
                throw new EecException("Unsupported edge type: " + edgeType.name());
        }
        // 重置画笔颜色和透明度
        mPaint.setAlpha(alpha);
        mPaint.setColor(color);
        // View更新
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制矩形边框
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    // 触摸事件处理类
    private class MotionConsumer {

        // 触控点无效时的索引值
        private final static int NoActionIndex = -1;
        // 块的像素大小
        private final int CellSizePx;
        // 手指按下时的坐标
        private float downX;
        private float downY;
        // 控件原偏移值
        private int offsetX;
        private int offsetY;
        // 触控点索引值
        private int actionIndex = -1;
        // 是否执行了移动操作
        private boolean performMove = false;
        // 上一个边框状态
        private EecInputViewEdgeType lastEdgeType;

        public MotionConsumer() {
            // 获取块的像素大小
            CellSizePx = EEC.getInstance().getEecWindowManager().getRealBaseCl();
        }

        public boolean onMotion(MotionEvent event) {
            if (event.getPointerCount() != 1) {
                // 如果触摸点数量不是1，则抛弃此事件
                return false;
            } else {
                switch (GameButton.this.parent.getGlobalStatus()) {
                    case Applied:
                        // TODO: 实现Applied
                        return true;
                    case Edited:
                        switch (event.getAction()) {
                            // 当手指按下
                            case MotionEvent.ACTION_DOWN:
                                if (actionIndex != NoActionIndex)
                                    // 当触控点索引有效时，放弃此事件
                                    return false;
                                else {
                                    Log.e(TAG, "down");
                                    // 更新手指按下时坐标
                                    downX = event.getRawX();
                                    downY = event.getRawY();
                                    // 记录控件原偏移值
                                    offsetX = model.getBind().horizontal.offset;
                                    offsetY = model.getBind().vertical.offset;
                                    // 更新触控点编号
                                    actionIndex = event.getActionIndex();
                                    // 更新上一个边框状态
                                    lastEdgeType = edgeType;
                                }
                                break;
                            // 当手指拖动
                            case MotionEvent.ACTION_MOVE:
                                if (actionIndex != event.getActionIndex()) {
                                    return false;
                                } else {
                                    Log.e(TAG, "move");
                                    // 控件偏移值增量为触摸点偏移值相较于块大小的整数倍
                                    int offsetDeltaX = (int) ((event.getRawX() - downX) / CellSizePx);
                                    int offsetDeltaY = (int) ((event.getRawY() - downY) / CellSizePx);
                                    Log.e(TAG, "offsetDeltaX: " + offsetDeltaX + " offsetDeltaY: " + offsetDeltaY);
                                    // 当控件偏移增量有效时更新控件偏移值
                                    if (offsetDeltaX != 0 || offsetDeltaY != 0) {
                                        model.getBind().horizontal.offset = offsetX + offsetDeltaX;
                                        model.getBind().vertical.offset = offsetY + offsetDeltaY;
                                        // 更新部署器信息
                                        GameButton.this.parent.getDeployer().updateView(GameButton.this);
                                        // 更新view
                                        GameButton.this.viewUpdate(EecInputViewUpdateType.WithChildren);
                                        // 更改移动状态为是
                                        if (!performMove)
                                            performMove = true;
                                        // 更新边框状态
                                        setEdgeType(EecInputViewEdgeType.ViewMoving);
                                        // 发起控件Model更新事件
                                        EventBus.getDefaultEventBus().post(
                                                new ViewModelUpdatedEvent(GameButton.this));
                                        // 发起控件被拖拽事件
                                        EventBus.getDefaultEventBus().post(
                                                new ViewModelDraggingEvent(GameButton.this));
                                    }
                                }
                                break;
                            // 当手指抬起时
                            case MotionEvent.ACTION_UP:
                                // 如果有效触控点存在
                                if (actionIndex != NoActionIndex) {
                                    Log.e(TAG, "up");
                                    // 还原触控点数值
                                    actionIndex = NoActionIndex;
                                    if (!performMove) {
                                        // 如果未执行移动操作，且未执行编辑操作，则执行编辑操作
                                        viewEdit();
                                    } else {
                                        // 还原移动状态
                                        performMove = false;
                                        // 还原控件边框状态
                                        setEdgeType(lastEdgeType);
                                        // 发起控件拖拽完成事件
                                        EventBus.getDefaultEventBus().post(
                                                new ViewModelDraggedFinishedEvent(GameButton.this));
                                    }
                                }
                                break;
                        }
                        return true;
                }
            }
            return false;
        }
    }
}
