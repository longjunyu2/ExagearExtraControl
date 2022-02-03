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

package net.junyulong.ecc.core.widgets.textView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.view.View;

import net.junyulong.ecc.core.local.LocalStrings;

public class TipTextView extends android.support.v7.widget.AppCompatTextView {

    private String tipInfo = "";

    private final Paint mPaint;

    public TipTextView(Context context) {
        super(context);
        // 设置监听器
        setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setPositiveButton(LocalStrings.Accept, (dialog, which) -> dialog.dismiss());
            builder.setMessage(tipInfo);
            builder.create().show();
        });

        // 配置画笔
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(50);
        mPaint.setUnderlineText(true);
        mPaint.setTextScaleX(1.2f);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public TipTextView(Context context, String tipInfo) {
        this(context);
        this.tipInfo = tipInfo;
    }

    public String getTipInfo() {
        return this.tipInfo;
    }

    public void setTipInfo(String info) {
        this.tipInfo = info;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float radius = getHeight() * 0.7f / 2f - mPaint.getStrokeWidth();
        canvas.drawCircle(getWidth() - radius * 1.5f, getHeight() / 2f + 5, radius, mPaint);

    }
}
