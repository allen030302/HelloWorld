package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity_test2 extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test2);

        viewPager = findViewById(R.id.T2ViewPapper);
        bottomNavigationView = findViewById(R.id.navigation2);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        TestMainPagerAdapter adapter = new TestMainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BlankFragment(),"BMI");
        adapter.addFragment(new BlankFragment2(),"Order");
        adapter.addFragment(new BlankFragment3(),"Setting");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(menuItem != null){
                    menuItem.setChecked(false);
                }
                else{
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav1:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.nav2:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav3:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return false;
        }
    };
}