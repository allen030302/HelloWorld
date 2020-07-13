package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment3 extends Fragment implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private Spinner sp1;
    private Button btn1;
    private Boolean sp1On = false;
    private EditText text1;
    private GoogleApiClient mGoogleApiClient;
    private Button btnrobot;
    private TextView robotcheck;
    public BlankFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment3 newInstance(String param1, String param2) {
        BlankFragment3 fragment = new BlankFragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void showAlertDialog() {
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.spinnerdialog, null);
        text1 = (EditText)view1.findViewById(R.id.editA);
        Spinner spinner = view1.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.notify_array,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setView(view1)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(),spinner.getSelectedItem().toString() + ":" + text1.getText().toString(),Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_blank3, container, false);


        view = inflater.inflate(R.layout.fragment_blank3,container,false);
        sp1 = (Spinner)view.findViewById(R.id.notify_spinner);
        robotcheck=view.findViewById(R.id.robotcheck);
        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.notify_array,R.layout.support_simple_spinner_dropdown_item);
        sp1.setAdapter(nAdapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp1On == true) Toast.makeText(getActivity(),nAdapter.getItem(i),Toast.LENGTH_SHORT).show();
                else{
                    sp1On = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(),"無選擇",Toast.LENGTH_SHORT).show();
            }
        });
        btn1 = (Button)view.findViewById(R.id.btn01);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();


        btnrobot = (Button)view.findViewById(R.id.btnrobot);
        btnrobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText(0);
                TextView tvResult = (TextView)view.findViewById(R.id.robotcheck);
                SafetyNet.SafetyNetApi.verifyWithRecaptcha(mGoogleApiClient, getResources().getString(R.string.my_site_key)).setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                    @Override
                    public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                        final Status status = recaptchaTokenResult.getStatus();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ((status != null) && status.isSuccess()) {
                                    // 驗證通過
                                    setText(1);
                                } else {
                                    // 驗證失敗
                                    Log.e("MY_APP_TAG", "Error occurred " + "when communicating with the reCAPTCHA service.");
                                    setText(0);
                                }
                            }
                        });
                    }
                });
            }
        });

        return view;

    }

    private void setText(int i) {
        if (i == 1){
            robotcheck.setText("驗證通過");
            robotcheck.setTextColor(Color.GREEN);
        }
        else{
            robotcheck.setText("驗證失敗");
            robotcheck.setTextColor(Color.RED);
        }

    }

    public final void runOnUiThread(Runnable action) {
        action.run();
    }


    // ConnectionCallbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // 已經連線到 Google Services
        Toast.makeText(this.getActivity(), "已經連線到 Google Services", Toast.LENGTH_SHORT).show();
        Log.d("onConnected", "已經連線到 Google Services");
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Google Services連線中斷
        // int參數是連線中斷的代號
        Toast.makeText(this.getActivity(), "Google Services 連線中斷，連線中斷代號 " + i, Toast.LENGTH_SHORT).show();
        Log.d("onConnectionSuspended", "Google Services 連線中斷，連線中斷代號 " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Google Services 連線失敗
        // ConnectionResult 參數是連線失敗的資訊
        Toast.makeText(this.getActivity(), "Google Services 連線失敗，" + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
        Log.w("onConnectionFailed", "Google Services 連線失敗，" + connectionResult.getErrorMessage());
    }
}