package com.cgaxtr.tfg.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.presenter.HomePresenter;
import com.cgaxtr.tfg.view.views.HomeFragmentView;

public class HomeFragment extends Fragment implements HomeFragmentView{

    private static final String KEY_STEPS = "steps";

    private HomePresenter presenter;
    private ProgressBar stepsProgress;
    private TextView steps;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        stepsProgress = v.findViewById(R.id.homeStepsProgress);
        steps = v.findViewById(R.id.homeSteps);
        presenter.onCreateView();
        return v;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("ATTACH", "attach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setProgress(final int data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stepsProgress.setProgress(data);
            }
        });
    }

    @Override
    public void setData(final int data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                steps.setText(Integer.toString(data));
            }
        });

    }
}
