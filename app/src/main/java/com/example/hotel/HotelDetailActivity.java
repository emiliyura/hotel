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

import com.bumptech.glide.Glide;

import java.time.Instant;

public class HotelDetailActivity extends AppCompatActivity {
    Context context;
    TextView detailDesc, detailName;
    ImageView detailImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        detailImage = findViewById(R.id.imageView);
        detailDesc = findViewById(R.id.hotel_description);
        detailName = findViewById(R.id.hotel_name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailName.setText(bundle.getString("Name"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

        Button button = findViewById(R.id.hotel_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelDetailActivity.this, ReservationOperation.class);
                intent.putExtra("hotelName", detailName.getText());
                startActivity(intent);
            }
        });
    }
}