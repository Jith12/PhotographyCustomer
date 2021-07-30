package com.example.photographycustomer.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photographycustomer.Adapter.PhotoviewAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.SelectedResponse;
import com.example.photographycustomer.Retrofit.ErrorUtils;
import com.example.photographycustomer.Retrofit.RetrofitAPI;
import com.example.photographycustomer.Retrofit.RetrofitBase;
import com.example.photographycustomer.Retrofit.RetrofitERROR;
import com.example.photographycustomer.Room.CustomerDB;
import com.example.photographycustomer.Room.SelectPhoto;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import needle.Needle;
import needle.UiRelatedTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;


public class PhotoViewFragment extends Fragment {

    private Loader loader;
    private Snackbar snackbar;
    private RecyclerView rvPhtoview;
    private GridLayoutManager gridLayoutManager;
    private PhotoviewAdapter photoviewAdapter;
    private NoboButton btnSend;
    private AppCompatTextView functionName;
    private AppCompatImageView folderImage;
    private String selectedimages = "", selectedimages1 = "";
    private String familyid, photographerid, customerid, collectionid, addsetid, emailid;

    public PhotoViewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PhotoViewFragment newInstance() {
        PhotoViewFragment fragment = new PhotoViewFragment();
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
        return inflater.inflate(R.layout.fragment_photo_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snackbar = new Snackbar(getActivity());

        rvPhtoview = view.findViewById(R.id.recycle_photoview);
        folderImage = view.findViewById(R.id.folder_image);
        functionName = view.findViewById(R.id.function_name);
        btnSend = view.findViewById(R.id.btn_send);

        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rvPhtoview.setHasFixedSize(true);
        rvPhtoview.setNestedScrollingEnabled(false);
        rvPhtoview.setLayoutManager(gridLayoutManager);

        Glide.with(getActivity())
                .load(R.drawable.folder)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(folderImage);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            viewlist();
        }else {
            snackbar.warning("Check Your Network Connection");
        }

        btnSend.setOnClickListener(v -> {
            familyid = Prefs.getString("familyid", "");
            photographerid = Prefs.getString("photographerid", "");
            customerid = Prefs.getString("customerid", "");
            collectionid = Prefs.getString("collectionid", "");
            addsetid = Prefs.getString("addsetid", "");
            emailid = Prefs.getString("emailid", "");

            if(emailid.isEmpty())
            {
                showalertbox();
            }
            else
            {
                sendtoserver(familyid, photographerid, customerid, collectionid, addsetid, emailid, selectedimages1);
            }

        });
    }

    private void showalertbox() {

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

                sendtoserver(familyid, photographerid, customerid, collectionid, addsetid, editText.getText().toString()
                        , selectedimages1);
            }
        });
        // create and show
        // the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void sendtoserver(String familyid, String photographerid, String customerid, String collectionid,
                              String addsetid, String emailid, String selectedimages2) {

        loader.show("");

        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<SelectedResponse> call = api.photoselected(photographerid, collectionid, addsetid, customerid, familyid, emailid, selectedimages2);

        call.enqueue(new Callback<SelectedResponse>() {
            @Override
            public void onResponse(@NotNull Call<SelectedResponse> call, @NotNull Response<SelectedResponse> response) {
                try {
                    if (response.isSuccessful()){
                        SelectedResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){

                                showalert(message);

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
            public void onFailure(@NotNull Call<SelectedResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> sendtoserver(familyid, photographerid, customerid, collectionid,
                            addsetid, emailid, selectedimages2), 3000);
                }else if (t instanceof UnknownHostException){
                    snackbar.warning("Unknown Host, Check your URL");
                }
                Log.e("Failure Error", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    private void showalert(String message) {

        // Create an alert builder
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert, null);
        builder.setView(customLayout);
        builder.setCancelable(false);

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                clearallvalues(); // Clear all Selected values from room db after send to photographer

                HomeFragment fragment2 = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment2);
                fragmentTransaction.commit();
            }
        });
        // create and show
        // the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearallvalues() {
        Needle.onBackgroundThread()
                .execute(new UiRelatedTask<Void>() {
                    @Override
                    protected Void doWork() {
                        CustomerDB.getInstance(getActivity()).getRoomDB()
                                .selectPhotoDAO()
                                .clear();
                        return null;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(Void aVoid) {

                    }
                });
    }

    private void viewlist() {

        Needle.onBackgroundThread()
                .execute(new UiRelatedTask<List<SelectPhoto>>() {
                    @Override
                    protected List<SelectPhoto> doWork() {
                        return CustomerDB
                                .getInstance(getActivity())
                                .getRoomDB()
                                .selectPhotoDAO()
                                .getAll();
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void thenDoUiRelatedWork(List<SelectPhoto> photos) {

                        if(photos.size() > 0)
                        {
                            for(int i = 0; i < photos.size(); i++) {
                                selectedimages += photos.get(i).getSelected_images()+",";
                            }

                            selectedimages1 = selectedimages.substring(0, selectedimages.length() - 1);

                            functionName.setText("Selected ( "+ photos.size()+" )");
                            photoviewAdapter = new PhotoviewAdapter(getActivity(), photos);
                            rvPhtoview.setAdapter(photoviewAdapter);

                            btnSend.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            btnSend.setVisibility(View.GONE);
                            functionName.setText("Selected ( 0 )");
                            Toast.makeText(getActivity(), "No Photos Avaliable to Show!..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}