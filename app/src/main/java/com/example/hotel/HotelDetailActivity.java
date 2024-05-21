package com.example.hotel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Instant;

public class HotelDetailActivity extends AppCompatActivity {
    Context context;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        Intent intent = getIntent();

        String hotelName = intent.getStringExtra("hotelname");
        String hotelDescription = intent.getStringExtra("hoteldescription");
        int hotelImage = intent.getIntExtra("hotelImage", 0);
        String hotelPrice = intent.getStringExtra("hotelPrice");

        TextView hotelNameView = findViewById(R.id.hotel_name);
        TextView hotelDescriptionView = findViewById(R.id.hotel_description);
        ImageView hotelImageView = findViewById(R.id.imageView);
        Button hotelPriceText = findViewById(R.id.hotel_button);

        hotelNameView.setText(hotelName);
        hotelDescriptionView.setText(hotelDescription);
        hotelImageView.setImageResource(hotelImage);
        hotelPriceText.setText("Забронировать номер " + hotelPrice + " за ночь");

        hotelPriceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HotelDetailActivity.this, ReservationOperation.class);
                intent1.putExtra("hotelname", hotelName);
                startActivity(intent1);
            }
        });
    }
}