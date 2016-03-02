package com.problem.recyclerex.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by avin on 02/03/16.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder>{

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ImageListViewHolder extends RecyclerView.ViewHolder {
        public ImageListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
