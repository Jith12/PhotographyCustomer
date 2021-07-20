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

import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.AddsetResponse;

import java.util.List;

public class AddsetAdapter extends RecyclerView.Adapter<AddsetAdapter.ViewHolder> {

    private Context context;
    private List<AddsetResponse.Datum> addsetResponseList;

    public AddsetAdapter(Context context, List<AddsetResponse.Datum> addsetResponseList) {
        this.context = context;
        this.addsetResponseList = addsetResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_addset, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AddsetResponse.Datum item = addsetResponseList.get(position);

        holder.studioName.setText(item.getEventName());

        holder.itemView.setOnClickListener(v -> {
            /*FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            CollectionListFragment collectionListFragment = CollectionListFragment.newInstance(item.getType(),
                    String.valueOf(item.getId()));
            ft.replace(R.id.container, collectionListFragment);
            ft.addToBackStack(null);
            ft.commit();*/
        });
    }

    @Override
    public int getItemCount() {
        return addsetResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView studioName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studioName = itemView.findViewById(R.id.studio_name);
        }
    }
}
