package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HotelDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotelname");
        String hotelDescription = intent.getStringExtra("hoteldescription");

        TextView hotelNameView = findViewById(R.id.hotel_name);
        TextView hotelDescriptionView = findViewById(R.id.hotel_description);

        hotelNameView.setText(hotelName);
        hotelDescriptionView.setText(hotelDescription);
    }
}