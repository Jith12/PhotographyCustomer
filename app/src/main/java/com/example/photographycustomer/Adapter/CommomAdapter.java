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
import com.example.photographycustomer.Fragment.CommonFragment;
import com.example.photographycustomer.Fragment.EalbumFragment;
import com.example.photographycustomer.R;
import com.example.photographycustomer.Response.CommonResponse;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class CommomAdapter extends RecyclerView.Adapter<CommomAdapter.ViewHolder>{

    private Context context;
    private List<CommonResponse.Datum> commonList;
    private String familyid, photographerid, customerid, collectionid, familyname;

    public CommomAdapter(Context context, List<CommonResponse.Datum> commonList) {
        this.context = context;
        this.commonList = commonList;
    }

    @NonNull
    @Override
    public CommomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_common, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommomAdapter.ViewHolder holder, int position) {

        CommonResponse.Datum item = commonList.get(position);

        holder.studioName.setText(item.getCommonName());

        familyid = Prefs.getString("familyid", "");
        photographerid = Prefs.getString("photographerid", "");
        customerid = Prefs.getString("customerid", "");
        collectionid = Prefs.getString("collectionid", "");
        familyname = Prefs.getString("familyname", "");

        holder.itemView.setOnClickListener(v -> {
            if(item.getId().equals("1"))
            {
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                AddsetFragment addsetFragment = AddsetFragment.newInstance(familyid, photographerid, customerid, collectionid, familyname, item.getCommonName());
                ft.replace(R.id.container, addsetFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
            else if(item.getId().equals("2"))
            {
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                EalbumFragment ealbumFragment = EalbumFragment.newInstance(familyid, photographerid, customerid, collectionid, familyname, item.getCommonName());
                ft.replace(R.id.container, ealbumFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return commonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView studioName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studioName = itemView.findViewById(R.id.studio_name);
        }
    }
}
