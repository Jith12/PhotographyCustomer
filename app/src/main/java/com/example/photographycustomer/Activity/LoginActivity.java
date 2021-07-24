package com.example.photographycustomer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.ExistResponse;
import com.example.photographycustomer.Response.FlagResponse;
import com.example.photographycustomer.Response.MobileResponse;
import com.example.photographycustomer.Response.NameResponse;
import com.example.photographycustomer.Response.OtpResponse;
import com.example.photographycustomer.Retrofit.ErrorUtils;
import com.example.photographycustomer.Retrofit.RetrofitAPI;
import com.example.photographycustomer.Retrofit.RetrofitBase;
import com.example.photographycustomer.Retrofit.RetrofitERROR;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Objects;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;
import thebat.lib.validutil.ValidUtils;

public class LoginActivity extends AppCompatActivity {

    private NoboButton back_btn,log_mobile_continue_btn,log_mobile_submit_btn,log_verify_btn;
    private LinearLayout otp_layout,log_mobile_submit_lay;
    private TextFieldBoxes log_mobile_tfb,log_name_tfb,log_otp_tfb;
    private ExtendedEditText log_mobile_eet,log_name_eet,log_otp_eet;
    private OtpTextView otp_view;
    private AppCompatTextView log_skip, log_head;
    private Loader loader;
    private Snackbar snackbar;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loader = new Loader(this);
        snackbar = new Snackbar(this);

        userid = Prefs.getString("c_id","");

        log_head = findViewById(R.id.log_head);
        log_mobile_tfb = findViewById(R.id.log_mobile_tfb);
        log_name_tfb = findViewById(R.id.log_name_tfb);
        log_mobile_eet = findViewById(R.id.log_mobile_eet);
        log_name_eet = findViewById(R.id.log_name_eet);
        otp_view = findViewById(R.id.otp_view);
        log_skip = findViewById(R.id.log_skip);

        otp_layout = findViewById(R.id.otp_layout);
        back_btn = findViewById(R.id.back_btn);
        log_mobile_continue_btn = findViewById(R.id.log_mobile_continue_btn);
        log_mobile_submit_lay = findViewById(R.id.log_mobile_submit_lay);
        log_mobile_submit_btn = findViewById(R.id.log_mobile_submit_btn);
        log_verify_btn = findViewById(R.id.log_verify_btn);

        log_mobile_continue_btn.setOnClickListener(v -> {

            if (ValidUtils.isNetworkAvailable(Objects.requireNonNull(this))){
                if(Validate()){
                    String mobileno = log_mobile_eet.getText().toString();
                    /*if(userid.equals(""))
                    {
                        nextstep(mobileno);
                    }
                    else
                    {
                        nextstepthree(userid);
                    }*/

                    checkvalid(mobileno);
                }
            }else {
                snackbar.warning("Check Your Network Connection");
            }
        });

        log_mobile_submit_btn.setOnClickListener(v -> {
            if(Validatename()){
                String cus_id = Prefs.getString("c_id","");
                String name = log_name_eet.getText().toString();
                nextstepone(cus_id, name);
            }
        });

        back_btn.setOnClickListener(v -> {
            String mobile_num = Prefs.getString("c_mobileno","");
            log_mobile_eet.setText(mobile_num);// Value to display after entered in db
            log_mobile_tfb.setVisibility(View.VISIBLE);
            log_name_tfb.setVisibility(View.GONE);
            otp_layout.setVisibility(View.GONE);
            log_mobile_continue_btn.setVisibility(View.VISIBLE);
            log_mobile_submit_lay.setVisibility(View.GONE);
        });

        log_verify_btn.setOnClickListener(v -> {
            if(Validateotp()){
                String cus_id = Prefs.getString("c_id","");
                String otpno = otp_view.getOTP();
                nextsteptwo(cus_id, otpno);
            }
        });

