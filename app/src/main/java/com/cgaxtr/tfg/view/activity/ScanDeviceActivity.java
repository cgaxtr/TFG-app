package com.cgaxtr.tfg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.data.model.Device;
import com.cgaxtr.tfg.presenter.ScanDevicePresenter;
import com.cgaxtr.tfg.utils.LocationUtils;
import com.cgaxtr.tfg.utils.PermissionsUtils;
import com.cgaxtr.tfg.view.adapter.LeDeviceListAdapter;
import com.cgaxtr.tfg.view.views.ScanActivityView;

import java.util.List;

public class ScanDeviceActivity extends AppCompatActivity implements ScanActivityView {

    private static final int PERMISSION_BLUETOOTH_CODE = 20;
    private static final int GPS_CODE = 30;

    ListView listView;
    LeDeviceListAdapter adapter;
    ScanDevicePresenter presenter;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_device);

        initUI();

        presenter = new ScanDevicePresenter(this);
        presenter.onCreate();
    }

    private void initUI() {
        listView = findViewById(R.id.deviceScanList);
        adapter = new LeDeviceListAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Device device = (Device) adapter.getItem(i);
                presenter.associateDevice(device);
            }
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Buscando");
        progress.setMessage("Buscando dispositivos cercanos...");
        progress.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showResultScan(List<Device> list) {
        Log.d("RESULT", list.toString());
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showError(String error) {
        Toast t = Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public void statLoading() {
        progress.show();
    }

    @Override
    public void stopLoading() {
        progress.dismiss();
    }

    @Override
    public void requestPermissions() {
        if (PermissionsUtils.shouldAskForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionsUtils.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_BLUETOOTH_CODE);
            PermissionsUtils.markedPermissionAsAsked(this, Manifest.permission.ACCESS_FINE_LOCATION);
        }else if (!PermissionsUtils.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            presenter.permissionsGranted(false);
        }else{
            presenter.permissionsGranted(true);
        }
    }

    @Override
    public void requestLocation() {
        if (!LocationUtils.statusCheck(this)) {
            LocationUtils.requestGps(this, GPS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_BLUETOOTH_CODE:
                presenter.permissionsGranted(grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_CODE){
            presenter.locationGranted(LocationUtils.statusCheck(this));
        }
    }
}
