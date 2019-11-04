package com.cgaxtr.tfg.view.activity;

import android.os.Bundle;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.view.fragment.HomeFragment;
import com.cgaxtr.tfg.view.fragment.ProfileFragment;
import com.cgaxtr.tfg.view.fragment.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements TestFragment.OnFragmentInteractionListener{

    private int navIndex = 1;
    private Fragment homeFragment, testFragment, profileFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            boolean selectable = false;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navIndex = 1;
                    selectable = true;
                    break;
                case R.id.navigation_test:
                    navIndex = 2;
                    selectable = true;
                    break;
                case R.id.navigation_notifications:
                    navIndex = 3;
                    selectable = true;
                    break;
            }

            loadFragment();
            return selectable;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        homeFragment = new HomeFragment();
        testFragment = new TestFragment();
        profileFragment = new ProfileFragment();

        loadFragment();
    }

    private void loadFragment(){
        Fragment fg = null;

        switch (navIndex){
            case 1:
                fg = homeFragment;
                break;
            case 2:
                fg = testFragment;
                break;
            case 3:
                fg = profileFragment;
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fg);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
