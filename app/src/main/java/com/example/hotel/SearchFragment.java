package com.example.hotel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private Button searchButton;
    private TextView hoteltextViewName;
    private ImageView hotelImage;
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Hotel> hotelList = new ArrayList<Hotel>();

//        hotelList.add(new Hotel("Russian Hotel", R.drawable.hotel));
//        hotelList.add(new Hotel("Las Vegas", R.drawable.lasvegas));

        hotelList.add(new Hotel("Russian Hotel", R.drawable.hotel, "НАСТОЯЩИЙ РУССКИЙ ОТЕЛЬ"));
        hotelList.add(new Hotel("Las Vegas", R.drawable.lasvegas, "ЭТО НЕ ОТЕЛЬ ИЗ ФОЛАУТА НЬЮ ВЕГАС"));

        hotelAdapter = new HotelAdapter(getActivity(), hotelList);
        recyclerView.setAdapter(hotelAdapter);

        return view;
    }
}
