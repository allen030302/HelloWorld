package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Integer listnullcheck = 0;
    private Integer spinnerPosition;
    private String accountV,passwordV;
    private String[] AccountArray,AccountID,PasswordArray;
    private ListView lv;

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
                buttonrun.setEnabled(true);
                mode = 1;
                account.setText("");
                password.setText("");
            }
        });

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonupdate.setTextColor(Color.RED);
                password.setEnabled(true);
                accountselect.setEnabled(true);
                buttonrun.setEnabled(true);
                mode = 2;
                account.setText(accountselect.getSelectedItem().toString());
                spinnerPosition = accountselect.getSelectedItemPosition();
                password.setText("");
            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttondelete.setTextColor(Color.RED);
                accountselect.setEnabled(true);
                buttonrun.setEnabled(true);
                mode = 3;
            }
        });

        buttonselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonselect.setTextColor(Color.RED);
                buttonrun.setEnabled(true);
                mode = 4;
            }
        });

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        int count;
                        count = db.delete(DATABASE_TABLE,"_id="+AccountID[spinnerPosition],null);
                        output.setText("刪除紀錄成功:"+count);
                        sqlcommand.setText("Delete From Users Where _id  = "+AccountID[spinnerPosition]);
                        cleanbutton();
                        SqlgetAccount();
                        if(listnullcheck >= 1){
                            lv.setAdapter(null);
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        buttonrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv;
                accountV = account.getText().toString();
                passwordV = password.getText().toString();
                output.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                switch (mode){
                    case 1:
                        if(accountV != null && !accountV.equals("") && passwordV != null && !passwordV.equals("")) {
                            if(SqlAccountCheck(accountV) > 0){
                                output.setText("輸入帳號已存在!!");
                                cleanbutton();
                                break;
                            }
                            else {
                                long id;
                                cv = new ContentValues();
                                cv.put("account",account.getText().toString());
                                cv.put("password",password.getText().toString());
                                id = db.insert(DATABASE_TABLE,null,cv);
                                output.setText("帳號新增成功:"+id);
                                sqlcommand.setText("INSERT INTO Users account,password Values "+account.getText().toString()+","+password.getText().toString());
                                cleanbutton();
                                SqlgetAccount();
                                if(listnullcheck >= 1){
                                    lv.setAdapter(null);
                                }
                            }
                            break;
                        }
                        else {
                            //Toast.makeText(view.getContext(),"請輸入帳號密碼",Toast.LENGTH_SHORT).show();
                            output.setText("請輸入帳號密碼!!");
                            break;
                        }
                    case 2:
                        if(accountV != null && !accountV.equals("") && passwordV != null && !passwordV.equals("")){
                            int count;
                            cv = new ContentValues();
                            cv.put("password",passwordV);
                            count = db.update(DATABASE_TABLE,cv,"_id="+AccountID[spinnerPosition],null);
                            output.setText("更新紀錄成功:"+count);
                            sqlcommand.setText("UPDATE Users Set password=" + passwordV + " WHERE account = "+accountV);
                            cleanbutton();
                            SqlgetAccount();
                            if(listnullcheck >= 1){
                                lv.setAdapter(null);
                            }
                        }
                        else {
                            output.setText("請輸入新密碼!!");
                        }
                        break;
                    case 3:
                        if(accountV != null && !accountV.equals("")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("確定要刪除 "+ accountV +" ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                        }
                        else {
                            output.setText("請選擇刪除帳號!!");
                            break;
                        }
                    case 4:
                        //SqlQuery("SELECT * FROM " + DATABASE_TABLE);
                        sqlcommand.setText("SELECT * FROM Users");
                        output.setText(null);
                        output.setTextSize(TypedValue.COMPLEX_UNIT_DIP,1);
                        ListView_Customer();
                        listnullcheck += 1;
                        cleanbutton();
                        break;
                }
            }
        });

        accountselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                account.setText(accountselect.getSelectedItem().toString());
                spinnerPosition = accountselect.getSelectedItemPosition();
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
        AccountID = new String[c.getCount()];
        PasswordArray = new String[c.getCount()];
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            AccountID[i] = c.getString(0);
            AccountArray[i] = c.getString(1);
            PasswordArray[i] = c.getString(2);
            c.moveToNext();
        }
        ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,AccountArray);
        accountselect.setAdapter(nAdapter);
    }

    public Integer SqlAccountCheck(String newAccount){
        Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE account = '" +newAccount+"'",null);
        c.moveToFirst();
        return c.getCount();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = dbHelper.getWritableDatabase();
    }

    private void cleanbutton(){
        buttonInsert.setTextColor(Color.BLACK);
        buttonupdate.setTextColor(Color.BLACK);
        buttondelete.setTextColor(Color.BLACK);
        buttonselect.setTextColor(Color.BLACK);
        buttonrun.setEnabled(false);
        account.setEnabled(false);
        password.setEnabled(false);
        accountselect.setEnabled(false);
        password.setText("");
    }

    private void ListView_Customer(){
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.accountlist, new String[]{"ID", "Account", "Password"}, new int[]{R.id.accountID, R.id.listaccount, R.id.listpassword});
        lv = (ListView) findViewById(R.id.accountshowlist);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView txv = (TextView)view.findViewById(R.id.accountID);
                Toast.makeText(MainActivitySqlite.this, "你點擊了:" + txv.getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private List getData() {
        List list = new ArrayList();
        Map map = new HashMap();
        for(int i = 0;i<AccountID.length;i++){
            map = new HashMap();
            map.put("ID", AccountID[i].toString());
            map.put("Account", AccountArray[i].toString());
            map.put("Password", PasswordArray[i].toString());
            list.add(map);
        }
        return list;
    }

}
