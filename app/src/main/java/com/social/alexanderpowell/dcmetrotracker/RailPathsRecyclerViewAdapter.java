package com.social.alexanderpowell.dcmetrotracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RailPathsRecyclerViewAdapter extends RecyclerView.Adapter<RailPathsRecyclerViewAdapter.ViewHolder> {

    private List<String> mData, mData1;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String colorCode;

    // data is passed into the constructor
    public RailPathsRecyclerViewAdapter(Context context, List<String> data, List<String> data1, String colorCode) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mData1 = data1;
        this.colorCode = colorCode;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.train_route_station_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String stationName = mData.get(position);
        holder.myTextView.setText(stationName);
        if (this.colorCode.equals("RD")) {
            holder.myImageView.setBackgroundResource(R.drawable.red_circle_64);
        } else if (this.colorCode.equals("YL")) {
            holder.myImageView.setBackgroundResource(R.drawable.yellow_circle_64);
        } else if (this.colorCode.equals("GR")) {
            holder.myImageView.setBackgroundResource(R.drawable.green_circle_64);
        } else if (this.colorCode.equals("BL")) {
            holder.myImageView.setBackgroundResource(R.drawable.blue_circle_64);
        } else if (this.colorCode.equals("OR")) {
            holder.myImageView.setBackgroundResource(R.drawable.orange_circle_64);
        } else if (this.colorCode.equals("SV")) {
            holder.myImageView.setBackgroundResource(R.drawable.silver_circle_64);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.station_name_text);
            myImageView = itemView.findViewById(R.id.line_color);
            /*String color = myTextView.getText().toString().toLowerCase();
            if (color.equals("metro center")) {
                myImageView.setBackgroundResource(R.drawable.red_circle_64);
            } else {
                myImageView.setBackgroundResource(R.drawable.blue_circle_64);
            }*/
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getStationName(int id) {
        return mData.get(id);
    }

    public String getStationCode(int id) {
        return mData1.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
