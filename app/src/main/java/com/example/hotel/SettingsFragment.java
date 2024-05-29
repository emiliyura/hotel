package com.example.hotel;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    RecyclerView recyclerView;
    List<Reservation> reservationList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    private boolean isAdmin = false;
    FloatingActionButton fab;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        assert firebaseUser != null;
        String userId = firebaseUser.getUid();

        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerReservation);

        databaseReference1.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userType = snapshot.child("type").getValue(String.class);
                isAdmin = userType != null && userType.equals("Admin");

                fab.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
                loadReservations();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработчик
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        reservationList = new ArrayList<>();
        ReservationAdapter reservationAdapter = new ReservationAdapter(getActivity(), reservationList);
        recyclerView.setAdapter(reservationAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();

        databaseReference = FirebaseDatabase.getInstance().getReference("reservations");

        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), HotelCreate.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadReservations() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        if (isAdmin) {
            // Загрузка всех бронирований для администратора
            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reservationList.clear();
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Reservation reservation = itemSnapshot.getValue(Reservation.class);
                        reservationList.add(reservation);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    dialog.dismiss();
                }
            });
        } else {
            // Загрузка бронирований только для текущего пользователя
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser != null) {
                String userId = firebaseUser.getUid();
                eventListener = databaseReference.orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reservationList.clear();
                        for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                            Reservation reservation = itemSnapshot.getValue(Reservation.class);
                            reservationList.add(reservation);
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                    }
                });
            } else {
                dialog.dismiss();
            }
        }
    }
}
