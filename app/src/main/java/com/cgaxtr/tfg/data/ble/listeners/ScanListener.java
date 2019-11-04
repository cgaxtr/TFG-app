package com.cgaxtr.tfg.data.ble.listeners;

import com.cgaxtr.tfg.data.model.Device;

import java.util.List;

public interface ScanListener {
    void onStop(List<Device> devices);
}
