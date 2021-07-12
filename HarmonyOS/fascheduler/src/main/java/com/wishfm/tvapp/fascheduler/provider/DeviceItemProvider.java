package com.wishfm.tvapp.fascheduler.provider;

import com.wishfm.tvapp.fascheduler.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.distributedschedule.interwork.DeviceInfo;

import java.util.List;

public class DeviceItemProvider extends BaseItemProvider {
    private List<DeviceInfo> list;
    private AbilitySlice slice;
    private Component.ClickedListener clickedListener;

    public DeviceItemProvider(List<DeviceInfo> list, AbilitySlice slice, Component.ClickedListener clickedListener) {
        this.list = list;
        this.slice = slice;
        this.clickedListener = clickedListener;
    }

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
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_sample, null, false);
        } else {
            cpt = convertComponent;
        }
        DeviceInfo device = list.get(position);
        Text text = (Text) cpt.findComponentById(ResourceTable.Id_list_title);
        text.setText(""+(position+1)+": "+device.getDeviceName());
        text.setTag(position);
        text.setClickedListener(clickedListener);
        return cpt;
    }
}

