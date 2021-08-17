package com.wishfm.mobileapp.fascheduler.provider;

import com.wishfm.mobileapp.fascheduler.ResourceTable;
import com.wishfm.mobileapp.fascheduler.utils.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.multimodalinput.event.KeyEvent;

import java.util.List;

public class DeviceItemProvider extends BaseItemProvider {
    private List<DeviceInfo> list;
    private AbilitySlice slice;
    private Listener listener;
    private static final int COLOR = 0x33FFFFFF;
    private static final int CORNER_RADIUS = 20;
    private static final int FOCUS_BACKGROUND_COLOR = 0xE5CCCCCC;
    private static final int TEXT_COLOR = 0xFF0C0000;
    private static final int UNFOCUS_BACKGROUND_COLOR = 0x33FFFFFF;
    private static final String TAG = "Device Item";

    public interface Listener {
        void onItemClick(int position);
    }

    public DeviceItemProvider(List<DeviceInfo> list, AbilitySlice slice, Listener listener) {
        this.list = list;
        this.slice = slice;
        this.listener = listener;
    }

    static class ComponentHolder {
        Text deviceName;
        int position;
    }

    private final Component.FocusChangedListener focusChangedListener = (component, hasFocus) -> {
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setCornerRadius(CORNER_RADIUS);
        if (hasFocus) {
            shapeElement.setRgbColor(RgbColor.fromArgbInt(FOCUS_BACKGROUND_COLOR));

            ComponentHolder componentHolder = (ComponentHolder) component.getTag();
            LogUtil.debug(TAG, "Has focus at position: " + componentHolder.position);
            componentHolder.deviceName.setBackground(shapeElement);
        } else {
            shapeElement.setRgbColor(RgbColor.fromArgbInt(UNFOCUS_BACKGROUND_COLOR));

            ComponentHolder componentHolder = (ComponentHolder) component.getTag();
            LogUtil.debug(TAG, "Lost focus at position: " + componentHolder.position);
            componentHolder.deviceName.setBackground(shapeElement);
        }
    };

    private final Component.KeyEventListener itemOnKeyListener = ((component, keyEvent) -> {
        if (keyEvent.isKeyDown()) {
            if (keyEvent.getKeyCode() == KeyEvent.KEY_ENTER || keyEvent.getKeyCode() == KeyEvent.KEY_DPAD_CENTER) {
                ComponentHolder componentHolder = (ComponentHolder) component.getTag();
                this.listener.onItemClick(componentHolder.position);
            }
        }
        return false;
    });

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list != null && position > 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        final ComponentHolder componentHolder;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_sample, null, false);

            componentHolder = new ComponentHolder();
            componentHolder.deviceName = (Text) cpt.findComponentById(ResourceTable.Id_list_title);
            componentHolder.position = position;
            DeviceInfo device = list.get(position);
            componentHolder.deviceName.setTextColor(new Color(TEXT_COLOR));
            componentHolder.deviceName.setText(""+(position+1)+": "+device.getDeviceName());

            ShapeElement shapeElement = new ShapeElement();
            shapeElement.setRgbColor(RgbColor.fromArgbInt(COLOR));
            shapeElement.setCornerRadius(CORNER_RADIUS);
            cpt.setBackground(shapeElement);
            cpt.setFocusable(Component.FOCUS_ENABLE);
            cpt.setFocusChangedListener(focusChangedListener);
            cpt.setKeyEventListener(itemOnKeyListener);
            cpt.setClickedListener(component -> listener.onItemClick(position));

            if (position == 0) {
                cpt.setBindStateChangedListener(new Component.BindStateChangedListener() {
                    @Override
                    public void onComponentBoundToWindow(Component component) {
                        component.requestFocus();
                    }

                    @Override
                    public void onComponentUnboundFromWindow(Component component) {

                    }
                });
            }

            cpt.setTag(componentHolder);
        } else {
            cpt = convertComponent;
        }
        return cpt;
    }
}