package com.cgaxtr.tfg.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.data.model.Question;
import com.cgaxtr.tfg.presenter.TestPresenter;
import com.cgaxtr.tfg.view.adapter.TestAdapter;
import com.cgaxtr.tfg.view.views.TestFragmentView;

import java.util.List;

public class TestFragment extends Fragment implements TestFragmentView {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private TestAdapter adapter;
    private TestPresenter presenter;
    ProgressDialog progress;
    private TextView notification;

    public TestFragment() {
        presenter = new TestPresenter(this);
        presenter.getAvailableTests();
    }

    /*
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();

        return fragment;
    }

     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress =  new ProgressDialog(getContext());
        progress.setTitle("Cargando");
        progress.setMessage("Espere mientras carga..");
        progress.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_test, container, false);

        notification = v.findViewById(R.id.testNotification);
        recyclerView = v.findViewById(R.id.testList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TestAdapter(getContext(), this.presenter);

        recyclerView.setAdapter(adapter);

        presenter.loadTest();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void startLoading() {
        progress.show();
    }

    @Override
    public void stopLoading() {
        progress.dismiss();
    }

    @Override
    public void showList(List<Question> list) {
        adapter.setList(list);
    }

    @Override
    public void showNotification(String text) {
        notification.setText(text);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
