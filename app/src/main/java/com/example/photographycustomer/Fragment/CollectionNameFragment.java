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
import com.example.photographycustomer.Adapter.CollectionNameAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.CollectionNameResponse;
import com.example.photographycustomer.Response.CollectionResponse;
import com.example.photographycustomer.Retrofit.ErrorUtils;
import com.example.photographycustomer.Retrofit.RetrofitAPI;
import com.example.photographycustomer.Retrofit.RetrofitBase;
import com.example.photographycustomer.Retrofit.RetrofitERROR;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;

public class CollectionNameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "profile";
    private static final String ARG_PARAM2 = "customerid";
    private static final String ARG_PARAM3 = "photographerid";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private RecyclerView rvCollection;
    private GridLayoutManager gridLayoutManager;
    private CollectionNameAdapter collectionNameAdapter;
    private Loader loader;
    private Snackbar snack;
    private String customerid;

    public CollectionNameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CollectionNameFragment newInstance(String param1, String param2, String param3) {
        CollectionNameFragment fragment = new CollectionNameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snack = new Snackbar(getActivity());

        rvCollection = view.findViewById(R.id.rv_collection);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvCollection.setHasFixedSize(true);
        rvCollection.setNestedScrollingEnabled(false);
        rvCollection.setLayoutManager(gridLayoutManager);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            collectionnamelist();
        }else {
            snack.warning("Check Your Network Connection");
        }
    }

    private void collectionnamelist() {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<CollectionNameResponse> call = api.collectionnamelist(mParam3, mParam2);

        call.enqueue(new Callback<CollectionNameResponse>() {
            @Override
            public void onResponse(@NotNull Call<CollectionNameResponse> call, @NotNull Response<CollectionNameResponse> response) {
                try {
                    if (response.isSuccessful()){
                        CollectionNameResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                List<CollectionNameResponse.Datum> result = data.getData();

                                collectionNameAdapter = new CollectionNameAdapter(getActivity(), result);
                                rvCollection.setAdapter(collectionNameAdapter);

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
            public void onFailure(@NotNull Call<CollectionNameResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> collectionnamelist(), 3000);
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