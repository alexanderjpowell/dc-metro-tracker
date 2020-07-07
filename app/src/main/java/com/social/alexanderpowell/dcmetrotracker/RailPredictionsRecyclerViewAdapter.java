package com.social.alexanderpowell.dcmetrotracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RailPredictionsRecyclerViewAdapter extends RecyclerView.Adapter<RailPredictionsRecyclerViewAdapter.ViewHolder> {

    //private List<String> mData;
    private List<JSONObject> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    /*public RailPredictionsRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }*/
    public RailPredictionsRecyclerViewAdapter(Context context, List<JSONObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.train_prediction_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String predictionTime = mData.get(position);
        //holder.myTextView.setText(predictionTime);
        try {
            String predictionTime = mData.get(position).getString("Min");
            String destinationName = mData.get(position).getString("DestinationName");
            String lineColor = mData.get(position).getString("Line");
            if (lineColor.equals("RD")) {
                holder.lineColorImageView.setBackground(context.getDrawable(R.drawable.red_circle));
            } else if (lineColor.equals("BL")) {
                holder.lineColorImageView.setBackground(context.getDrawable(R.drawable.blue_circle));
            } else if (lineColor.equals("YL")) {
                holder.lineColorImageView.setBackground(context.getDrawable(R.drawable.yellow_circle));
            } else if (lineColor.equals("OR")) {
                holder.lineColorImageView.setBackground(context.getDrawable(R.drawable.orange_circle));
            } else if (lineColor.equals("GR")) {
                holder.lineColorImageView.setBackground(context.getDrawable(R.drawable.green_circle));
            } else if (lineColor.equals("SV")) {
                holder.lineColorImageView.setBackground(context.getDrawable(R.drawable.silver_circle));
            }
            holder.destinationTextView.setText(destinationName);
            Integer predictionTimeInt = convertStringToInt(predictionTime);
            if (predictionTimeInt == null) {
                if (predictionTime.equals("BRD")) {
                    holder.minutesToArrivalTextView.setText(predictionTime);
                    holder.minutesToArrivalTextView.setTextColor(Color.GREEN);
                } else if (predictionTime.equals("ARR")) {
                    holder.minutesToArrivalTextView.setText(predictionTime);
                    holder.minutesToArrivalTextView.setTextColor(Color.GREEN);
                } else {
                    holder.minutesToArrivalTextView.setText(predictionTime);
                    holder.minutesToArrivalTextView.setTextColor(Color.BLACK);
                }
            } else if (predictionTimeInt <= 10) {
                holder.minutesToArrivalTextView.setText(predictionTime + " min");
                holder.minutesToArrivalTextView.setTextColor(Color.GREEN);
            } else {
                holder.minutesToArrivalTextView.setText(predictionTime + " min");
                holder.minutesToArrivalTextView.setTextColor(Color.RED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Nullable
    public Integer convertStringToInt(String minutes) {
        Integer ret;
        try {
            ret = Integer.valueOf(minutes);
        } catch (Exception ex) {
            ret = null;
        }
        return ret;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView minutesToArrivalTextView, destinationTextView;
        ImageView lineColorImageView;
        String lineColorCode;

        ViewHolder(View itemView) {
            super(itemView);
            minutesToArrivalTextView = itemView.findViewById(R.id.prediction_time_text);
            destinationTextView = itemView.findViewById(R.id.destination_text);
            lineColorImageView = itemView.findViewById(R.id.line_color_image_view);

            //lineColorImageView.setBackground(context.getDrawable(R.drawable.green_circle));

            /*if (convertStringToInt(minutesToArrivalTextView.getText().toString()) > 10) {
                minutesToArrivalTextView.setTextColor(Color.GREEN);
            } else {
                minutesToArrivalTextView.setTextColor(Color.RED);
            }*/

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    /*public String getStationName(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
