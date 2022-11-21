package com.example.pramodn.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pramodn.myapplication.fragment.Create;
import com.example.pramodn.myapplication.fragment.Groups;
import com.example.pramodn.myapplication.fragment.Home_fragment;
import com.example.pramodn.myapplication.fragment.Hosted;
import com.example.pramodn.myapplication.fragment.Inbox;
import com.example.pramodn.myapplication.fragment.Search;
import com.example.pramodn.myapplication.fragment.account;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("EventZo");
        setContentView(R.layout.activity_home);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView navigate= findViewById(R.id.topmenu);
        navigate.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new Home_fragment());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new Home_fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.Inbox:
                    fragment = new Inbox();
                    loadFragment(fragment);
                    return true;
                case R.id.account:
                    fragment = new account();
                    loadFragment(fragment);
                    return true;
                case R.id.Create:
                    fragment = new Create();
                    loadFragment(fragment);
                    return true;
                case R.id.groups:
                    fragment = new Groups();
                    loadFragment(fragment);
                    return true;
                case R.id.hosted:
                    fragment = new Hosted();
                    loadFragment(fragment);
                    return true;
                case R.id.search:
                    fragment = new Search();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }

}
