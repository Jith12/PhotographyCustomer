package com.example.photographycustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.photographycustomer.Others.Constants;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.SixViewResponse;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final Context context;
    private final List<SixViewResponse.Datum> homeResponseList;

    public HomeAdapter(Context context, List<SixViewResponse.Datum> homeResponseList) {
        this.context = context;
        this.homeResponseList = homeResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_alloption, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SixViewResponse.Datum homepage = homeResponseList.get(position);

        holder.Name.setText(homepage.getName());
        holder.Location.setText(homepage.getCityName());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage);

        if(homepage.getRole().equals("1"))
        {
            Glide.with(context)
                    .load(Constants.CUS_PROFILE_S_URL+homepage.getProfileImage())
                    .apply(options)
                    .into(holder.Image);
        }
        else
        {
            Glide.with(context)
                    .load(Constants.CUS_PROFILE_L_URL+homepage.getProfileImage())
                    .apply(options)
                    .into(holder.Image);
        }
    }

    @Override
    public int getItemCount() {
        return homeResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView Name;
        private final AppCompatTextView Location;
        private final AppCompatImageView Image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Image = itemView.findViewById(R.id.image);
            Location = itemView.findViewById(R.id.location);
        }
    }
}