        log_skip.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            Animatoo.animateFade(LoginActivity.this);
            finish();
        });
    }

    private void checkvalid(String mobileno) {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(this).create(RetrofitAPI.class);
        Call<FlagResponse> call = api.flagset(mobileno);

        call.enqueue(new Callback<FlagResponse>() {
            @Override
            public void onResponse(@NotNull Call<FlagResponse> call, @NotNull Response<FlagResponse> response) {
                try {
                    if (response.isSuccessful()){
                        FlagResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                Prefs.putString("c_id",data.getUserid());
                                nextstepthree(data.getUserid());

                            }else {
                                nextstep(mobileno);
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snackbar.warning(errorUtils.message());
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("Catch Exception",Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<FlagResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> checkvalid(mobileno), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Exception", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void nextstepthree(String userid) {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(this).create(RetrofitAPI.class);
        Call<ExistResponse> call = api.CheckCustomerid(userid);

        call.enqueue(new Callback<ExistResponse>() {
            @Override
            public void onResponse(@NotNull Call<ExistResponse> call, @NotNull Response<ExistResponse> response) {
                try {
                    if (response.isSuccessful()){
                        ExistResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                ExistResponse.Data result = data.getData();

                                Prefs.putBoolean("loggedIn", true);

                                otp_view.setOTP(data.getData().getOtpNo());

                                log_mobile_tfb.setVisibility(View.GONE);
                                log_name_tfb.setVisibility(View.GONE);
                                log_mobile_continue_btn.setVisibility(View.GONE);
                                log_mobile_submit_lay.setVisibility(View.GONE);
                                otp_layout.setVisibility(View.VISIBLE);
                                log_head.setVisibility(View.GONE);

                            }else {
                                snackbar.failure(message);
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snackbar.warning(errorUtils.message());
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("Catch Exception",Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExistResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> nextstep(userid), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Exception", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void nextsteptwo(String cust_id, String otpno) {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(this).create(RetrofitAPI.class);
        Call<OtpResponse> call = api.CheckOtp(cust_id, otpno);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(@NotNull Call<OtpResponse> call, @NotNull Response<OtpResponse> response) {
                try {
                    if (response.isSuccessful()){
                        OtpResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Animatoo.animateFade(LoginActivity.this);
                                finish();

                            }else {
                                snackbar.failure(message);
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snackbar.warning(errorUtils.message());
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("Catch Exception",Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<OtpResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> nextstepone(cust_id, otpno), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Exception", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private boolean Validateotp() {
        String otp_no = otp_view.getOTP();
        if(otp_no.equals(""))
        {
            Toast.makeText(this, "Otp No can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    private void nextstepone(String cust_id, String name) {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(this).create(RetrofitAPI.class);
        Call<NameResponse> call = api.CheckName(cust_id, name);

        call.enqueue(new Callback<NameResponse>() {
            @Override
            public void onResponse(@NotNull Call<NameResponse> call, @NotNull Response<NameResponse> response) {
                try {
                    if (response.isSuccessful()){
                        NameResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){

                                otp_view.setOTP(data.getOtpNo());

                                log_mobile_tfb.setVisibility(View.GONE);
                                log_name_tfb.setVisibility(View.GONE);
                                log_mobile_continue_btn.setVisibility(View.GONE);
                                log_mobile_submit_lay.setVisibility(View.GONE);
                                otp_layout.setVisibility(View.VISIBLE);
                                log_head.setVisibility(View.GONE);

                            }else {
                                snackbar.failure(message);
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snackbar.warning(errorUtils.message());
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("Catch Exception",Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<NameResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> nextstepone(cust_id, name), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Exception", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    private boolean Validatename() {
        String name_enter = log_name_eet.getText().toString();
        if(name_enter.equals(""))
        {
            log_name_tfb.setError("Name filed can't be empty", true);
            return false;
        }
        else
        {
            return true;
        }
    }

    private void nextstep(String mobileno) {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(this).create(RetrofitAPI.class);
        Call<MobileResponse> call = api.CheckMobileNo(mobileno);

        call.enqueue(new Callback<MobileResponse>() {
            @Override
            public void onResponse(@NotNull Call<MobileResponse> call, @NotNull Response<MobileResponse> response) {
                try {
                    if (response.isSuccessful()){
                        MobileResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                MobileResponse.Data result = data.getData();

                                Prefs.putString("c_id", result.getId());
                                Prefs.putString("c_mobileno", result.getMobileNo());
                                Prefs.putBoolean("loggedIn", true);

                                log_mobile_tfb.setVisibility(View.GONE);
                                log_name_tfb.setVisibility(View.VISIBLE);
                                otp_layout.setVisibility(View.GONE);
                                log_mobile_continue_btn.setVisibility(View.GONE);
                                log_mobile_submit_lay.setVisibility(View.VISIBLE);

                            }else {
                                snackbar.failure(message);
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snackbar.warning(errorUtils.message());
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("Catch Exception",Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<MobileResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> nextstep(mobileno), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Exception", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private boolean Validate() {
        String mobile_num = log_mobile_eet.getText().toString();
        if(mobile_num.equals(""))
        {
            log_mobile_tfb.setError("Mobile No can't be empty", true);
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}