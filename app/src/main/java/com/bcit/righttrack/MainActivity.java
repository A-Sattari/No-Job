package com.bcit.righttrack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 *
 * @version pre-alpha
 * @author Group JAD
 */
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

            /*
             These two lines below can be combined and written like this
                  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
             but to understand what is going on and how they work I leave them like that.
             */
            // Interface for interacting with Fragment objects inside of an Activity
            FragmentManager fragManager = getSupportFragmentManager();
            // Allows you to perform transactions, such as add and remove fragments
            FragmentTransaction transaction = fragManager.beginTransaction();

            switch (item.getItemId()) {

                case R.id.homeTab:
                    // Replace an existing fragment that was added to a container
                    transaction.replace(R.id.container, new HomePage());
                    transaction.commit();
                    return true;

                case R.id.tab2:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;

                case R.id.tab3:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;

                case R.id.foodBankTab:
                    // Replace an existing fragment that was added to a container
                    transaction.replace(R.id.container, new FoodBank());
                    transaction.commit();
                    return true;
            }
            return false;

        }
    };

}
