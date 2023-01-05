package top.bogey.touch_tool.data.action.start;

import android.os.Parcel;

import top.bogey.touch_tool.R;
import top.bogey.touch_tool.data.Task;
import top.bogey.touch_tool.data.TaskRunnable;
import top.bogey.touch_tool.data.WorldState;
import top.bogey.touch_tool.data.action.pin.Pin;
import top.bogey.touch_tool.data.action.pin.PinSubType;
import top.bogey.touch_tool.data.action.pin.object.PinInteger;
import top.bogey.touch_tool.data.action.pin.object.PinObject;
import top.bogey.touch_tool.data.action.pin.object.PinValueArea;

public class BatteryStartAction extends StartAction {
    private final Pin<? extends PinObject> areaPin;
    private transient boolean inRange = false;

    public BatteryStartAction() {
        super();
        areaPin = addPin(new Pin<>(new PinValueArea(1, 100, 1), R.string.action_battery_start_subtitle_battery));
        titleId = R.string.action_battery_start_title;
    }

    public BatteryStartAction(Parcel in) {
        super(in);
        areaPin = addPin(pinsTmp.remove(0));
        titleId = R.string.action_battery_start_title;
    }

    @Override
    public boolean checkReady(WorldState worldState, Task task) {
        int batteryPercent = worldState.getBatteryPercent();
        int low = ((PinValueArea) areaPin.getValue()).getCurrMin();
        int high = ((PinValueArea) areaPin.getValue()).getCurrMax();
        boolean result = batteryPercent >= low && batteryPercent <= high;
        // 已经执行过了，电量未出范围不再重复执行
        if (result && inRange) return false;
        inRange = result;
        return inRange;
    }
}