package com.example.photographycustomer.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.photographycustomer.Activity.LoginActivity;
import com.example.photographycustomer.Fragment.PhotoViewFragment;
import com.example.photographycustomer.Fragment.ProfileFragment;
import com.example.photographycustomer.Model.Setting;
import com.example.photographycustomer.R;
import com.pixplicity.easyprefs.library.Prefs;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;
import java.util.Objects;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    private Context context;
    private List<Setting> settingList;

    public SettingAdapter(Context context, List<Setting> settingList) {
        this.context = context;
        this.settingList = settingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_setting, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Setting setting = settingList.get(position);

        holder.title.setText(setting.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (position == 0){

                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ProfileFragment addsetFragment = ProfileFragment.newInstance();
                ft.replace(R.id.container, addsetFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
            else if (position == 1){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure want to Logout?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    Prefs.clear();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    Animatoo.animateSlideLeft(context);
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton("No", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    *//*MaterialDialog materialDialog = new MaterialDialog.Builder((FragmentActivity)context)
                            .setTitle("LOGOUT")
                            .setMessage("Are you sure want to Logout?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", R.drawable.yes, (dialogInterface, i) -> {
                                Prefs.clear();
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                                Animatoo.animateSlideLeft(context);
                                dialogInterface.dismiss();
                            })
                            .setNegativeButton("No", R.drawable.no, (dialogInterface, which) -> {
                                dialogInterface.dismiss();
                            })
                            .build();
                    materialDialog.show();*//*

                    MaterialDialog mDialog = new MaterialDialog.Builder((FragmentActivity)context)
                            .setTitle("LOGOUT")
                            .setMessage("Are you sure want to Logout?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", R.drawable.yes, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    // Delete Operation
                                }
                            })
                            .setNegativeButton("No", R.drawable.no, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .build();

                    // Show Dialog
                    mDialog.show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("LOGOUT")
                            .setMessage("Are you sure want to Logout?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                        Prefs.clear();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        Animatoo.animateSlideLeft(context);
                        dialogInterface.dismiss();
                    });
                    builder.setNegativeButton("No", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return settingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.set_title);
        }
    }
}
