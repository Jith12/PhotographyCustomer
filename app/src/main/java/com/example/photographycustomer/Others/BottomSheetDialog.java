package com.example.photographycustomer.Others;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.photographycustomer.Interface.BottomSheetClickListener;
import com.example.photographycustomer.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    public Activity activity;

    private BottomSheetClickListener bottomSheetClickListener;
    private AppCompatTextView ascending, descending;

    public BottomSheetDialog(BottomSheetClickListener bottomSheetClickListener) {
        this.bottomSheetClickListener = bottomSheetClickListener;
    }

    public BottomSheetDialog(){ }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity=(Activity) context;
    }

    public static BottomSheetDialog newInstance() {
        BottomSheetDialog fragment = new BottomSheetDialog();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_bottom_sheet_dialog,
                container, false);

        ascending = v.findViewById(R.id.ascending);
        descending = v.findViewById(R.id.descending);


        ascending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //bottomSheetClickListener.onBottomSheetClickListener("add_member",loginResponse);
                /*MemberCreateFragment fragment = new MemberCreateFragment().newInstance(loginResponse);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
                dismiss();*/
                Toast.makeText(getActivity(), "Ascending", Toast.LENGTH_SHORT).show();
                bottomSheetClickListener.onBottomSheetClickListener("Ascending");
            }
        });

        descending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //bottomSheetClickListener.onBottomSheetClickListener("add_member",loginResponse);
                /*MemberCreateFragment fragment = new MemberCreateFragment().newInstance(loginResponse);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
                dismiss();*/
                Toast.makeText(getActivity(), "Descending", Toast.LENGTH_SHORT).show();
                bottomSheetClickListener.onBottomSheetClickListener("Descending");
            }
        });


        return v;
    }


}
