package com.example.photographycustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.photographycustomer.Others.Constants;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.SliderResponse;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderResponse.Datum> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderResponse.Datum> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderResponse.Datum sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        SliderResponse.Datum sliderItem = mSliderItems.get(position);
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

        Glide.with(viewHolder.itemView)
                .load(Constants.CUS_BANNER_URL+sliderItem.getBannerImage())
                .apply(requestOptions)
                .into(viewHolder.image);
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        private View itemView;
        private AppCompatImageView image;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_banner_slider);
            this.itemView = itemView;
        }
    }

}
