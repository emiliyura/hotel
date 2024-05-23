package com.example.hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class ReservationOperation extends AppCompatActivity {

    private DatabaseReference databaseReservations;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private EditText etRoomNumber, etCheckInDate, etCheckOutDate;
    private TextView tvHotelName, tvUserName, tvUserEmail;
    private Button btnReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_reservation_operation);

        // Инициализация Firebase
        databaseReservations = FirebaseDatabase.getInstance().getReference("reservations");

        // Инициализация UI элементов
        tvHotelName = findViewById(R.id.hotel_name);
        tvUserName = findViewById(R.id.user_name);
        tvUserEmail = findViewById(R.id.user_email);
        etRoomNumber = findViewById(R.id.et_room_number);
        etCheckInDate = findViewById(R.id.et_check_in_date);
        etCheckOutDate = findViewById(R.id.et_check_out_date);
        btnReserve = findViewById(R.id.btn_reserve);

        // Получение данных из Intent
        String hotelName = getIntent().getStringExtra("hotelName");
        tvHotelName.setText(hotelName);

        // Получение данных пользователя из SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name", "");
        String userEmail = sharedPreferences.getString("user_email", "");

        Log.d("ReservationOperation", "Loaded user data: name=" + userName + ", email=" + userEmail);

        // Проверка наличия данных пользователя
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userEmail)) {
            tvUserName.setText(userName);
            tvUserEmail.setText(userEmail);

            btnReserve.setOnClickListener(v -> {
                String roomNumber = etRoomNumber.getText().toString().trim();
                String checkInDate = etCheckInDate.getText().toString().trim();
                String checkOutDate = etCheckOutDate.getText().toString().trim();

                if (TextUtils.isEmpty(roomNumber) || TextUtils.isEmpty(checkInDate) || TextUtils.isEmpty(checkOutDate)) {
                    Toast.makeText(ReservationOperation.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    makeReservation(userName, roomNumber, checkInDate, checkOutDate, userEmail, hotelName);
                }
            });
        } else {
            Toast.makeText(this, "Ошибка загрузки данных пользователя", Toast.LENGTH_SHORT).show();
            finish(); // Закрыть активность, если данные пользователя не загружены
        }
    }

    private void makeReservation(String user, String roomNumber, String checkInDate, String checkOutDate, String email, String hotelName) {
        isRoomAvailable(hotelName, roomNumber, checkInDate, checkOutDate, isAvailable -> {
            if (isAvailable) {
                String reservationId = databaseReservations.push().getKey();
                Reservation reservation = new Reservation(reservationId, user, roomNumber, checkInDate, checkOutDate, email, hotelName);
                addReservation(reservation);
            } else {
                Toast.makeText(ReservationOperation.this, "Комната занята на выбранные даты", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addReservation(Reservation reservation) {
        String reservationId = reservation.getId(); // Установка id для брони
        databaseReservations.child(reservationId).setValue(reservation)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ReservationOperation.this, "Бронирование успешно", Toast.LENGTH_LONG).show();

                    // Переход обратно в профиль
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(ReservationOperation.this, "Ошибка бронирования", Toast.LENGTH_LONG).show());
    }


    private void isRoomAvailable(String hotelName, String roomNumber, String checkInDate, String checkOutDate, RoomAvailabilityCallback callback) {
        databaseReservations.orderByChild("roomNumber").equalTo(roomNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isAvailable = true;
                LocalDate checkIn = LocalDate.parse(checkInDate, dateFormatter);
                LocalDate checkOut = LocalDate.parse(checkOutDate, dateFormatter);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = snapshot.getValue(Reservation.class);
                    if (reservation != null && hotelName.equals(reservation.getHotelName())) {
                        LocalDate resCheckIn = LocalDate.parse(reservation.getCheckInDate(), dateFormatter);
                        LocalDate resCheckOut = LocalDate.parse(reservation.getCheckOutDate(), dateFormatter);
                        if (datesOverlap(resCheckIn, resCheckOut, checkIn, checkOut)) {
                            isAvailable = false;
                            break;
                        }
                    }
                }
                callback.onResult(isAvailable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ReservationOperation.this, "Ошибка проверки доступности", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !(start1.isAfter(end2) || end1.isBefore(start2));
    }

    public interface RoomAvailabilityCallback {
        void onResult(boolean isAvailable);
    }
}
