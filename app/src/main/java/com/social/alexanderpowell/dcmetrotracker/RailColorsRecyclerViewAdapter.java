package com.social.alexanderpowell.dcmetrotracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RailColorsRecyclerViewAdapter extends RecyclerView.Adapter<RailColorsRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public RailColorsRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.train_route_row, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String color = mData.get(position);
        holder.myTextView.setText(color);

        // get icons from here: https://www.iconsdb.com/yellow-icons/circle-icon.html
        switch (color) {
            case "Blue":
                holder.myImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_circle_64));
                break;
            case "Green":
                holder.myImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_64));
                break;
            case "Orange":
                holder.myImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.orange_circle_64));
                break;
            case "Red":
                holder.myImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.red_circle_64));
                break;
            case "Silver":
                holder.myImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.silver_circle_64));
                break;
            case "Yellow":
                holder.myImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.yellow_circle_64));
                break;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView myImageView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.route_color_image);
            myTextView = itemView.findViewById(R.id.route_color_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
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
