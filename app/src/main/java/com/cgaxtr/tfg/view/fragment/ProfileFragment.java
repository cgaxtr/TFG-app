package com.cgaxtr.tfg.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.presenter.ProfilePresenter;
import com.cgaxtr.tfg.view.activity.LoginActivity;
import com.cgaxtr.tfg.view.views.ProfileFragmentView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements ProfileFragmentView {

    ProfilePresenter presenter;

    private BarChart stepsChart;
    private LineChart heartRateChart;
    private Button logOut, disconnectDevice;
    private TextView name, email, birthdate, deviceName, deviceMac, battery;

    public ProfileFragment() {
        presenter = new ProfilePresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        initUI(v);

        return v;
    }


    private void populateLineChart(List<Integer> values, final List<String> labels){
        List<Entry> entries = new ArrayList<>();

        for(int i = 0; i < values.size(); i++){
            entries.add(new Entry(i, values.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "pulsaciones");
        LineData lineData = new LineData(dataSet);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels.get((int) value);
            }
        };

        XAxis xAxis = heartRateChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        heartRateChart.setData(lineData);
        heartRateChart.invalidate(); // refresh
    }

    private void populateBarChart(List<Integer> list, final List<String> labels){
        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            entries.add(new BarEntry(i, list.get(i)));
        }

        BarDataSet set = new BarDataSet(entries, "Heart rate");
        BarData data = new BarData(set);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels.get((int) value);
            }
        };

        XAxis xAxis = stepsChart.getXAxis();
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(entries.size());


        data.setBarWidth(0.9f); // set custom bar width
        stepsChart.setData(data);
        stepsChart.setFitBars(true); // make the x-axis fit exactly all bars
        stepsChart.invalidate(); // refresh
    }

    private void initUI(View v){

        logOut = v.findViewById(R.id.profile_logout);
        disconnectDevice = v.findViewById(R.id.profile_unbound);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logout();
            }
        });

        disconnectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.disconnectDevice();
            }
        });

        name = v.findViewById(R.id.profile_name);
        email = v.findViewById(R.id.profile_email);
        birthdate = v.findViewById(R.id.profile_birthdate);

        deviceName = v.findViewById(R.id.profile_device_name);
        deviceMac = v.findViewById(R.id.profile_device_mac);
        battery = v.findViewById(R.id.profile_device_battery);

        heartRateChart = v.findViewById(R.id.profileHeartRateChart);
        heartRateChart.setDescription(null);
        heartRateChart.getLegend().setEnabled(false);
        heartRateChart.setTouchEnabled(false);

        stepsChart = v.findViewById(R.id.profileStepsChart);
        stepsChart.setDescription(null);
        stepsChart.getLegend().setEnabled(false);
        stepsChart.setTouchEnabled(false);

        //
        //stepsChart.getAxisRight().setDrawGridLines(false);
        //stepsChart.getAxisLeft().setDrawGridLines(false);
        //stepsChart.getXAxis().setDrawGridLines(false);
        //

        presenter.loadUserData();
        presenter.loadDeviceData();
        presenter.loadHeartRateChart();
        presenter.loadStepsChart();
    }

    @Override
    public void setDataUser(String name, String email, String date) {
        this.name.setText(name);
        this.email.setText(email);
        this.birthdate.setText(date);
    }

    @Override
    public void setDataDevice(String name, String mac, String remainBattery) {
        this.deviceName.setText(name);
        this.deviceMac.setText(mac);
        this.battery.setText(remainBattery);
    }

    @Override
    public void setDataStepsChart(List<Integer> values, List<String> xAxis) {
        populateLineChart(values, xAxis);
    }

    @Override
    public void setDataHeartRateChart(List<Integer> values, List<String> xAxis) {
        populateBarChart(values, xAxis);
    }

    @Override
    public void loadLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void showBatteryData(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                battery.setText(value);
            }
        });
    }
}
