package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonInsert.setTextColor(Color.RED);
            }
        });

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonupdate.setTextColor(Color.RED);
            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttondelete.setTextColor(Color.RED);
            }
        });

        buttonselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanbutton();
                buttonselect.setTextColor(Color.RED);
            }
        });

        buttonrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();

    }

    private void cleanbutton(){
        buttonInsert = (Button)findViewById(R.id.buttonsqliteinsert);
        buttonupdate = (Button)findViewById(R.id.buttonsqliteupdate);
        buttondelete = (Button)findViewById(R.id.buttonsqlitedelete);
        buttonselect = (Button)findViewById(R.id.buttonsqliteselect);
        buttonrun = (Button)findViewById(R.id.buttonsqliterun);
        buttonInsert.setTextColor(Color.BLACK);
        buttonupdate.setTextColor(Color.BLACK);
        buttondelete.setTextColor(Color.BLACK);
        buttonselect.setTextColor(Color.BLACK);
        buttonrun.setEnabled(true);
    }
}