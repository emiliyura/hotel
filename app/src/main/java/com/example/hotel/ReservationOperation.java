package com.example.hotel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import java.util.Calendar;

public class ReservationOperation extends AppCompatActivity {

    private DatabaseReference databaseReservations;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Spinner spinnerRoomNumber;
    private TextView tvHotelName, tvUserName, tvUserEmail, checkInDateTextView, checkOutDateTextView;
    private Button btnReserve, checkInDateButton, checkOutDateButton;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_reservation_operation);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        // Инициализация Firebase
        databaseReservations = FirebaseDatabase.getInstance().getReference("reservations");

        // Инициализация UI элементов
        tvHotelName = findViewById(R.id.hotel_name);
        tvUserName = findViewById(R.id.user_name);
        tvUserEmail = findViewById(R.id.user_email);
        spinnerRoomNumber = findViewById(R.id.spinner_room_number);
        checkInDateButton = findViewById(R.id.check_in_date_button);
        checkOutDateButton = findViewById(R.id.check_out_date_button);
        checkInDateTextView = findViewById(R.id.check_in_date);
        checkOutDateTextView = findViewById(R.id.check_out_date);
        btnReserve = findViewById(R.id.btn_reserve);

        // Set up the Spinner with room numbers
        String[] roomNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomNumber.setAdapter(adapter);

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
                String roomNumber = spinnerRoomNumber.getSelectedItem().toString().trim();
                String checkInDate = checkInDateTextView.getText().toString().trim();
                String checkOutDate = checkOutDateTextView.getText().toString().trim();

                if (TextUtils.isEmpty(roomNumber) || TextUtils.isEmpty(checkInDate) || TextUtils.isEmpty(checkOutDate)) {
                    Toast.makeText(ReservationOperation.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    makeReservation(userName, roomNumber, checkInDate, checkOutDate, userEmail, hotelName);
                }
            });

            checkInDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(checkInDateButton, checkInDateTextView);
                }
            });

            checkOutDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(checkOutDateButton, checkOutDateTextView);
                }
            });
        } else {
            Toast.makeText(this, "Ошибка загрузки данных пользователя", Toast.LENGTH_SHORT).show();
            finish(); // Закрыть активность, если данные пользователя не загружены
        }
    }

    private void showDatePickerDialog(final View view, final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                textView.setText(selectedDate);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void makeReservation(String user, String roomNumber, String checkInDate, String checkOutDate, String email, String hotelName) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            isRoomAvailable(hotelName, roomNumber, checkInDate, checkOutDate, isAvailable -> {
                if (isAvailable) {
                    String reservationId = databaseReservations.push().getKey();
                    Reservation reservation = new Reservation(reservationId, hotelName, roomNumber, checkInDate, checkOutDate, user, email, userId);
                    addReservation(reservation);
                } else {
                    Toast.makeText(ReservationOperation.this, "Комната занята на выбранные даты", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "Ошибка аутентификации пользователя", Toast.LENGTH_SHORT).show();
        }
    }

    private void addReservation(Reservation reservation) {
        String reservationId = reservation.getId(); // Установка id для брони
        databaseReservations.child(reservationId).setValue(reservation)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ReservationOperation.this, "Бронирование успешно", Toast.LENGTH_LONG).show();
                    finish(); // Переход обратно в профиль
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
