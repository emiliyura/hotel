package com.example.hotel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    Context context;
    List<Hotel> hotels;

    public HotelAdapter(Context context, List<Hotel> hotels){
        this.context = context;
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(hotels.get(position).getImage()).into((holder.imageView));
        holder.nameView.setText(hotels.get(position).getName());
        holder.recDesc.setText(hotels.get(position).getDescription());
        holder.recPrice.setText(hotels.get(position).getPrice());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.putExtra("Image", hotels.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Name", hotels.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Description", hotels.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Price", hotels.get(holder.getAdapterPosition()).getPrice());

                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView, recDesc, recPrice;
        Button button;
        CardView recCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recImage);
            nameView = itemView.findViewById(R.id.recTitle);
            recCard = itemView.findViewById(R.id.recCard);
            recPrice = itemView.findViewById(R.id.recPrice);
            recDesc = itemView.findViewById(R.id.recDesc);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setHotels(ArrayList<Hotel> newHotels){
        this.hotels = newHotels;
        notifyDataSetChanged();
    }
}
