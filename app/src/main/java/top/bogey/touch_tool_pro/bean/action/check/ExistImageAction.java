package top.bogey.touch_tool_pro.bean.action.check;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.google.gson.JsonObject;

import top.bogey.touch_tool_pro.MainApplication;
import top.bogey.touch_tool_pro.R;
import top.bogey.touch_tool_pro.bean.action.ActionType;
import top.bogey.touch_tool_pro.bean.function.FunctionContext;
import top.bogey.touch_tool_pro.bean.pin.Pin;
import top.bogey.touch_tool_pro.bean.pin.pins.PinArea;
import top.bogey.touch_tool_pro.bean.pin.pins.PinBoolean;
import top.bogey.touch_tool_pro.bean.pin.pins.PinImage;
import top.bogey.touch_tool_pro.bean.pin.pins.PinInteger;
import top.bogey.touch_tool_pro.bean.pin.pins.PinPoint;
import top.bogey.touch_tool_pro.bean.task.TaskRunnable;
import top.bogey.touch_tool_pro.service.MainAccessibilityService;

public class ExistImageAction extends CheckAction{
    private transient Pin imagePin = new Pin(new PinImage(), R.string.pin_image);
    private transient Pin similarPin = new Pin(new PinInteger(85), R.string.action_exist_image_check_subtitle_similar);
    private transient Pin areaPin = new Pin(new PinArea(), R.string.pin_area);
    private transient Pin posPin = new Pin(new PinPoint(), R.string.pin_point, true);
    private transient Pin colorPin = new Pin(new PinBoolean(), R.string.action_exist_image_check_subtitle_with_color);

    public ExistImageAction() {
        super(ActionType.CHECK_EXIST_IMAGE);
        imagePin = addPin(imagePin);
        similarPin = addPin(similarPin);
        areaPin = addPin(areaPin);
        posPin = addPin(posPin);
        colorPin = addPin(colorPin);
    }

    public ExistImageAction(JsonObject jsonObject) {
        super(jsonObject);
        imagePin = reAddPin(imagePin);
        similarPin = reAddPin(similarPin);
        areaPin = reAddPin(areaPin);
        posPin = reAddPin(posPin);
        colorPin = reAddPin(colorPin);
    }

    @Override
    public void calculate(TaskRunnable runnable, FunctionContext context, Pin pin) {
        if (!pin.equals(resultPin)) return;

        PinBoolean result = resultPin.getValue(PinBoolean.class);
        result.setBool(false);

        MainAccessibilityService service = MainApplication.getInstance().getService();
        if (!service.isCaptureEnabled()) return;

        PinImage image = (PinImage) getPinValue(runnable, context, imagePin);
        Bitmap bitmap = image.getImage(service);
        if (bitmap == null) return;

        PinInteger similar = (PinInteger) getPinValue(runnable, context, similarPin);
        PinArea area = (PinArea) getPinValue(runnable, context, areaPin);
        PinBoolean withColor = (PinBoolean) getPinValue(runnable, context, colorPin);
        Rect rect = service.binder.matchImage(bitmap, similar.getValue(), area.getArea(service), withColor.isBool());
        if (rect == null) return;
        result.setBool(true);
        posPin.getValue(PinPoint.class).setPoint(service, rect.centerX(), rect.centerY());
    }

    public Pin getImagePin() {
        return imagePin;
    }

    public Pin getPosPin() {
        return posPin;
    }
}