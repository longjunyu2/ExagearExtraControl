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

import android.content.Context;
import android.graphics.Color;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.junyulong.ecc.core.EEC;

public class TscFpsPopupWindow extends TscPopupWindow {

    private final Context context;

    // 文字大小
    private final static int TextSizeSp = 13;
    // 边距大小
    private final static int MarginSizeDp = 5;
    // 帧生成时间
    private long mStartFrameTime = 0;
    // 帧数
    private int mFrameCount = 0;
    // 监控内部
    private final static long MONITOR_INTERVAL = 160L;
    // 是否启用了fps更新
    private boolean fpsShowing = false;
    // 更新中断
    private boolean fpsUpdateInterrupted = false;
    // 文本控件实例
    private TextView fpsTextView;

    public TscFpsPopupWindow(Context context) {
        super(context);
        this.context = context;
        // 背景透明
        this.setBackgroundDrawable(null);
        // 创建视图
        setContentView(createView());
    }

    private View createView() {
        int marginPx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginSizeDp);

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);

        // 显示帧数的文本
        fpsTextView = new TextView(context);
        fpsTextView.setTextSize(TextSizeSp);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        textParams.gravity = Gravity.CENTER;
        fpsTextView.setLayoutParams(textParams);
        layout.addView(fpsTextView);
        return layout;
    }

    private void startFpsUpdate() {
        getFps(fps -> fpsTextView.post(() -> {
            int color;
            // 当fps>30时，字体为绿色
            if (fps >= 30)
                color = Color.GREEN;
                // 当15<=fps<30时，字体为黄色
            else if (fps > 15)
                color = Color.YELLOW;
                // 当fps<15时，字体为红色
            else
                color = Color.RED;
            // 更新颜色和帧率, 帧率四舍五入取整
            fpsTextView.setTextColor(color);
            fpsTextView.setText(String.valueOf(Math.round(fps)));
        }));
    }

    private void getFps(FpsRunnable runnable) {
        // 如果fps更新未启用，则启用它
        if (!fpsShowing) {
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                @Override
                public void doFrame(long frameTimeNanos) {
                    if (fpsUpdateInterrupted) {
                        // 如果中断启用，则退出并恢复中断
                        fpsUpdateInterrupted = false;
                        return;
                    }
                    if (mStartFrameTime == 0) {
                        mStartFrameTime = frameTimeNanos;
                    }
                    float interval = (frameTimeNanos - mStartFrameTime) / 1000000.0f;
                    if (interval > MONITOR_INTERVAL) {
                        float fps = mFrameCount * 1000L / interval;
                        runnable.run(fps);
                        mFrameCount = 0;
                        mStartFrameTime = 0;
                    } else {
                        mFrameCount++;
                    }
                    Choreographer.getInstance().postFrameCallback(this);
                }
            });
            fpsShowing = true;
        }
    }

    // 重写PopupWindow显示方法，在显示时启用fps更新
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        startFpsUpdate();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        startFpsUpdate();
    }

    // 重写PopupWindow隐藏方法，在隐藏时关闭fps更新
    @Override
    public void dismiss() {
        super.dismiss();
        // 启用中断
        fpsUpdateInterrupted = true;
        // 设定未显示状态
        fpsShowing = false;
    }

    public interface FpsRunnable {
        void run(float fps);
    }

}
