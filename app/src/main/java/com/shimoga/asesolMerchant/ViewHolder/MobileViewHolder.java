package com.shimoga.asesolMerchant.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shimoga.asesolMerchant.Common.ItemClickListener;
import com.shimoga.asesolMerchant.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MobileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mobile_name,mobile_price,mobile_ram,release_date,storage;
    public ImageView mobile_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MobileViewHolder(@NonNull View itemView) {
        super(itemView);

        mobile_name=(TextView)itemView.findViewById(R.id.mobile_name);
        mobile_image=(ImageView) itemView.findViewById(R.id.mobile_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
