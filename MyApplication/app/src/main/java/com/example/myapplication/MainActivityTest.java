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
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(MainActivityTest.this, ""+tab.getText(), Toast.LENGTH_SHORT).show();
                switch (tab.getPosition()) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }



}