package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderPage extends AppCompatActivity {

    private BlankFragment BMI;
    private BlankFragment2 Order;
    private BlankFragment3 Setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        Float fl=(Float) getIntent().getExtras().getFloat("BMI_EXTRA");
        String page = (String) getIntent().getExtras().getString("Page");
        int P = Integer.parseInt(page);

        Log.d("111", "onCreate: ");

        //TextView tvHello = findViewById(R.id.textHello);

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.navigation);
        //bottomNavigationView.getMenu().setGroupCheckable(0, false, false); //取消他元件自帶的動畫
        //bottomNavigationView.getMenu().getItem(1).setEnabled(false); //呈現不可點擊狀態

        switch (P){
            case 0:
                gotoBMI();
                break;
            case 1:
                gotoOrder();
                break;
            case 2:
                gotoSetting();
                break;
        }
        bottomNavigationView.getMenu().getItem(P).setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.nav1:
                    gotoBMI();
                    break;
                case R.id.nav2:
                    gotoOrder();
                    break;
                case R.id.nav3:
                    gotoSetting();
                    break;
            }
            return true;
        });

    }

    private void gotoBMI() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (BMI == null) BMI = new BlankFragment();

        if (Order != null) fragmentTransaction.hide(Order);
        if (Setting != null) fragmentTransaction.hide(Setting);
        if (BMI.isAdded()){
            fragmentTransaction.show(BMI);
        }
        else{
            fragmentTransaction.add(R.id.fragment1,BMI,"BMI");
        }

//        fragmentTransaction.replace(R.id.fragment1, BMI,"BMI");
//        if (fragmentTransaction.isAddToBackStackAllowed()){
//            fragmentTransaction.addToBackStack(null);
//        }

        fragmentTransaction.commit();
    }


    private void gotoOrder() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (Order == null) Order = new BlankFragment2();

        if (BMI != null) fragmentTransaction.hide(BMI);
        if (Setting != null) fragmentTransaction.hide(Setting);
        if (Order.isAdded()){
            fragmentTransaction.show(Order);
        }
        else{
            fragmentTransaction.add(R.id.fragment1,Order,"Order");
        }

//        fragmentTransaction.replace(R.id.fragment1, Order,"Order");
//        if (fragmentTransaction.isAddToBackStackAllowed()){
//            fragmentTransaction.addToBackStack(null);
//        }

        fragmentTransaction.commit();
    }

    private void gotoSetting() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (Setting == null) Setting = new BlankFragment3();

        if (BMI != null) fragmentTransaction.hide(BMI);
        if (Order != null) fragmentTransaction.hide(Order);
        if (Setting.isAdded()){
            fragmentTransaction.show(Setting);
        }
        else{
            fragmentTransaction.add(R.id.fragment1,Setting,"Setting");
        }

//        fragmentTransaction.replace(R.id.fragment1, Setting,"Setting");
//        if (fragmentTransaction.isAddToBackStackAllowed()){
//            fragmentTransaction.addToBackStack(null);
//        }

        fragmentTransaction.commit();
    }

}