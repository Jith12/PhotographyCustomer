package com.example.photographycustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photographycustomer.Others.Constants;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.PhotoResponse;
import com.example.photographycustomer.Room.CustomerDB;
import com.example.photographycustomer.Room.SelectPhoto;

import java.util.List;

import needle.Needle;
import needle.UiRelatedTask;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

    private Context context;
    private List<PhotoResponse.Datum> photosList;

    public PhotoAdapter(Context context, List<PhotoResponse.Datum> photosList) {
        this.context = context;
        this.photosList = photosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PhotoResponse.Datum item = photosList.get(position);

        Needle.onBackgroundThread()
                .execute(new UiRelatedTask<List<SelectPhoto>>() {
                    @Override
                    protected List<SelectPhoto> doWork() {
                        return CustomerDB
                                .getInstance(context)
                                .getRoomDB()
                                .selectPhotoDAO()
                                .getPhoto(item.getPhotos());

                    }

                    @Override
                    protected void thenDoUiRelatedWork(List<SelectPhoto> cartcheck) {

                        if(cartcheck.size() > 0)
                        {
                            for(int j = 0; j < cartcheck.size(); j++)
                            {
                                if(item.getPhotos().equalsIgnoreCase(cartcheck.get(j).getSelected_images()))
                                {
                                    holder.photoCheck.setChecked(true);
                                }
                                else
                                {
                                    holder.photoCheck.setChecked(false);
                                }
                            }
                        }

                    }
                });


        Glide.with(context)
                .load(Constants.CUS_ADDSET_URL+item.getPhotos())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.photoImage);

        holder.photoCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    addphotos(item.getPhotos());
                }
                else
                {
                    removephotos(item.getPhotos());
                }
            }
        });
    }

    private void removephotos(String filename) {

        Needle.onBackgroundThread()
                .execute(new UiRelatedTask<Void>() {
                    @Override
                    protected Void doWork() {
                        CustomerDB.getInstance(context).getRoomDB()
                                .selectPhotoDAO()
                                .removeByFilename(filename);
                        return null;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(Void aVoid) {

                    }
                });

    }

    private void addphotos(String filename) {

        Needle.onBackgroundThread()
                .execute(new UiRelatedTask<Void>() {
                    @Override
                    protected Void doWork() {

                        SelectPhoto photos = new SelectPhoto();
                        photos.setSelected_images(filename);
                        CustomerDB.getInstance(context).getRoomDB()
                                .selectPhotoDAO()
                                .insert(photos);

                        return null;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(Void aVoid) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView photoImage;
        private AppCompatCheckBox photoCheck;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImage = itemView.findViewById(R.id.photos_show);
            photoCheck = itemView.findViewById(R.id.photo_check);
        }
    }
}
