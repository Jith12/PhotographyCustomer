package com.example.photographycustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photographycustomer.Fragment.AddsetFragment;
import com.example.photographycustomer.Fragment.FamilyFragment;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.FamilyResponse;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder>{

    private Context context;
    private List<FamilyResponse.Datum> familyList;

    public FamilyAdapter(Context context, List<FamilyResponse.Datum> familyList) {
        this.context = context;
        this.familyList = familyList;
    }

    @NonNull
    @Override
    public FamilyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_family, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyAdapter.ViewHolder holder, int position) {

        FamilyResponse.Datum item = familyList.get(position);

        holder.studioName.setText(item.getFamilyName());

        holder.itemView.setOnClickListener(v -> {
            FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            AddsetFragment addsetFragment = AddsetFragment.newInstance(item.getProfileImage(), item.getStudioName(),
                    item.getRole(), item.getRoleid(), item.getId(), item.getPhotographerId(), item.getCustomerId(), item.getCollectionId(),
                    item.getFamilyName());
            ft.replace(R.id.container, addsetFragment);
            ft.addToBackStack(null);
            ft.commit();
        });

    }

    @Override
    public int getItemCount() {
        return familyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView studioName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studioName = itemView.findViewById(R.id.studio_name);
        }
    }
}
