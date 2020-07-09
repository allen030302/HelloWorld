package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivityTest extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tab_layout;
    private TestMainPagerAdapter TestmainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        tab_layout = findViewById(R.id.tabLayoutMain);
        viewPager = findViewById(R.id.TViewPapper);
        TestmainPagerAdapter = new TestMainPagerAdapter(getSupportFragmentManager());
        TestmainPagerAdapter.addFragment(new BlankFragment(),"BMI計算機");
        TestmainPagerAdapter.addFragment(new BlankFragment2(),"訂餐");
        TestmainPagerAdapter.addFragment(new BlankFragment3(),"設定");

        viewPager.setAdapter(TestmainPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(MainActivityTest.this, ""+tab.getText(), Toast.LENGTH_SHORT).show();

//                switch () {
//                    case R.id.nav1:
//                        gotoBMI();
//                        break;
//                    case R.id.nav2:
//                        gotoOrder();
//                        break;
//                    case R.id.nav3:
//                        gotoSetting();
//                        break;
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

    private BlankFragment BMI;
    private BlankFragment2 Order;
    private BlankFragment3 Setting;

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