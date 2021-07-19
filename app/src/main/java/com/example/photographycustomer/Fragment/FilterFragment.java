package com.example.photographycustomer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photographycustomer.Adapter.FilterAdapter;
import com.example.photographycustomer.Interface.BottomSheetClickListener;
import com.example.photographycustomer.R;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends Fragment implements BottomSheetClickListener {

    private List<String> plansList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoboButton apply,revert;
    private String selectedItem;
    private BottomSheetClickListener bottomSheetClickListener;

    public FilterFragment(BottomSheetClickListener bottomSheetClickListener) {
        this.bottomSheetClickListener = bottomSheetClickListener;
    }

    public FilterFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
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
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plansList.add("Gold");
        plansList.add("Silver");
        plansList.add("Platinum");

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.filter_rv);
        apply = view.findViewById(R.id.apply_btn);
        revert = view.findViewById(R.id.revert_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        FilterAdapter filterAdapter = new FilterAdapter(plansList,this);
        recyclerView.setAdapter(filterAdapter);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedItem == null){
                    Toast.makeText(getContext(),"Please Select Plan",Toast.LENGTH_SHORT).show();
                }else{
                    getActivity().getSupportFragmentManager().popBackStack();
                    bottomSheetClickListener.onBottomSheetClickListener(selectedItem);
                }
            }
        });

        revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.remove("lastSelectedPlan");
                getActivity().getSupportFragmentManager().popBackStack();
                bottomSheetClickListener.onBottomSheetClickListener("revert");
            }
        });
    }

    @Override
    public void onBottomSheetClickListener(String item) {
        this.selectedItem = item;
    }
}