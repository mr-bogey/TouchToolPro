package top.bogey.touch_tool.data.action.pin;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import top.bogey.touch_tool.data.action.pin.object.PinObject;

public class Pin<T extends PinObject> implements Parcelable {
    private final String id;
    private final int title;

    private final T value;

    private final PinDirection direction;
    private final PinSlotType slotType;
    private final PinSubType subType;

    private final boolean removeAble;
    private final HashMap<String, String> links = new HashMap<>();

    private transient String actionId;

    public Pin(T value) {
        this(value, 0, PinDirection.IN, PinSlotType.SINGLE, PinSubType.NORMAL, false);
    }

    public Pin(T value, int title) {
        this(value, title, PinDirection.IN, PinSlotType.SINGLE, PinSubType.NORMAL, false);
    }

    public Pin(T value, PinSlotType slotType) {
        this(value, 0, PinDirection.IN, slotType, PinSubType.NORMAL, false);
    }

    public Pin(T value, int title, PinDirection direction) {
        this(value, title, direction, PinSlotType.SINGLE, PinSubType.NORMAL, false);
    }

    public Pin(T value, int title, PinSubType subType) {
        this(value, title, PinDirection.IN, PinSlotType.SINGLE, subType, false);
    }

    public Pin(T value, PinDirection direction, PinSlotType slotType) {
        this(value, 0, direction, slotType, PinSubType.NORMAL, false);
    }

    public Pin(T value, int title, PinDirection direction, PinSlotType slotType) {
        this(value, title, direction, slotType, PinSubType.NORMAL, false);
    }

    public Pin(T value, int title, PinDirection direction, PinSlotType slotType, PinSubType subType, boolean removeAble) {
        this.id = UUID.randomUUID().toString();
        this.title = title;

        this.value = value;

        this.direction = direction;
        this.slotType = slotType;
        this.subType = subType;

        this.removeAble = removeAble;
    }

    protected Pin(Parcel in) {
        id = in.readString();
        title = in.readInt();
        value = in.readParcelable(PinObject.class.getClassLoader());

        direction = in.readParcelable(PinDirection.class.getClassLoader());
        slotType = in.readParcelable(PinSlotType.class.getClassLoader());
        subType = in.readParcelable(PinSubType.class.getClassLoader());

        removeAble = in.readByte() == 1;
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        for (String key : bundle.keySet()) {
            links.put(key, bundle.getString(key));
        }
    }

    public static final Creator<Pin<? extends PinObject>> CREATOR = new Creator<Pin<? extends PinObject>>() {
        @Override
        public Pin<? extends PinObject> createFromParcel(Parcel in) {
            return new Pin<>(in);
        }

        @Override
        public Pin[] newArray(int size) {
            return new Pin[size];
        }
    };


    public HashMap<String, String> addLink(Pin<? extends PinObject> pin) {
        HashMap<String, String> removedLinks = new HashMap<>();
        // 单插槽，需要先移除之前的连接
        if (slotType == PinSlotType.SINGLE) {
            removedLinks.putAll(links);
            links.clear();
        }
        links.put(pin.getActionId(), pin.getId());
        return removedLinks;
    }

    public void removeLink(Pin<? extends PinObject> pin) {
        links.remove(pin.getActionId());
    }

    public String getId() {
        return id;
    }

    public int getTitle() {
        return title;
    }

    public T getValue() {
        if (value == null) throw new RuntimeException("插槽的值为空");
        return value;
    }

    public PinDirection getDirection() {
        return direction;
    }

    public PinSlotType getSlotType() {
        return slotType;
    }

    public PinSubType getSubType() {
        return subType;
    }

    public boolean isRemoveAble() {
        return removeAble;
    }

    public HashMap<String, String> getLinks() {
        return links;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(title);
        dest.writeParcelable(value, flags);

        dest.writeParcelable(direction, flags);
        dest.writeParcelable(slotType, flags);
        dest.writeParcelable(subType, flags);

        dest.writeByte((byte) (removeAble ? 1 : 0));
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : links.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        dest.writeBundle(bundle);
    }
}