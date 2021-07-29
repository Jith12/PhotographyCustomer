package com.example.photographycustomer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.photographycustomer.Adapter.AddsetAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.Others.Constants;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.AddsetResponse;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;

public class AddsetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM5 = "familyid";
    private static final String ARG_PARAM6 = "photographerid";
    private static final String ARG_PARAM7 = "customerid";
    private static final String ARG_PARAM8 = "collectionid";
    private static final String ARG_PARAM9 = "familyname";
    private static final String ARG_PARAM10 = "commonname";

    private Loader loader;
    private Snackbar snackbar;
    private RecyclerView rvAlbumlist;
    private GridLayoutManager gridLayoutManager;
    private AddsetAdapter addsetAdapter;

    // TODO: Rename and change types of parameters
    private String mParam5;
    private String mParam6;
    private String mParam7;
    private String mParam8;
    private String mParam9;
    private String mParam10;

    private CircleImageView studioImage;
    private AppCompatTextView studioName, studioRole;
    private String customerid;

    public AddsetFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddsetFragment newInstance(String param5, String param6, String param7, String param8, String param9, String param10) {
        AddsetFragment fragment = new AddsetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        args.putString(ARG_PARAM9, param9);
        args.putString(ARG_PARAM10, param10);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getString(ARG_PARAM7);
            mParam8 = getArguments().getString(ARG_PARAM8);
            mParam9 = getArguments().getString(ARG_PARAM9);
            mParam10 = getArguments().getString(ARG_PARAM10);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snackbar = new Snackbar(getActivity());

        customerid = Prefs.getString("c_id", "");

        rvAlbumlist = view.findViewById(R.id.recycle_albumlist);
        //studioImage = view.findViewById(R.id.studio_image);
        studioName = view.findViewById(R.id.studio_name);
        //studioRole = view.findViewById(R.id.role_name);

        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rvAlbumlist.setHasFixedSize(true);
        rvAlbumlist.setNestedScrollingEnabled(false);
        rvAlbumlist.setLayoutManager(gridLayoutManager);

        /*CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getActivity());
        circularProgressDrawable.setStrokeWidth(5.0f);
        circularProgressDrawable.setCenterRadius(15.0f);
        circularProgressDrawable.start();

        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        if(mParam4.equals("1"))
        {
            Glide.with(getActivity())
                    .load(Constants.CUS_PROFILE_S_URL+mParam1)
                    .apply(requestOptions)
                    .into(studioImage);
        }
        else if(mParam4.equals("2"))
        {
            Glide.with(getActivity())
                    .load(Constants.CUS_PROFILE_L_URL+mParam1)
                    .apply(requestOptions)
                    .into(studioImage);
        }*/

        studioName.setText(mParam10);
        //studioRole.setText(mParam3);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            addsetlist();
        }else {
            snackbar.warning("Check Your Network Connection");
        }
    }

    private void addsetlist() {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<AddsetResponse> call = api.addsetlist(mParam5, mParam6, mParam7, mParam8);

        call.enqueue(new Callback<AddsetResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddsetResponse> call, @NotNull Response<AddsetResponse> response) {
                try {
                    if (response.isSuccessful()){
                        AddsetResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                List<AddsetResponse.Datum> result = data.getData();

                                addsetAdapter = new AddsetAdapter(getActivity(), result);
                                rvAlbumlist.setAdapter(addsetAdapter);

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
            public void onFailure(@NotNull Call<AddsetResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> addsetlist(), 3000);
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