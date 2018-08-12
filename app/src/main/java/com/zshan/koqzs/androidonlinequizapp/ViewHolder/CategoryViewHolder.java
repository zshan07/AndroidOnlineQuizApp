// (Recycler View phase)(Fragments phase)
//we are using this to process each at recycler adapter

package com.zshan.koqzs.androidonlinequizapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zshan.koqzs.androidonlinequizapp.Interface.ItemClickListener;
import com.zshan.koqzs.androidonlinequizapp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView category_name;
    public ImageView category_image;

    private ItemClickListener itemClickListener;



    public CategoryViewHolder(View itemView) {
        super(itemView);
        category_image=(ImageView)itemView.findViewById(R.id.category_image);
        category_name=(TextView)itemView.findViewById(R.id.category_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
