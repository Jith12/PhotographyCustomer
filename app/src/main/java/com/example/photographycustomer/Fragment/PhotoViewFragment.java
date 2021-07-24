package com.example.photographycustomer.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photographycustomer.Adapter.PhotoviewAdapter;
import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Room.CustomerDB;
import com.example.photographycustomer.Room.SelectPhoto;
import com.ornach.nobobutton.NoboButton;

import java.util.List;
import java.util.Objects;

import needle.Needle;
import needle.UiRelatedTask;
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
            //sendtoserver();
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