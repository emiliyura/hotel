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
import androidx.recyclerview.widget.RecyclerView;

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
        Hotel hotel = hotels.get(position);

        holder.nameView.setText(hotels.get(position).getName());

        // Null check before retrieving the resource ID
        if (hotel.getImage() != null) {
            // Get the resource ID for the hotel image
            int imageResourceId = context.getResources().getIdentifier(hotel.getImage(), "drawable", context.getPackageName());
            holder.imageView.setImageResource(imageResourceId);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.putExtra("hotelname", hotel.getName());
                intent.putExtra("hoteldescription", hotel.getDescription());

                // Null check before passing the image resource ID
                if (hotel.getImage() != null) {
                    // Get the resource ID for the hotel image
                    int imageResourceId = context.getResources().getIdentifier(hotel.getImage(), "drawable", context.getPackageName());
                    intent.putExtra("hotelImage", imageResourceId);
                }

                intent.putExtra("hotelPrice", hotel.getPrice().toString());
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
        TextView nameView;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.namehotel);
            button = itemView.findViewById(R.id.hotelbutton);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setHotels(ArrayList<Hotel> newHotels){
        this.hotels = newHotels;
        notifyDataSetChanged();
    }
}
