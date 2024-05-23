package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private Button button;
    private TextView textView, textViewName;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Инициализируем базу данных Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());

        textView = view.findViewById(R.id.user_details);
        textViewName = view.findViewById(R.id.user_name);
        button = view.findViewById(R.id.logout);

        if (user == null) {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            textView.setText(user.getEmail());
            loadUserNameFromFirebase();
        }

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    private void loadUserNameFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User userProfile = snapshot.getValue(User.class);
                    if (userProfile != null && userProfile.getName() != null) {
                        textViewName.setText(userProfile.getName());
                    } else {
                        textViewName.setText("");
                    }
                } else {
                    textViewName.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
