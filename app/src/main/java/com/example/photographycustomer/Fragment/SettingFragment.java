package com.example.photographycustomer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photographycustomer.Adapter.SettingAdapter;
import com.example.photographycustomer.Model.Setting;
import com.example.photographycustomer.R;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {

    private RecyclerView rvSetting;
    private LinearLayoutManager layoutManager;
    private SettingAdapter adapter;
    private List<Setting> settingList = new ArrayList<>();

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSetting = view.findViewById(R.id.rv_setting);

        layoutManager = new LinearLayoutManager(getActivity());
        rvSetting.setHasFixedSize(true);
        rvSetting.setNestedScrollingEnabled(false);
        rvSetting.setLayoutManager(layoutManager);

        Setting setting = new Setting("Profile");
        settingList.add(setting);
        setting = new Setting("Logout");
        settingList.add(setting);

        adapter = new SettingAdapter(getActivity(), settingList);
        rvSetting.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}