package com.smarthub.smhubads.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthub.smhubads.ItemType;
import com.smarthub.smhubads.R;
import com.smarthub.smhubads.Utils.AppLogger;
import com.smarthub.smhubads.Utils.CustomViewGroup;


public abstract class AdListAdapterView extends RecyclerView.Adapter<AdListAdapterView.ViewHold> {

    private int adInterval;
    private int layoutRes;

    public AdListAdapterView( int adInterval, int layoutRes) {
        this.adInterval = adInterval;
        this.layoutRes = layoutRes;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch(ItemType.values()[viewType]){
            case AD_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ad_view,parent,false);
                LinearLayout mainContentView = view.findViewById(R.id.main_content);
                View content = LayoutInflater.from(parent.getContext()).inflate(layoutRes, null);
                mainContentView.addView(content);
                return new ViewHold(view);
            case LOAD_MORE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_load_view,parent,false);
                mainContentView = view.findViewById(R.id.main_content);
//                TextView textView = view.findViewById(R.id.text);
//                textView.setOnClickListener(v->{
//                    loadMoreRequest();
//                });
                content = LayoutInflater.from(parent.getContext()).inflate(layoutRes, null);
                mainContentView.addView(content);
                ViewHold viewHolder = new ViewHold(view);
                return viewHolder;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(layoutRes,parent,false);;
                return new ViewHold(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % adInterval==0 && position!=0)
            return ItemType.AD_TYPE.ordinal();
        else
            return ItemType.MAIN_CONTENT.ordinal();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold viewHold, int position) {
        if (viewHold.getItemViewType() == ItemType.AD_TYPE.ordinal()){
            ViewGroup adContainer = viewHold.itemView.findViewById(R.id.ad_container);
            adContainer.removeAllViews();
            loadAd(adContainer);
        }
    }

    public class ViewHold extends RecyclerView.ViewHolder {

        public ViewHold(@NonNull View itemView) {
            super(itemView);
        }

    }

    public abstract void loadAd(ViewGroup viewGroup);
}
