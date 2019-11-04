package com.cgaxtr.tfg.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.presenter.LoginPresenter;
import com.cgaxtr.tfg.view.views.LoginActivityView;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    private EditText email, password;
    private Button login;
    private ProgressBar progressBar;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
        initUI();
        presenter.checkLogin();
    }

    private void initUI(){
        email = findViewById(R.id.logEmail);
        password = findViewById(R.id.logPassword);
        login = findViewById(R.id.logLogin);
        progressBar = findViewById(R.id.logProgressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString();
                String pass = password.getText().toString();

                presenter.login(user, pass);
            }
        });
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showScanDeviceActivity() {
        Intent i = new Intent(this, ScanDeviceActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showError(String errorMsg) {
        Snackbar.make(findViewById(R.id.activity_login), errorMsg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopShowingLoading() {
        progressBar.setVisibility(View.GONE);
    }

}
