package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.stetho.Stetho;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private TextView tt;
    private Button button1;
    private String edit;
    private String edit2,BMi;
    private RecyclerView Re;
    private float BMI;
    private List<String> DataList=new ArrayList<>();
    private MainAdapter mainAdapter;
    private Button btnB;
    private Button btnC;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private WriteData2CSVThread CSV;
    private static final String FILE_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AboutView" + File.separator + "data";
    private static final String FILE_CSV = "about_data.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.edit);
        editText2 = findViewById(R.id.edit2);
        Re = findViewById(R.id.Re1);
       // editText3 = findViewById(R.id.edit3);
        tt=findViewById(R.id.edit3);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Stetho.initializeWithDefaults(this);

        button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            //判斷是否為整數
            public boolean isNumeric(String str){
                for (int i = str.length();--i>=0;){
                    if (!Character.isDigit(str.charAt(i))){
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void onClick(View view) {
                edit=editText.getText().toString();
                edit2=editText2.getText().toString();
                if(edit != null && !edit.equals("") && edit2 != null && !edit2.equals("")){
                    try {
                        BMI = Float.parseFloat(edit)/ ((Float.parseFloat(edit2)*Float.parseFloat(edit2)));
                        BMi=String.valueOf(BMI);
                        tt.setText(BMi);
                        //體重(公斤) / 身高2(公尺2).

                        DataList.add(edit);
                        DataList.add(edit2);
                        DataList.add(BMi);

                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                        itemTouchHelper.attachToRecyclerView(Re);
                        mainAdapter=new MainAdapter(DataList,getApplicationContext());
                        Re.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        Re.setAdapter(mainAdapter);
                    }
                    catch (Exception a){
                        AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                        message.setTitle("警告");
                        message.setMessage("請輸入正確格式");
                        message.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        message.show();
                    }

                }
                else {
                    AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                    message.setTitle("警告");
                    message.setMessage("請輸入完整資料");
                    message.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    message.show();
                }

            }

            // 取得Header
            //View header = navigation_view.getHeaderView(0);
            // 取得Header中的TextView
            //TextView txtHeader = (TextView) header.findViewById(R.id.txtHeader);
            //txtHeader.setText("123");

        });
        btnB = findViewById(R.id.buttonB);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bag=new Bundle();
                bag.putFloat("BMI_EXTRA", BMI);
                bag.putString("Page","1");
                intent.putExtras(bag);
                intent.setClass(MainActivity.this , OrderPage.class);
                startActivity(intent);
            }
        });
        btnC = findViewById(R.id.buttonC);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bag=new Bundle();
//                bag.putFloat("BMI_EXTRA", BMI);
//                bag.putString("Page","2");
//                intent.putExtras(bag);
//                intent.setClass(MainActivity.this , OrderPage.class);
                intent.setClass(MainActivity.this , MainActivityTest.class);
                startActivity(intent);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 點選時收起選單
                drawerLayout.closeDrawer(GravityCompat.START);

                // 取得選項id
                int id = item.getItemId();

                // 依照id判斷點了哪個項目並做相應事件
                if (id == R.id.nav1) {
                    // 按下「BMI計算機」要做的事
                    Toast.makeText(MainActivity.this, "BMI計算機", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (id == R.id.nav2) {
                    // 按下「訂餐」要做的事
                    Toast.makeText(MainActivity.this, "訂餐", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (id == R.id.nav3) {
                    // 按下「設定」要做的事
                    Toast.makeText(MainActivity.this, "設定", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設置要用哪個menu檔做為選單
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.nav1) {
            // 按下「設定」要做的事
            //Toast.makeText(this, "BMI計算機", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this , MainActivity_test2.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.nav2) {
            // 按下「使用說明」要做的事
            Toast.makeText(this, "訂餐", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.nav3) {
            // 按下「關於」要做的事
            CSV = new WriteData2CSVThread(DataList,FILE_FOLDER,FILE_CSV);
            CSV.run();
            Toast.makeText(this, "匯出成功", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            // Step 2-1
            final int fromPos = viewHolder.getAdapterPosition();
            final int toPos = target.getAdapterPosition();
            // move item in `fromPos` to `toPos` in adapter.
            mainAdapter.notifyItemMoved(fromPos, toPos);
            mainAdapter.moveItem(fromPos, toPos);
            return true;// true if moved, false otherwise
//            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mainAdapter.notifyItemRemoved(position);
            mainAdapter.removeItem(position);
        }


    };


}

