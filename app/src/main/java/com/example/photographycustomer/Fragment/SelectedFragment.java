package com.example.photographycustomer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.photographycustomer.R;

public class SelectedFragment extends Fragment {

    private String mParam1;
    private FrameLayout selectFrame;
    private AppCompatImageView selectImage;
    private String funImg, funName;
    private AppCompatTextView collectionName;

    public SelectedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("eventName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectFrame = view.findViewById(R.id.frame_select);
        selectImage = view.findViewById(R.id.function_image);
        collectionName = view.findViewById(R.id.collection_name);

        collectionName.setText(funName);

        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage);

        Glide.with(getActivity())
                .load(FileName+funImg)
                .apply(options)
                .into(selectImage);*/

        selectFrame.setOnClickListener(v -> {
            PhotoViewFragment photoViewFragment = new PhotoViewFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, photoViewFragment);
            fragmentTransaction.commit();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}