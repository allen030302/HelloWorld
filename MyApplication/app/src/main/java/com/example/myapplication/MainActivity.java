package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.stetho.Stetho;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.androidexception.filepicker.dialog.DirectoryPickerDialog;
import ir.androidexception.filepicker.dialog.SingleFilePickerDialog;

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
    private WriteData2CSVThread CSVW;
    private ReadCSVThread CSVR;
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
        navigation_view.getMenu().getItem(0).setChecked(true);
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
                    //Toast.makeText(MainActivity.this, "BMI計算機", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this , MainActivitySqlite.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.nav2) {
                    // 按下「訂餐」要做的事
                    //Toast.makeText(MainActivity.this, "訂餐", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this , MainActivityApiAlbums.class);
                    startActivity(intent);
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

    String Folder = "";

    private boolean permissionGranted(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void saveCSV(String foldername){
        if (foldername != ""){
            CSVW = new WriteData2CSVThread(DataList,foldername,FILE_CSV);
            CSVW.run();
            Toast toast = Toast.makeText(this.getApplicationContext(), "匯出成功\n" + foldername + "/" + FILE_CSV, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageView = new ImageView(this.getApplicationContext());
            imageView.setImageResource(R.drawable.android);
            toastView.addView(imageView,0);
            toast.show();
        }
        else {
            Toast.makeText(this, "請選擇匯出路徑", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCSV(String filename){
        if (filename != ""){
            CSVR = new ReadCSVThread(filename);
            CSVR.run();
            DataList.clear();
            DataList.addAll(CSVR.arraycsv);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(Re);
            mainAdapter=new MainAdapter(DataList,getApplicationContext());
            Re.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            Re.setAdapter(mainAdapter);

            Toast.makeText(this, "匯入成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "請選擇匯入檔案", Toast.LENGTH_SHORT).show();
        }
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
            //Toast.makeText(this, "訂餐", Toast.LENGTH_SHORT).show();
            //NotificationMessage("訂餐");
            if(permissionGranted()) {
                SingleFilePickerDialog singleFilePickerDialog = new SingleFilePickerDialog(this,
                        () -> Toast.makeText(MainActivity.this, "Canceled!!", Toast.LENGTH_SHORT).show(),
                        files -> openCSV(files[0].getPath()));
                singleFilePickerDialog.show();
            }
            else{
                requestPermission();
            }
            return true;
        }
        else if (id == R.id.nav3) {
            // 按下「關於」要做的事
            Folder = "";
            if(permissionGranted()) {
                DirectoryPickerDialog directoryPickerDialog = new DirectoryPickerDialog(this,
                        () -> Toast.makeText(MainActivity.this, "Canceled!!", Toast.LENGTH_SHORT).show(),
                        files -> saveCSV(files[0].getPath())
                );
                directoryPickerDialog.show();
            }
            else{
                requestPermission();
            }

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

    private static final int PICKFILE_REQUEST_CODE = 0;

    public void onBrowse() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent = Intent.createChooser(intent,"Choose a file");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            if(uri != null){
                Toast.makeText(this, data.getDataString(), Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(this, "無效的檔案路徑!!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "取消選擇檔案!!", Toast.LENGTH_LONG).show();
        }


    }

    public void NotificationMessage(String message){
        NotificationHelper notificationHelper = new NotificationHelper(this);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(message);
        notificationHelper.getManager().notify(1, nb.build());
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程式", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}

