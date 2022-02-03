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

package net.junyulong.ecc;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eltechs.axs.activities.XServerDisplayActivity;
import com.eltechs.axs.widgets.viewOfXServer.ViewOfXServer;
import com.eltechs.axs.xserver.ViewFacade;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.EecContext;
import net.junyulong.ecc.uiOverlay.EecUiOverlay;

import java.util.LinkedList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends XServerDisplayActivity {

    // EEC 上下文
    private EecContext mContext;

    // EEC 实例
    private EEC mEEC;

    // EEC UI 封装实例
    private EecUiOverlay overlay;

    // XServer 实例
    private ViewOfXServer viewOfXServer;

    // 显示Log的文本视图
    private TextView textLogShower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // 重写EecUiOverlay的attach方法，额外添加显示输入事件日志的控件
        overlay = new EecUiOverlay(null) {
            @Override
            public View attach(XServerDisplayActivity xServerDisplayActivity, ViewOfXServer viewOfXServer) {
                View v = super.attach(xServerDisplayActivity, viewOfXServer);
                textLogShower = new TextView(MainActivity.this);
                textLogShower.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                textLogShower.setX(EEC.getInstance().getEecWindowManager().getScreenWidth() / 2f);
                textLogShower.setY(EEC.getInstance().getEecWindowManager().getScreenHeight() / 2f);
                textLogShower.setTextColor(Color.WHITE);
                ((ViewGroup) v).addView(textLogShower);
                return v;
            }
        };
        viewOfXServer = new TestViewOfXServer(this, new LogShow() {

            public final LinkedList<String> linkedList = new LinkedList<>();

            @Override
            public void doLog(String log) {
                // 队列实现日志，最多保留5条日志
                if (linkedList.size() > 5) {
                    linkedList.poll();
                }
                linkedList.push(log);
                textLogShower.post(() -> {
                    StringBuilder builder = new StringBuilder();
                    for (String str : linkedList) {
                        builder.append(str).append("\n");
                    }
                    textLogShower.setText(builder.toString());
                });
                Log.e("ViewFacade", log);
            }
        });
        // 初始化EEC上下文
        mContext = EecContext.getContext(null, overlay, this, viewOfXServer);
        // 初始化EEC实例
        mEEC = EEC.attach(mContext);
        // 绑定视图
        addContentView(overlay.attach(this, viewOfXServer), new ConstraintLayout.LayoutParams(MATCH_PARENT,
                MATCH_PARENT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 卸载EEC实例
        EEC.detach();
        // 卸载视图封装
        overlay.detach();
    }

    private static class TestViewOfXServer extends ViewOfXServer {

        private final ViewFacade viewFacade;

        public TestViewOfXServer(Context context, LogShow logShow) {
            super(context);
            this.viewFacade = new TestViewFacade(logShow);
        }

        // 重写getXServerFacade方法，覆盖原ViewFacade实例
        @Override
        public ViewFacade getXServerFacade() {
            return viewFacade;
        }
    }

    private interface LogShow {
        void doLog(String log);
    }

    private static class TestViewFacade extends ViewFacade {

        private final LogShow logShow;

        public TestViewFacade(LogShow logShow) {
            this.logShow = logShow;
        }

        // 重写ViewFacade的事件处理方法，用于生成输入事件产生的日志
        @Override
        public void injectKeyPress(byte b) {
            super.injectKeyPress(b);
            logShow.doLog("[Type] Press [KeyCode] " + b);
        }

        @Override
        public void injectKeyRelease(byte b) {
            super.injectKeyRelease(b);
            logShow.doLog("[Type] Release [KeyCode] " + b);
        }

        @Override
        public void injectPointerMove(int i, int i2) {
            super.injectPointerMove(i, i2);
            logShow.doLog("[Type] Move [Info] x: " + i + " y: " + i2);
        }

        @Override
        public void injectPointerDelta(int i, int i2) {
            super.injectPointerDelta(i, i2);
            logShow.doLog("[Type] Delta [Info] x: " + i + " y: " + i2);
        }

        @Override
        public void injectPointerWheelUp(int paramInt) {
            super.injectPointerWheelUp(paramInt);
            logShow.doLog("[Type] WheelUp [Info] Delta: " + paramInt);
        }

        @Override
        public void injectPointerWheelDown(int paramInt) {
            super.injectPointerWheelDown(paramInt);
            logShow.doLog("[Type] WheelDown [Info] Delta: " + paramInt);
        }

        @Override
        public void injectPointerButtonPress(int i) {
            super.injectPointerButtonPress(i);
            logShow.doLog("[Type] Press [KeyCode] " + i);
        }

        @Override
        public void injectPointerButtonRelease(int i) {
            super.injectPointerButtonRelease(i);
            logShow.doLog("[Type] Release [KeyCode] " + i);
        }
    }

}