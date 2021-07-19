package com.example.photographycustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.photographycustomer.Others.Constants;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.AllViewResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailedAdapter extends RecyclerView.Adapter<DetailedAdapter.ViewHolder> {

    private final Context context;
    private final List<AllViewResponse.Datum> detailResponseList;

    public DetailedAdapter(Context context, List<AllViewResponse.Datum> detailResponseList) {
        this.context = context;
        this.detailResponseList = detailResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AllViewResponse.Datum detailResponse = detailResponseList.get(position);

        holder.Name.setText(detailResponse.getName());
        holder.Location.setText(detailResponse.getCityName());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage);

        if(detailResponse.getRole().equals("1"))
        {
            Glide.with(context)
                    .load(Constants.CUS_PROFILE_S_URL+detailResponse.getProfileImage())
                    .apply(options)
                    .into(holder.Image);
        }
        else
        {
            Glide.with(context)
                    .load(Constants.CUS_PROFILE_L_URL+detailResponse.getProfileImage())
                    .apply(options)
                    .into(holder.Image);
        }
    }

    @Override
    public int getItemCount() {
        return detailResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView Name, Location;
        private final CircleImageView Image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Image = itemView.findViewById(R.id.image);
            Location = itemView.findViewById(R.id.location);
        }
    }
}
