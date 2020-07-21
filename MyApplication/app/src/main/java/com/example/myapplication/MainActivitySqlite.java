package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySqlite extends AppCompatActivity {

    private static  String DATABASE_TABLE = "Users";
    private static final String DATABASE_NAME = "DATABASEBMI";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private MyDBHelper dbHelper;
    private EditText account,password;
    private TextView output,sqlcommand;
    private Spinner accountselect;
    private Button buttonInsert,buttonupdate,buttondelete,buttonselect,buttonrun;
    private Integer mode = 0;
    private String accountV,passwordV;
    private String[] AccountArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sqlite);

        // 建立SQLiteOpenHelper物件
        dbHelper = new MyDBHelper(this,DATABASE_NAME,null,DATABASE_VERSION,DATABASE_TABLE);
        db = dbHelper.getWritableDatabase(); // 開啟資料庫
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        output = (TextView)findViewById(R.id.sqliteoutput);
        sqlcommand = (TextView)findViewById(R.id.sqlitecommand);
        accountselect = (Spinner)findViewById(R.id.spinnersqliteaccount);
        buttonInsert = (Button)findViewById(R.id.buttonsqliteinsert);
        buttonupdate = (Button)findViewById(R.id.buttonsqliteupdate);
        buttondelete = (Button)findViewById(R.id.buttonsqlitedelete);
        buttonselect = (Button)findViewById(R.id.buttonsqliteselect);
        buttonrun = (Button)findViewById(R.id.buttonsqliterun);
        accountselect.setEnabled(false);
        SqlgetAccount();

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonInsert.setTextColor(Color.RED);
                account.setEnabled(true);
                password.setEnabled(true);
                mode = 1;
                account.setText("");
            }
        });

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonupdate.setTextColor(Color.RED);
                password.setEnabled(true);
                accountselect.setEnabled(true);
                mode = 2;
                account.setText(accountselect.getSelectedItem().toString());
                password.setText("");
            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttondelete.setTextColor(Color.RED);
                accountselect.setEnabled(true);
                mode = 3;
            }
        });

        buttonselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonselect.setTextColor(Color.RED);
                mode = 4;
            }
        });

        buttonrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                accountV = account.getText().toString();
                passwordV = password.getText().toString();
                switch (mode){
                    case 1:
                        if(accountV != null && !accountV.equals("") && passwordV != null && !passwordV.equals("")) {
                            long id;
                            cv.put("account",account.getText().toString());
                            cv.put("password",password.getText().toString());
                            id = db.insert(DATABASE_TABLE,null,cv);
                            output.setText("帳號新增成功:"+id);
                            sqlcommand.setText("INSERT INTO Users account,password Values "+account.getText().toString()+","+password.getText().toString());
                            SqlgetAccount();
                        }
                        else {
                            //Toast.makeText(view.getContext(),"請輸入帳號密碼",Toast.LENGTH_SHORT).show();
                            output.setText("請輸入帳號密碼!!");
                        }
                        break;
                    case 2:
                        if(accountV != null && !accountV.equals("") && passwordV != null && !passwordV.equals("")){
                            int count;
                            cv.put("password",passwordV);
                            count = db.update(DATABASE_TABLE,cv,"account="+accountV,null);
                            output.setText("更新紀錄成功:"+count);
                            sqlcommand.setText("UPDATE Users Set password=" + passwordV + "WHERE account = "+accountV);
                        }
                        break;
                    case 3:

                        break;
                    case 4:
                        SqlQuery("SELECT * FROM " + DATABASE_TABLE);
                        sqlcommand.setText("SELECT * FROM Users");
                        break;
                }
            }
        });

        accountselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                account.setText(accountselect.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void SqlQuery(String sql){
        String[] colNames;
        String str = "";
        Cursor c = db.rawQuery(sql,null);
        colNames = c.getColumnNames();
        // 顯示欄位名稱
        for (int i = 0;i<colNames.length;i++){
            str += colNames[i] + "\t\t\t\t";
            c.moveToFirst(); //第1筆
        }
        str += "\n";
        for (int i = 0; i < c.getCount(); i++) {
            str += c.getString(0) + "\t\t\t\t";
            str += c.getString(1) + "\t\t\t\t";
            str += c.getString(2) + "\n";
            c.moveToNext();  // 下一筆
        }
        output.setText(str.toString());
    }

    public void SqlgetAccount(){
        Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE,null);
        AccountArray = new String[c.getCount()];
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            AccountArray[i] = c.getString(1);
            c.moveToNext();
        }
        ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,AccountArray);
        accountselect.setAdapter(nAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    private void cleanbutton(){
        buttonInsert.setTextColor(Color.BLACK);
        buttonupdate.setTextColor(Color.BLACK);
        buttondelete.setTextColor(Color.BLACK);
        buttonselect.setTextColor(Color.BLACK);
        buttonrun.setEnabled(true);
        account.setEnabled(false);
        password.setEnabled(false);
        accountselect.setEnabled(false);

    }
}