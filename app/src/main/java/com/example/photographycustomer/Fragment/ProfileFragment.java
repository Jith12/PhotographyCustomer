package com.example.photographycustomer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.ProfSaveResponse;
import com.example.photographycustomer.Response.ProfViewResponse;
import com.example.photographycustomer.Retrofit.ErrorUtils;
import com.example.photographycustomer.Retrofit.RetrofitAPI;
import com.example.photographycustomer.Retrofit.RetrofitBase;
import com.example.photographycustomer.Retrofit.RetrofitERROR;
import com.ornach.nobobutton.NoboButton;
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

public class ProfileFragment extends Fragment {

    private Loader loader;
    private Snackbar snackbar;
    private String customerid;
    private CircleImageView prfImage;
    private AppCompatTextView mainName, editprf;
    private AppCompatTextView editName, editMobile, editAlterMobile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snackbar = new Snackbar(getActivity());

        customerid = Prefs.getString("c_id","");

        prfImage = view.findViewById(R.id.profile_image);
        mainName = view.findViewById(R.id.editname_main);
        editprf = view.findViewById(R.id.editprofile);
        editName = view.findViewById(R.id.edit_name);
        editMobile = view.findViewById(R.id.edit_mobileno);
        editAlterMobile = view.findViewById(R.id.edit_altermobile);

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            editprofile();
        }else {
            snackbar.warning("Check Your Network Connection");
        }

        editprf.setOnClickListener(v -> {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            EditDialog editDialog = EditDialog.newInstance();
            editDialog.setCancelable(true);
            editDialog.show(ft, "dialog");
        });
    }

    private void editprofile() {
        loader.show("");
        RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<ProfViewResponse> call = api.profileview(customerid);

        call.enqueue(new Callback<ProfViewResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfViewResponse> call, @NotNull Response<ProfViewResponse> response) {
                try {
                    if (response.isSuccessful()){
                        ProfViewResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                ProfViewResponse.Data result = data.getData();

                                mainName.setText(result.getName());

                                editName.setText(result.getName());
                                editMobile.setText(String.valueOf(result.getMobileNo()));
                                editAlterMobile.setText(result.getAlterMobileNo());

                                CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getActivity());
                                circularProgressDrawable.setStrokeWidth(5.0f);
                                circularProgressDrawable.setCenterRadius(15.0f);
                                circularProgressDrawable.start();

                                Glide.with(getActivity())
                                        .load(R.drawable.noimage)
                                        .fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(circularProgressDrawable)
                                        .into(prfImage);

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
            public void onFailure(@NotNull Call<ProfViewResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snackbar.warning("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> editprofile(), 3000);
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

    public static class EditDialog extends DialogFragment {

        private CircleImageView prfImage;
        private AppCompatTextView prfMainName;
        private AppCompatEditText prfName, prfMobile, prfAlterMobile;
        private NoboButton prfSave, prfDiscard;
        private Loader loader;
        private Snackbar snackbar;
        private String customerid;

        public EditDialog() {
        }

        public static EditDialog newInstance() {
            EditDialog frag = new EditDialog();
            return frag;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AlertDialogStyle);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_edit_profile, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            loader = new Loader(getActivity());
            snackbar = new Snackbar(getActivity());

            customerid = Prefs.getString("c_id","");

            prfImage = view.findViewById(R.id.profile_images);
            prfMainName = view.findViewById(R.id.edit_mainnames);
            prfName = view.findViewById(R.id.edit_names);
            prfMobile = view.findViewById(R.id.edit_mobilenos);
            prfAlterMobile = view.findViewById(R.id.edit_altermobiles);
            prfSave = view.findViewById(R.id.edit_save);
            prfDiscard = view.findViewById(R.id.edit_discard);

            if (ValidUtils.isNetworkAvailable(requireActivity())){
                editValues();
            }else {
                snackbar.warning("Check Your Network Connection");
            }

            prfSave.setOnClickListener(v -> {
                if(Validate())
                {
                    String names = prfName.getText().toString();
                    String mobilenos = prfMobile.getText().toString();
                    String altermobilenos = prfAlterMobile.getText().toString();

                    updateprofile(customerid, names, mobilenos, altermobilenos);
                }
            });

            prfDiscard.setOnClickListener(v -> {
                getDialog().dismiss();
            });
        }

        private void editValues() {
            loader.show("");
            RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
            Call<ProfViewResponse> call = api.profileview(customerid);

            call.enqueue(new Callback<ProfViewResponse>() {
                @Override
                public void onResponse(@NotNull Call<ProfViewResponse> call, @NotNull Response<ProfViewResponse> response) {
                    try {
                        if (response.isSuccessful()){
                            ProfViewResponse data = response.body();
                            loader.dismiss("");
                            if (data != null){
                                Boolean status = Objects.requireNonNull(data).getStatus();
                                String message = data.getMessage();
                                if (status){
                                    ProfViewResponse.Data result = data.getData();

                                    prfMainName.setText(result.getName());

                                    prfName.setText(result.getName());
                                    prfMobile.setText(String.valueOf(result.getMobileNo()));
                                    prfAlterMobile.setText(result.getAlterMobileNo());

                                    CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getActivity());
                                    circularProgressDrawable.setStrokeWidth(5.0f);
                                    circularProgressDrawable.setCenterRadius(15.0f);
                                    circularProgressDrawable.start();

                                    Glide.with(getActivity())
                                            .load(R.drawable.noimage)
                                            .fitCenter()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .placeholder(circularProgressDrawable)
                                            .into(prfImage);

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
                public void onFailure(@NotNull Call<ProfViewResponse> call, @NotNull Throwable t) {
                    loader.dismiss("");
                    if (t instanceof SocketTimeoutException){
                        snackbar.warning("Timeout, Retrying Again. Please Wait");
                        new Handler().postDelayed(() -> editValues(), 3000);
                    }else if (t instanceof UnknownHostException){
                        snackbar.warning("Unknown Host, Check your URL");
                    }
                    Log.e("Failure Error", Objects.requireNonNull(t.getMessage()));
                }
            });
        }

        private boolean Validate() {

            String names = prfName.getText().toString();
            String altermobileno = prfAlterMobile.getText().toString();

            if(names.isEmpty())
            {
                Toast.makeText(getActivity(), "Name filed can't be empty!..", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                return true;
            }
        }

        private void updateprofile(String customerid, String names, String mobilenos, String altermobilenos) {
            loader.show("");
            RetrofitAPI api = RetrofitBase.getRetrofit(getActivity()).create(RetrofitAPI.class);
            Call<ProfSaveResponse> call = api.profilesave(customerid, names, altermobilenos, mobilenos);

            call.enqueue(new Callback<ProfSaveResponse>() {
                @Override
                public void onResponse(@NotNull Call<ProfSaveResponse> call, @NotNull Response<ProfSaveResponse> response) {
                    try {
                        if (response.isSuccessful()){
                            ProfSaveResponse data = response.body();
                            loader.dismiss("");
                            if (data != null){
                                Boolean status = Objects.requireNonNull(data).getStatus();
                                String message = data.getMessage();
                                if (status){

                                    snackbar.success(message);

                                    HomeFragment fragment2 = new HomeFragment();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container, fragment2);
                                    fragmentTransaction.commit();

                                    getDialog().dismiss();

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
                public void onFailure(@NotNull Call<ProfSaveResponse> call, @NotNull Throwable t) {
                    loader.dismiss("");
                    if (t instanceof SocketTimeoutException){
                        snackbar.warning("Timeout, Retrying Again. Please Wait");
                        new Handler().postDelayed(() -> updateprofile(customerid, names, altermobilenos, mobilenos), 3000);
                    }else if (t instanceof UnknownHostException){
                        snackbar.warning("Unknown Host, Check your URL");
                    }
                    Log.e("Failure Error", Objects.requireNonNull(t.getMessage()));
                }
            });
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }

}