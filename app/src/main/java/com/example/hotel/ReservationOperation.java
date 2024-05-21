package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReservationOperation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_operation);

        Intent intent = getIntent();

        String hotelName = intent.getStringExtra("hotelName");
        TextView hotelNameView = findViewById(R.id.hotel_name_inOperation);
        hotelNameView.setText(hotelName);
    }
}