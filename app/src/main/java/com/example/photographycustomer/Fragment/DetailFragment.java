package com.example.photographycustomer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.photographycustomer.Adapter.DetailedAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.Interface.BottomSheetClickListener;
import com.example.photographycustomer.Others.BottomSheetDialog;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.AllViewResponse;
import com.example.photographycustomer.Retrofit.ErrorUtils;
import com.example.photographycustomer.Retrofit.RetrofitAPI;
import com.example.photographycustomer.Retrofit.RetrofitBase;
import com.example.photographycustomer.Retrofit.RetrofitERROR;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;


public class DetailFragment extends Fragment implements BottomSheetClickListener {

    private RecyclerView rvDetail;
    private GridLayoutManager layoutManager;
    private DetailedAdapter newDetailedAdapter;
    private Loader loader;
    private Snackbar snackbar;
    private String customerid;
    private LinearLayout linearSort, linearFilter;

    public DetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snackbar = new Snackbar(getActivity());

        customerid = Prefs.getString("c_id", "");

        rvDetail = view.findViewById(R.id.rv_details);
        linearSort = view.findViewById(R.id.linear_sort);
        linearFilter = view.findViewById(R.id.linear_filter);

        layoutManager = new GridLayoutManager(getActivity(), 2);
        rvDetail.setHasFixedSize(true);
        rvDetail.setNestedScrollingEnabled(false);
        rvDetail.setLayoutManager(layoutManager);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            AllViews();
        }else {
            snackbar.warning("Check Your Network Connection");
        }

        linearSort.setOnClickListener(v -> {
            BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
            bottomSheet.show(getActivity().getSupportFragmentManager(),
                    "ModalBottomSheet");
        });

        linearFilter.setOnClickListener(v -> {
            Fragment filter = new FilterFragment(this);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, filter).
                    addToBackStack(null).commit();
        });
    }

    private void AllViews() {

        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<AllViewResponse> call = api.homeall(customerid);

        call.enqueue(new Callback<AllViewResponse>() {
            @Override
            public void onResponse(@NotNull Call<AllViewResponse> call, @NotNull Response<AllViewResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        AllViewResponse data = response.body();

                        if(data != null)
                        {
                            boolean status = data.getStatus();
                            String message = data.getMessage();

                            if(status)
                            {
                                loader.dismiss("");

                                List<AllViewResponse.Datum> results = data.getData();

                                newDetailedAdapter = new DetailedAdapter(getActivity(), results);
                                rvDetail.setAdapter(newDetailedAdapter);

                            }
                            else
                            {
                                loader.dismiss("");
                                snackbar.failure(message);
                            }
                        }
                        else
                        {
                            loader.dismiss("");
                            Log.e("Data Error", data.getMessage());
                        }
                    }
                    else
                    {
                        loader.dismiss("");
                        Log.e("Response Error", response.message());
                    }
                }
                catch (Exception e)
                {
                    loader.dismiss("");
                    Log.e("Exception Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AllViewResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> AllViews(), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.host("Unknown Host, Check your URL");
                }
                Log.e("Failure Error", t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Prefs.remove("lastSelectedPlan");
    }

    @Override
    public void onBottomSheetClickListener(String item) {
        switch (item){
            case "revert":
                AllViews();
                break;

            case "Ascending":
            case "Descending":
                SortLstvalue(customerid, item);
                break;

            case "Gold":
            case "Silver":
            case "Platinum":
                getFilterList(customerid, item);
                break;
        }
    }

    private void getFilterList(String customerid, String item) {

        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<AllViewResponse> call = api.filterName(customerid, item);

        call.enqueue(new Callback<AllViewResponse>() {
            @Override
            public void onResponse(@NotNull Call<AllViewResponse> call, @NotNull Response<AllViewResponse> response) {
                try {
                    if (response.isSuccessful()){
                        AllViewResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                List<AllViewResponse.Datum> results = data.getData();

                                newDetailedAdapter = new DetailedAdapter(getActivity(), results);
                                rvDetail.setAdapter(newDetailedAdapter);

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
            public void onFailure(@NotNull Call<AllViewResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> getFilterList(customerid, item), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Error", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    private void SortLstvalue(String customerid, String item) {

        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<AllViewResponse> call = api.sortName(customerid, item);

        call.enqueue(new Callback<AllViewResponse>() {
            @Override
            public void onResponse(@NotNull Call<AllViewResponse> call, @NotNull Response<AllViewResponse> response) {
                try {
                    if (response.isSuccessful()){
                        AllViewResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                List<AllViewResponse.Datum> results = data.getData();

                                newDetailedAdapter = new DetailedAdapter(getActivity(), results);
                                rvDetail.setAdapter(newDetailedAdapter);

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
            public void onFailure(@NotNull Call<AllViewResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> SortLstvalue(customerid, item), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}