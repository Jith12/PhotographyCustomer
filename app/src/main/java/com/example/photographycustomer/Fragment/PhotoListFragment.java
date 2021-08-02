package com.example.photographycustomer.Fragment;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photographycustomer.Adapter.PhotoAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.PhotoResponse;
import com.example.photographycustomer.Retrofit.ErrorUtils;
import com.example.photographycustomer.Retrofit.RetrofitAPI;
import com.example.photographycustomer.Retrofit.RetrofitBase;
import com.example.photographycustomer.Retrofit.RetrofitERROR;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;

public class PhotoListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "addsetid";
    private static final String ARG_PARAM2 = "customerid";
    private static final String ARG_PARAM3 = "photographerid";
    private static final String ARG_PARAM4 = "collectionid";
    private static final String ARG_PARAM5 = "eventname";
    private static final String ARG_PARAM6 = "familyid";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;

    private Loader loader;
    private Snackbar snackbar;
    private RecyclerView rvPhotos;
    private GridLayoutManager gridLayoutManager;
    private PhotoAdapter photoAdapter;
    private AppCompatTextView functionName;
    private NoboButton btnSubmit;
    private AppCompatImageView folderImage;

    public PhotoListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PhotoListFragment newInstance(String param1, String param2, String param3, String param4,
                                                String param5, String param6) {
        PhotoListFragment fragment = new PhotoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
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
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snackbar = new Snackbar(getActivity());

        rvPhotos = view.findViewById(R.id.recycle_photolist);
        folderImage = view.findViewById(R.id.folder_image);
        functionName = view.findViewById(R.id.function_name);
        btnSubmit = view.findViewById(R.id.btn_submit);

        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rvPhotos.setHasFixedSize(true);
        rvPhotos.setNestedScrollingEnabled(false);
        rvPhotos.setLayoutManager(gridLayoutManager);

        Glide.with(getActivity())
                .load(R.drawable.folder)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(folderImage);

        functionName.setText(mParam5);

        Prefs.putString("familyid", mParam6);
        Prefs.putString("photographerid", mParam3);
        Prefs.putString("customerid", mParam2);
        Prefs.putString("collectionid", mParam4);
        Prefs.putString("eventName", mParam5);
        Prefs.putString("addsetid", mParam1);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            photolist();
        }else {
            snackbar.warning("Check Your Network Connection");
        }

        btnSubmit.setOnClickListener(v -> {

            /*SelectedFragment fragment = new SelectedFragment();
            Bundle arguments = new Bundle();
            arguments.putString("eventName", mParam5);
            fragment.setArguments(arguments);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();*/

            showalert();

        });
    }

    private void showalert() {

        Boolean emailVal = Prefs.getBoolean("emailval",false);

        if(emailVal)
        {
            SelectedFragment fragment = new SelectedFragment();
            Bundle arguments = new Bundle();
            arguments.putString("eventName", mParam5);
            fragment.setArguments(arguments);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }
        else
        {
            // Create an alert builder
            AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
            builder.setTitle("Enter Your Email Id..");

            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
            builder.setView(customLayout);
            builder.setCancelable(false);

            // add a button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // send data from the
                    // AlertDialog to the Activity
                    dialog.dismiss();
                    Prefs.putBoolean("emailval", true);
                    EditText editText = customLayout.findViewById(R.id.editText);
                    Prefs.putString("emailid", editText.getText().toString());

                    SelectedFragment fragment = new SelectedFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("eventName", mParam5);
                    fragment.setArguments(arguments);
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                }
            });
            // create and show
            // the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    private void photolist() {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<PhotoResponse> call = api.photolist(mParam3, mParam4, mParam1, mParam2, mParam6);

        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(@NotNull Call<PhotoResponse> call, @NotNull Response<PhotoResponse> response) {
                try {
                    if (response.isSuccessful()){
                        PhotoResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                List<PhotoResponse.Datum> result = data.getData();

                                photoAdapter = new PhotoAdapter(getActivity(), result);
                                rvPhotos.setAdapter(photoAdapter);

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
            public void onFailure(@NotNull Call<PhotoResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> photolist(), 3000);
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