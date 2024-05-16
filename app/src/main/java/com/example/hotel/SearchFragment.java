package com.example.hotel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private Button searchButton;
    private TextView hoteltextViewName;
    private ImageView hotelImage;
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;

    public SearchFragment() {}

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<Hotel> hotelList = new ArrayList<>();

        hotelList.add(new Hotel("Russian Hotel", R.drawable.hotel, "НАСТОЯЩИЙ РУССКИЙ ОТЕЛЬ тут очень много слов короче это очень хороший русский отель прям топ 10 из 10 с едой басикамми клубами и всякими такимим преколами. НАСТОЯЩИЙ РУССКИЙ ОТЕЛЬ тут очень много слов короче это очень хороший русский отель прям топ 10 из 10 с едой басикамми клубами и всякими такимим преколами. НАСТОЯЩИЙ РУССКИЙ ОТЕЛЬ тут очень много слов короче это очень хороший русский отель прям топ 10 из 10 с едой басикамми клубами и всякими такимим преколами. НАСТОЯЩИЙ РУССКИЙ ОТЕЛЬ тут очень много слов короче это очень хороший русский отель прям топ 10 из 10 с едой басикамми клубами и всякими такимим преколами. "));
        hotelList.add(new Hotel("Las Vegas", R.drawable.lasvegas, "ЭТО НЕ ОТЕЛЬ ИЗ ФОЛАУТА НЬЮ ВЕГАС"));


        hotelAdapter = new HotelAdapter(getActivity(), hotelList);
        recyclerView.setAdapter(hotelAdapter);


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("Hotel");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                hotelList.clear();
//
//                for (DataSnapshot hotelSnapshot : snapshot.getChildren()) {
//                    Hotel hotel = hotelSnapshot.getValue(Hotel.class);
//                    hotel.setKey(hotelSnapshot.getKey());
//                    hotelList.add(hotel);
//                }
//                 //Обновляем адаптер после получения данных из Firebase
//                hotelAdapter = new HotelAdapter(getActivity(), hotelList);
//                recyclerView.setAdapter(hotelAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Firebase", "onCancelled", error.toException());
//            }
//        });
//
        return view;
    }
}
