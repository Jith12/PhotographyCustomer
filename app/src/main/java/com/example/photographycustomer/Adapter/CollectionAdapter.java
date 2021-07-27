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
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.photographycustomer.Fragment.AddsetFragment;
import com.example.photographycustomer.Fragment.CollectionNameFragment;
import com.example.photographycustomer.Others.Constants;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.CollectionResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>{

    private Context context;
    private List<CollectionResponse.Datum> collectionlist;

    public CollectionAdapter(Context context, List<CollectionResponse.Datum> collectionlist) {
        this.context = context;
        this.collectionlist = collectionlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collectionlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.ViewHolder holder, int position) {

        CollectionResponse.Datum item = collectionlist.get(position);

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5.0f);
        circularProgressDrawable.setCenterRadius(15.0f);
        circularProgressDrawable.start();

        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        if(item.getRoleid().equals("1"))
        {
            Glide.with(context)
                    .load(Constants.CUS_PROFILE_S_URL+item.getProfileImage())
                    .apply(requestOptions)
                    .into(holder.studioImage);
        }
        else if(item.getRoleid().equals("2"))
        {
            Glide.with(context)
                    .load(Constants.CUS_PROFILE_L_URL+item.getProfileImage())
                    .apply(requestOptions)
                    .into(holder.studioImage);
        }

        holder.studioName.setText(item.getStudioName());
        holder.studioRole.setText(item.getRole());

        holder.itemView.setOnClickListener(v -> {
            FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            /*AddsetFragment addsetFragment = AddsetFragment.newInstance(item.getProfileImage(), item.getStudioName(),
                    item.getRole(), item.getRoleid(), item.getId(), item.getPhotographerId());*/
            CollectionNameFragment collectionNameFragment = CollectionNameFragment.newInstance(item.getProfileImage(),
                    item.getCustomerId(), item.getPhotographerId(), item.getStudioName(), item.getRole(), item.getRoleid());
            ft.replace(R.id.container, collectionNameFragment);
            ft.addToBackStack(null);
            ft.commit();
        });

    }

    @Override
    public int getItemCount() {
        return collectionlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView studioImage;
        private AppCompatTextView studioName, studioRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studioImage = itemView.findViewById(R.id.studio_image);
            studioName = itemView.findViewById(R.id.studio_name);
            studioRole = itemView.findViewById(R.id.role_name);
        }
    }
}
