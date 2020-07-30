package com.example.myapplication;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManagerMachan {
    private static RetrofitManagerMachan mInstanceMachan= new RetrofitManagerMachan();
    private MyAPIService myAPIServiceMachan;

    private RetrofitManagerMachan(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.1.1.71:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIServiceMachan = retrofit.create(MyAPIService.class);
    }

    public static RetrofitManagerMachan getmInstance(){
        return mInstanceMachan;
    }

    public MyAPIService getAPI(){
        return  myAPIServiceMachan;
    }

}
