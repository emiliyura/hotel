package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsFragment extends Fragment {

    FloatingActionButton fab;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HotelCreate.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
