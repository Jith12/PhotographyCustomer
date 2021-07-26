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

import com.example.photographycustomer.Fragment.CollectionNameFragment;
import com.example.photographycustomer.Fragment.FamilyFragment;
import com.example.photographycustomer.Fragment.PhotoListFragment;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.CollectionNameResponse;

import java.util.List;

public class CollectionNameAdapter extends RecyclerView.Adapter<CollectionNameAdapter.ViewHolder>{

    private Context context;
    private List<CollectionNameResponse.Datum> collectionlist;

    public CollectionNameAdapter(Context context, List<CollectionNameResponse.Datum> collectionlist) {
        this.context = context;
        this.collectionlist = collectionlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collectionnamelist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionNameAdapter.ViewHolder holder, int position) {

        CollectionNameResponse.Datum item = collectionlist.get(position);

        holder.studioName.setText(item.getCollectionName());

        holder.itemView.setOnClickListener(v -> {
            FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            FamilyFragment familyFragment = FamilyFragment.newInstance(item.getId(), item.getPhotographerId(),
                    item.getCustomerId());
            ft.replace(R.id.container, familyFragment);
            ft.addToBackStack(null);
            ft.commit();
        });

    }

    @Override
    public int getItemCount() {
        return collectionlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView studioName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studioName = itemView.findViewById(R.id.studio_name);
        }
    }
}
