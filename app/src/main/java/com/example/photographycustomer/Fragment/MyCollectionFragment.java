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

import com.example.photographycustomer.Adapter.CollectionAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.CollectionResponse;
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

public class MyCollectionFragment extends Fragment {

    private RecyclerView rvCollection;
    private GridLayoutManager gridLayoutManager;
    private CollectionAdapter collectionAdapter;
    private Loader loader;
    private Snackbar snack;
    private String customerid;

    public MyCollectionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MyCollectionFragment newInstance(String param1, String param2) {
        MyCollectionFragment fragment = new MyCollectionFragment();
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
        return inflater.inflate(R.layout.fragment_my_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snack = new Snackbar(getActivity());

        customerid = Prefs.getString("c_id", "");

        rvCollection = view.findViewById(R.id.rv_collection);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvCollection.setHasFixedSize(true);
        rvCollection.setNestedScrollingEnabled(false);
        rvCollection.setLayoutManager(gridLayoutManager);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            collectionlist();
        }else {
            snack.warning("Check Your Network Connection");
        }
    }

    private void collectionlist() {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<CollectionResponse> call = api.collectionlist(customerid);

        call.enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(@NotNull Call<CollectionResponse> call, @NotNull Response<CollectionResponse> response) {
                try {
                    if (response.isSuccessful()){
                        CollectionResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                List<CollectionResponse.Datum> result = data.getData();

                                collectionAdapter = new CollectionAdapter(getActivity(), result);
                                rvCollection.setAdapter(collectionAdapter);

                            }else {
                                snack.failure(message);
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.warning(errorUtils.message());
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("Exception Error", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CollectionResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> collectionlist(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.warning("Unknown Host, Check your URL");
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