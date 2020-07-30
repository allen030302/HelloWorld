package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityApiAlbums extends AppCompatActivity {

    private TextView titleAPI,textloginOutput;
    private EditText textaccount,textpassword;
    private Button btnlogin;

    MyAPIService myAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_api_albums);
        titleAPI = findViewById(R.id.APItitle);
        textloginOutput = findViewById(R.id.textlogin);
        textaccount = findViewById(R.id.MachanAccount);
        textpassword = findViewById(R.id.MachanPassword);
        btnlogin = findViewById(R.id.Machanbtnlogin);


        // 2. 透過RetrofitManager取得連線基底
        myAPIService = RetrofitManager.getInstance().getAPI();
        // 3. 建立連線的Call，此處設置call為myAPIService中的getAlbums()連線
        Call<Albums> call = myAPIService.getAlbums();
        call.enqueue(new Callback<Albums>() {
            @Override
            public void onResponse(Call<Albums> call, Response<Albums> response) {
                // 連線成功
                String title = response.body().getTitle();
                titleAPI.setText(title);
            }
            @Override
            public void onFailure(Call<Albums> call, Throwable t) {
                // 連線失敗
                titleAPI.setText("連線失敗");
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textaccount.getText().toString().equals("") || textpassword.getText().toString().equals("")){
                    Toast.makeText(MainActivityApiAlbums.this, "請輸入帳號密碼", Toast.LENGTH_SHORT).show();
                }
                else {
                    textloginOutput.setText("登入中...");
                    myAPIService = RetrofitManagerMachan.getmInstance().getAPI();
                    Call<MachanLoginMod> callLogin = myAPIService.postLogin(textaccount.getText().toString(),textpassword.getText().toString());
                    callLogin.enqueue(new Callback<MachanLoginMod>() {
                        @Override
                        public void onResponse(Call<MachanLoginMod> call, Response<MachanLoginMod> response) {
                            String data;
                            try {
                                data = response.errorBody().string();
                                Toast.makeText(MainActivityApiAlbums.this,data, Toast.LENGTH_LONG).show();
                                Log.e("sssss", data );
                                try {
                                    JSONObject jerror = new JSONObject(data);
                                    Log.e("JJJJ",jerror.getJSONObject("content"),getString("status"))
                                    //                                    Toast.makeText(MainActivityApiAlbums.this,data, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String status = response.message();
                            Log.d("ppp", "onResponse: "+status);
                            if(status.equals("OK")){
                                String token = response.body().getToken();
                                textloginOutput.setText(token);
                            }
                            else if (status.equals("Unauthorized")){

                                textloginOutput.setText("Unauthorized");

                            }

                        }

                        @Override
                        public void onFailure(Call<MachanLoginMod> call, Throwable t) {
                            textloginOutput.setText("連線失敗");
                        }
                    });
                }

            }
        });



    }
}