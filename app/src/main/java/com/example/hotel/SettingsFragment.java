package com.example.hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private DatabaseReference databaseReservations;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<Reservation> reservationList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recycler_view);
        databaseReservations = FirebaseDatabase.getInstance().getReference("reservations");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reservationList = new ArrayList<>();
        adapter = new ReservationAdapter(getActivity(), reservationList, databaseReservations);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Запуск активности для создания бронирования
                Intent intent = new Intent(getActivity(), ReservationOperation.class);
                startActivity(intent);
            }
        });

        loadUserReservations();

        return view;
    }

    private void loadUserReservations() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(getActivity(), "Ошибка загрузки данных пользователя", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReservations.orderByChild("email").equalTo(userEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reservationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = snapshot.getValue(Reservation.class);
                    if (reservation != null) {
                        reservationList.add(reservation);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
