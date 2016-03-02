package com.problem.recyclerex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.problem.recyclerex.R;
import com.problem.recyclerex.database.ImageItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by avin on 02/03/16.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder>{

    private ArrayList<ImageItemModel> mImageItemModels;
    private Context mContext;

    public ImageListAdapter(ArrayList<ImageItemModel> mImageItemModels, Context mContext) {
        this.mImageItemModels = mImageItemModels;
        this.mContext = mContext;
    }

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item_layout, parent, false);

        ImageListViewHolder imageListViewHolder = new ImageListViewHolder(itemLayoutView);
        return imageListViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, int position) {
        ImageItemModel imageItemModel = mImageItemModels.get(position);
        Picasso.with(mContext).load(imageItemModel.getmUrl()).into(holder.mImageIV);
        holder.mTitleTV.setText(imageItemModel.getmTitle());
    }

    @Override
    public int getItemCount() {
        return (mImageItemModels != null)?mImageItemModels.size():0;
    }

    public void changeSet(ArrayList<ImageItemModel> imageItemModels){
        mImageItemModels = imageItemModels;
        notifyDataSetChanged();
    }

    public class ImageListViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageIV;
        private TextView mTitleTV;

        public ImageListViewHolder(View itemView) {
            super(itemView);

            mImageIV = (ImageView) itemView.findViewById(R.id.image);
            mTitleTV = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
