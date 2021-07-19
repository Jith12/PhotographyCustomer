package com.example.photographycustomer.Retrofit;

import com.example.photographycustomer.Response.AllViewResponse;
import com.example.photographycustomer.Response.ExistResponse;
import com.example.photographycustomer.Response.MobileResponse;
import com.example.photographycustomer.Response.NameResponse;
import com.example.photographycustomer.Response.OtpResponse;
import com.example.photographycustomer.Response.ProfSaveResponse;
import com.example.photographycustomer.Response.ProfViewResponse;
import com.example.photographycustomer.Response.SixViewResponse;
import com.example.photographycustomer.Response.SliderResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/register")
    Call<MobileResponse> CheckMobileNo(
            @Field("mobileno") String mobileno
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/existregister")
    Call<ExistResponse> CheckCustomerid(
            @Field("customerid") String customerid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/loginname")
    Call<NameResponse> CheckName(
            @Field("customerid") String customerid,
            @Field("name") String name
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/verifyOtp")
    Call<OtpResponse> CheckOtp(
            @Field("customerid") String customerid,
            @Field("otp") String otpno
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @GET("customer/Banner")
    Call<SliderResponse> slider();

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/homeSix")
    Call<SixViewResponse> homeSix(
            @Field("customerid") String customerid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/homeViewall")
    Call<AllViewResponse> homeall(
            @Field("customerid") String customerid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/sortBy")
    Call<AllViewResponse> sortName(
            @Field("customerid") String customerid,
            @Field("sortvalue") String sortvalue
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/filterBy")
    Call<AllViewResponse> filterName(
            @Field("customerid") String customerid,
            @Field("filtervalue") String filtervalue
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/profileView")
    Call<ProfViewResponse> profileview(
            @Field("customerid") String customerid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:photography"})
    @FormUrlEncoded
    @POST("customer/profileSave")
    Call<ProfSaveResponse> profilesave(
            @Field("customerid") String customerid,
            @Field("name") String name,
            @Field("altermobileno") String altermobileno,
            @Field("mobileno") String mobileno
    );
}
