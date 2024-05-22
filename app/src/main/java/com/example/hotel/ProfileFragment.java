package com.example.hotel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private Button button;
    private TextView textView, textViewName;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Используем getActivity().getSharedPreferences для доступа к SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name", "");

        auth = FirebaseAuth.getInstance();
        button = view.findViewById(R.id.logout);
        textView = view.findViewById(R.id.user_details);
        textViewName = view.findViewById(R.id.user_name);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            if (textView != null) {
                textView.setText(user.getEmail());
                textViewName.setText(userName);
            }
        }

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}
