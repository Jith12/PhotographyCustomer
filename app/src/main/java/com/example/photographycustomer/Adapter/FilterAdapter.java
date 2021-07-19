package com.example.photographycustomer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photographycustomer.Interface.BottomSheetClickListener;
import com.example.photographycustomer.R;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private List<String> list;
    private boolean lastchecked = false;
    private ViewHolder lastholder = null;
    private int lastPosition = -1;
    private BottomSheetClickListener bottomSheetClickListener;

    public FilterAdapter(List<String> list, BottomSheetClickListener bottomSheetClickListener) {
        this.list = list;
        this.bottomSheetClickListener = bottomSheetClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_filter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.textView.setText(list.get(position));

        int i = Prefs.getInt("lastSelectedPlan",-1);

        if(i!= -1 && position == i){
            holder.radioButton.setChecked(true);
            bottomSheetClickListener.onBottomSheetClickListener(list.get(position));
            lastPosition = position;
            lastchecked = true;
            lastholder = holder;
            Prefs.putInt("lastSelectedPlan",position);

        }

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(lastPosition == position){
                    return;
                }
                else if(lastchecked){
                    lastholder.radioButton.setChecked(false);
                }

                holder.radioButton.setChecked(true);
                bottomSheetClickListener.onBottomSheetClickListener(list.get(position));
                lastPosition = position;
                lastchecked = true;
                lastholder = holder;
                Prefs.putInt("lastSelectedPlan",position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatRadioButton radioButton;
        private AppCompatTextView textView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.filter_radio_btn);
            textView = itemView.findViewById(R.id.filter_textview);

        }
    }
}
