package com.example.photographycustomer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photographycustomer.Helper.Loader;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EalbumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM5 = "familyid";
    private static final String ARG_PARAM6 = "photographerid";
    private static final String ARG_PARAM7 = "customerid";
    private static final String ARG_PARAM8 = "collectionid";
    private static final String ARG_PARAM9 = "familyname";
    private static final String ARG_PARAM10 = "commonname";

    // TODO: Rename and change types of parameters
    private String mParam5;
    private String mParam6;
    private String mParam7;
    private String mParam8;
    private String mParam9;
    private String mParam10;

    private Loader loader;
    private Snackbar snackbar;

    private AppCompatTextView studioName;

    public EalbumFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EalbumFragment newInstance(String param5, String param6, String param7, String param8, String param9, String param10) {
        EalbumFragment fragment = new EalbumFragment();
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
        return inflater.inflate(R.layout.fragment_ealbum, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snackbar = new Snackbar(getActivity());

        studioName = view.findViewById(R.id.studio_name);

        studioName.setText(mParam10);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}