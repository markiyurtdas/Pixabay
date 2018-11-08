package com.dev.marki.pixabay.Util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marki.pixabay.Data.Item;
import com.dev.marki.pixabay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by marki on 06.02.2018.
 */


public class ItemAdapter extends RecyclerView.Adapter<com.dev.marki.pixabay.Util.ItemAdapter.AdapterViewHolder > {
    private Context ctx;
    private ArrayList<Item> itemsList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ItemAdapter(Context ctx, ArrayList<Item> itemsList) {
        this.ctx = ctx;
        this.itemsList = itemsList;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.card_view,parent,false);
        return new AdapterViewHolder(v);
    }


    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Item currentItem = itemsList.get(position);

        String imageUrl = currentItem.getUrl();
        String name = currentItem.getName();
        int likeCount = currentItem.getLike();
        int viewsCount = currentItem.getViews();


        holder.name.setText(name);
        holder.like.setText("Likes: "+ likeCount);
        holder.views.setText("Views: "+viewsCount);
        Picasso.with(ctx).load(imageUrl).fit().centerInside().into(holder.image);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name;
        public TextView like;
        public TextView views;


        public AdapterViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.text_view_title);
            like = itemView.findViewById(R.id.text_view_like);
            views = itemView.findViewById(R.id.card_view_views);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }





}
