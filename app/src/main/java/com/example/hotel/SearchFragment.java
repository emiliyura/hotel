package com.example.hotel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    List<Hotel> hotelList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.hotels_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        hotelList = new ArrayList<>();
        HotelAdapter hotelAdapter = new HotelAdapter(getContext(), hotelList);
        recyclerView.setAdapter(hotelAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Hotel");

        // Инициализируем searchView
        searchView = view.findViewById(R.id.search_view); // Предполагая, что у вас есть search_view в layout

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Выполняется при нажатии кнопки "Поиск" на клавиатуре
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Выполняется при изменении текста в SearchView
                ArrayList<Hotel> filteredHotel = filterHotel(hotelList, newText); // Исправлено hotelList вместо list
                hotelAdapter.setHotels(filteredHotel);
                return true;
            }
        });

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hotelList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Hotel hotel = itemSnapshot.getValue(Hotel.class);
                    if (hotel != null) {
                        hotelList.add(hotel);
                    }
                }
                hotelAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        return view;
    }

    // Добавлен метод filterHotel
    // Добавлен метод filterHotel
    private ArrayList<Hotel> filterHotel(List<Hotel> hotels, String query) {
        ArrayList<Hotel> filteredHotels = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredHotels.add(hotel);
            }
        }
        return filteredHotels;
    }
}
