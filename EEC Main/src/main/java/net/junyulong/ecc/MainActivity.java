package net.junyulong.ecc;

import android.content.Context;
import android.os.Bundle;
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

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends XServerDisplayActivity {

    private EecContext mContext;

    private EEC mEEC;

    private EecUiOverlay overlay;

    private ViewOfXServer viewOfXServer;

    private TextView textLogShower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        overlay = new EecUiOverlay(null) {
            @Override
            public View attach(XServerDisplayActivity xServerDisplayActivity, ViewOfXServer viewOfXServer) {
                View v = super.attach(xServerDisplayActivity, viewOfXServer);
                textLogShower = new TextView(MainActivity.this);
                textLogShower.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                textLogShower.setX(EEC.getInstance().getEecWindowManager().getScreenWidth() / 2f);
                textLogShower.setY(EEC.getInstance().getEecWindowManager().getScreenHeight() / 2f);
                ((ViewGroup) v).addView(textLogShower);
                return v;
            }
        };
        viewOfXServer = new TestViewOfXServer(this, new LogShow() {

            public final LinkedList<String> linkedList = new LinkedList<>();

            @Override
            public void doLog(String log) {
                if (linkedList.size() > 5) {
                    linkedList.poll();
                }
                linkedList.push(log);
                textLogShower.post(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder builder = new StringBuilder();
                        for (String str : linkedList) {
                            builder.append(str).append("\n");
                        }
                        textLogShower.setText(builder.toString());
                    }
                });
                Log.e("ViewFacade", log);
            }
        });
        mContext = EecContext.getContext(null, overlay, this, viewOfXServer);
        mEEC = EEC.attach(mContext);
        setContentView(overlay.attach(this, viewOfXServer));
    }

    private static class TestViewOfXServer extends ViewOfXServer {

        private final ViewFacade viewFacade;

        public TestViewOfXServer(Context context, LogShow logShow) {
            super(context);
            this.viewFacade = new TestViewFacade(logShow);
        }

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