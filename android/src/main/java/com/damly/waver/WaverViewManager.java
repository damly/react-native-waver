package com.damly.waver;


import android.support.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class WaverViewManager extends SimpleViewManager<WaverView> {

    @Override
    public String getName() {
        return "WaverView";
    }

    @Override
    protected WaverView createViewInstance(ThemedReactContext reactContext) {
        return new WaverView(reactContext);
    }

    @ReactProp(name = "color", customType = "Color")
    public void setColor(WaverView view, @Nullable Integer color) {
        view.setColor(color);
    }

    @ReactProp(name = "value")
    public void setValue(WaverView view, @Nullable Integer value) {
        if(value > 30)
            value = 30;
        else if(value < 5)
            value = 5;
        view.setVolume(value);
    }
}