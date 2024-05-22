package com.example.hotel;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

public class ReservationOperation extends AppCompatActivity {

    private DatabaseReference databaseReservations;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private EditText etRoomNumber, etCheckInDate, etCheckOutDate, etUser;
    private TextView tvHotelName;
    private Button btnReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_reservation_operation);

        databaseReservations = FirebaseDatabase.getInstance().getReference("reservations");

        tvHotelName = findViewById(R.id.hotel_name);
        etRoomNumber = findViewById(R.id.et_room_number);
        etCheckInDate = findViewById(R.id.et_check_in_date);
        etCheckOutDate = findViewById(R.id.et_check_out_date);
        etUser = findViewById(R.id.et_user);
        btnReserve = findViewById(R.id.btn_reserve);

        String hotelName = getIntent().getStringExtra("hotelName");
        tvHotelName.setText(hotelName);

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomNumber = etRoomNumber.getText().toString().trim();
                String checkInDate = etCheckInDate.getText().toString().trim();
                String checkOutDate = etCheckOutDate.getText().toString().trim();
                String user = etUser.getText().toString().trim();

                if (TextUtils.isEmpty(roomNumber) || TextUtils.isEmpty(checkInDate) || TextUtils.isEmpty(checkOutDate) || TextUtils.isEmpty(user)) {
                    Toast.makeText(ReservationOperation.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    makeReservation(user, roomNumber, checkInDate, checkOutDate);
                }
            }
        });
    }

    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void makeReservation(String user, String roomNumber, String checkInDate, String checkOutDate) {
        if (!isValidDate(checkInDate) || !isValidDate(checkOutDate)) {
            Toast.makeText(ReservationOperation.this, "Дата должна быть в формате yyyy-MM-dd", Toast.LENGTH_LONG).show();
            return;
        }

        isRoomAvailable(roomNumber, checkInDate, checkOutDate, isAvailable -> {
            if (isAvailable) {
                Reservation reservation = new Reservation(user, roomNumber, checkInDate, checkOutDate);
                addReservation(reservation);
            } else {
                Toast.makeText(ReservationOperation.this, "Комната занята на выбранные даты", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void addReservation(Reservation reservation) {
        String reservationId = databaseReservations.push().getKey();
        Log.d("ReservationOperation", "Generated ID: " + reservationId);
        databaseReservations.child(reservationId).setValue(reservation)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ReservationOperation.this, "Бронирование успешно", Toast.LENGTH_LONG).show();
                    Log.d("ReservationOperation", "Reservation added successfully");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ReservationOperation.this, "Ошибка бронирования", Toast.LENGTH_LONG).show();
                    Log.e("ReservationOperation", "Error adding reservation", e);
                });
    }

    private void isRoomAvailable(String roomNumber, String checkInDate, String checkOutDate, RoomAvailabilityCallback callback) {
        if (!isValidDate(checkInDate) || !isValidDate(checkOutDate)) {
            Toast.makeText(ReservationOperation.this, "Дата должна быть в формате yyyy-MM-dd", Toast.LENGTH_LONG).show();
            return;
        }

        databaseReservations.orderByChild("roomNumber").equalTo(roomNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isAvailable = true;
                LocalDate checkIn = LocalDate.parse(checkInDate, dateFormatter);
                LocalDate checkOut = LocalDate.parse(checkOutDate, dateFormatter);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = snapshot.getValue(Reservation.class);
                    if (reservation != null) {
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
                Log.e("ReservationOperation", "Error checking room availability", databaseError.toException());
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
