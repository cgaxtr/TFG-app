package com.cgaxtr.tfg.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.data.model.Device;

import java.util.ArrayList;
import java.util.List;

public class LeDeviceListAdapter extends BaseAdapter {

    private List<Device> list;
    private Context context;

    public LeDeviceListAdapter(Context context){
        super();
        list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.device_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Device device = list.get(i);
        if(device.getName() != null && device.getName().length() > 0)
            viewHolder.name.setText(device.getName());
        else
            viewHolder.name.setText("Dispositivo desconocido");

        viewHolder.address.setText(device.getMacAddress());

        if(device.getBonded()){
            viewHolder.bonded.setText("Conectado");
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.deviceConnected));
            view.getBackground().setAlpha(80);
        }else{
            viewHolder.bonded.setText("No Conectado");
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.deviceNotConnected));
            view.getBackground().setAlpha(80);
        }

        return view;
    }

    public void setList(List<Device> list){
        this.list = list;
    }

    private class ViewHolder {
        TextView name;
        TextView address;
        TextView bonded;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.device_name);
            address = view.findViewById(R.id.device_address);
            bonded = view.findViewById(R.id.device_bonded);
        }
    }
}