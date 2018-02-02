package com.bcit.righttrack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**************************************/

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView bottomNavBar = findViewById(R.id.navigation);
        // Creates a listener for the bottom navigation bar
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.homeTab:
                    mTextMessage.setText(R.string.home_tab);
                    return true;

                case R.id.tab2:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;

                case R.id.tab3:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;

                case R.id.tab4:
                    mTextMessage.setText(R.string.tab4);
                    return true;
            }
            return false;
        }
    };

}
