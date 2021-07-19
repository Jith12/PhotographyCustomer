package com.example.photographycustomer.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photographycustomer.Adapter.HomeAdapter;
import com.example.photographycustomer.Adapter.SliderAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.SixViewResponse;
import com.example.photographycustomer.Response.SliderResponse;
import com.example.photographycustomer.Retrofit.ErrorUtils;
import com.example.photographycustomer.Retrofit.RetrofitAPI;
import com.example.photographycustomer.Retrofit.RetrofitBase;
import com.example.photographycustomer.Retrofit.RetrofitERROR;
import com.pixplicity.easyprefs.library.Prefs;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;


public class HomeFragment extends Fragment {

    private RecyclerView rvOption;
    private LinearLayoutManager layoutManager;
    private SliderAdapter sliderAdapter;
    private SliderView homeSlider;
    private Loader loader;
    private Snackbar snackbar;
    private HomeAdapter newHomeAdapter;
    private AppCompatTextView allname, allview;
    private String customerid;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snackbar = new Snackbar(getActivity());

        customerid = Prefs.getString("c_id","");

        homeSlider = view.findViewById(R.id.home_slider);
        rvOption = view.findViewById(R.id.recycle_alloption);
        allname = view.findViewById(R.id.allname);
        allview = view.findViewById(R.id.allview);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvOption.setHasFixedSize(true);
        rvOption.setNestedScrollingEnabled(false);
        rvOption.setLayoutManager(layoutManager);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            slider();
        }else {
            snackbar.warning("Check Your Network Connection");
        }

        allname.setText("All Studio");

        AllView();

        allview.setOnClickListener(v -> {
            DetailFragment detailFragment = new DetailFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, detailFragment);
            fragmentTransaction.commit();
        });
    }

    private void AllView() {
        //loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<SixViewResponse> call = api.homeSix(customerid);

        call.enqueue(new Callback<SixViewResponse>() {

            @Override
            public void onResponse(@NotNull Call<SixViewResponse> call, @NotNull Response<SixViewResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        SixViewResponse data = response.body();

                        if(data != null)
                        {
                            //loader.dismiss("");
                            boolean status = data.getStatus();
                            String message = data.getMessage();
                            if(status)
                            {
                                List<SixViewResponse.Datum> results = data.getData();

                                newHomeAdapter = new HomeAdapter(getActivity(), results);
                                rvOption.setAdapter(newHomeAdapter);
                            }
                            else
                            {
                                //loader.dismiss("");
                                snackbar.failure(message);
                            }
                        }
                    }
                    else
                    {
                        //loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snackbar.failure(errorUtils.message());
                        Log.e("Error Response", errorUtils.message());
                    }
                }
                catch (Exception e)
                {
                    //loader.dismiss("");
                    Log.e("Catch Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SixViewResponse> call, @NotNull Throwable t) {
                //loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> AllView(), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.host("Unknown Host, Check your URL");
                }
                Log.e("Failure Error", t.getMessage());
            }
        });
    }

    private void slider() {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<SliderResponse> call = api.slider();
        call.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(@NotNull Call<SliderResponse> call, @NotNull Response<SliderResponse> response) {
                try {
                    if (response.isSuccessful()){
                        SliderResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                List<SliderResponse.Datum> result = data.getData();
                                sliderAdapter = new SliderAdapter(getActivity());
                                sliderAdapter.renewItems(result);
                                homeSlider.setSliderAdapter(sliderAdapter);
                                homeSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
                                homeSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                homeSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                                homeSlider.setScrollTimeInSec(5);
                                homeSlider.setAutoCycle(true);
                                homeSlider.startAutoCycle();
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
                    Log.e("Exception Error", Objects.requireNonNull(e.getMessage()));
                }
            }
            @Override
            public void onFailure(@NotNull Call<SliderResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> slider(), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}