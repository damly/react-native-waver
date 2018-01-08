package com.damly.waver;

/**
 * Created by damly on 2018/1/8.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;


public class WaverView extends View {

    private int voiceLineColor = Color.BLACK;

    private Paint paintVoicLine;
    /**
     * 灵敏度
     */
    private int sensibility = 4;

    private float maxVolume = 100;


    private float translateX = 0;
    private boolean isSet = false;

    /**
     * 振幅
     */
    private float amplitude = 5;
    /**
     * 音量
     */
    private float volume = 1;
    private int fineness = 1;
    private float targetVolume = 1;


    private long lastTime = 0;
    private int lineSpeed = 90;

    List<Path> paths = null;

    public WaverView(Context context) {
        super(context);
        initAtts();
    }

    public WaverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAtts();
    }

    public WaverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAtts();
    }

    private void initAtts() {

        voiceLineColor = Color.BLACK;
        maxVolume = 100;
        sensibility = 4;

        lineSpeed = 90;
        fineness = 1;
        paths = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            paths.add(new Path());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawVoiceLine(canvas);
        invalidate();
    }

    private void drawVoiceLine(Canvas canvas) {
        lineChange();
        if (paintVoicLine == null) {
            paintVoicLine = new Paint();
            paintVoicLine.setColor(voiceLineColor);
            paintVoicLine.setAntiAlias(true);
            paintVoicLine.setStyle(Paint.Style.STROKE);
            paintVoicLine.setStrokeWidth(3);
        }
        canvas.save();

        int moveY = getHeight() / 2;
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).reset();
            paths.get(i).moveTo(getWidth(), getHeight() / 2);
        }
        for (float i = getWidth() - 1; i >= 0; i -= fineness) {
            amplitude = 4 * volume * i / getWidth() - 4 * volume * i * i / getWidth() / getWidth();
            for (int n = 1; n <= paths.size(); n++) {
                float sin = amplitude * (float) Math.sin((i - Math.pow(1.22, n)) * Math.PI / 180 - translateX);
                paths.get(n - 1).lineTo(i, (2 * n * sin / paths.size() - 15 * sin / paths.size() + moveY));
            }
        }
        for (int n = 0; n < paths.size(); n++) {

            float progress = 1.0f - (float) n / paths.size();
            float multiplier = Math.min(1.0f, (progress / 3.0f * 2.0f) + (1.0f / 3.0f));
            int alpha = (int)(255 * (n == 0 ? 1.0f : 1.0f * multiplier * 0.4f));

            paintVoicLine.setAlpha(alpha);

            if (paintVoicLine.getAlpha() > 0) {
                canvas.drawPath(paths.get(n), paintVoicLine);
            }
        }
        canvas.restore();
    }

    public void setColor(@Nullable Integer color) {
        this.voiceLineColor = color;
    }

    public void setVolume(@Nullable Integer volume) {
        if (volume > maxVolume * sensibility / 25) {
            isSet = true;
            this.targetVolume = getHeight() * volume / 2 / maxVolume;
        }
    }

    private void lineChange() {
        if (lastTime == 0) {
            lastTime = System.currentTimeMillis();
            translateX += 1.5;
        } else {
            if (System.currentTimeMillis() - lastTime > lineSpeed) {
                lastTime = System.currentTimeMillis();
                translateX += 1.5;
            } else {
                return;
            }
        }

        if (volume < targetVolume && isSet) {
            volume += getHeight() / 30;
        } else {
            isSet = false;
            if (volume <= 5) {
                volume = 5;
            } else {
                if (volume < getHeight() / 30) {
                    volume -= getHeight() / 60;
                } else {
                    volume -= getHeight() / 30;
                }
            }
        }
    }
}
