package top.bogey.touch_tool_pro.ui.blueprint.pin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import top.bogey.touch_tool_pro.bean.pin.Pin;
import top.bogey.touch_tool_pro.databinding.PinTopBinding;
import top.bogey.touch_tool_pro.ui.blueprint.card.ActionCard;
import top.bogey.touch_tool_pro.utils.DisplayUtils;

@SuppressLint("ViewConstructor")
public class PinTopView extends PinView {
    private final PinTopBinding binding;

    public PinTopView(@NonNull Context context, ActionCard<?> card, Pin pin) {
        super(context, card, pin);

        binding = PinTopBinding.inflate(LayoutInflater.from(context), this, true);
        initRemoveButton(binding.removeButton);
        refreshPinUI();
    }

    @Override
    public void refreshPinUI() {
        binding.pinSlot.setStrokeColor(getPinColor());
        binding.pinSlot.setShapeAppearanceModel(getPinStyle());
        binding.title.setText(pin.getTitle());

        boolean empty = pin.getLinks().isEmpty();
        binding.pinSlot.setCardBackgroundColor(empty ? DisplayUtils.getAttrColor(getContext(), com.google.android.material.R.attr.colorSurfaceVariant, 0) : getPinColor());
    }

    @Override
    public int[] getSlotLocationOnScreen(float scale) {
        int[] location = new int[2];
        binding.pinSlot.getLocationOnScreen(location);
        location[0] += (binding.pinSlot.getWidth() * scale / 2);
        return location;
    }

    @Override
    public ViewGroup getPinViewBox() {
        return null;
    }

}