package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ApplicationDescription extends AppCompatActivity {
//
    TextView textDescriptionApplication;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_application_description);

        button = findViewById(R.id.button_back);
        textDescriptionApplication = findViewById(R.id.description_application_text);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        textDescriptionApplication.setText("Приложение предназначено для обеспечения удобного и эффективного поиска отелей, дополнительной информационной об отеле и предоставления пользователям возможности брони отеля в список бронирований. Рассмотрим варианты использования приложения:\n" +
                "1.\tЛогин/Регистрация: В начале работы с новым пользователем приложение просит обязательно пройти аутентификацию.  При отсутствии аккаунта в системе пользователь его должен создать. При наличии аккаунта пользователь может выполнить вход в систему. Система запоминает последний вход пользователя, поэтому входить каждый раз не придется.\n" +
                "2.\tПросмотр списка отелей: После успешной аутентификации пользователь может изучить список всех отелей.\n" +
                "3.\t   Просмотр детальней информации: Пользователь может открыть страницу отеля и детально изучить информацию о нем.\n" +
                "4.\tБронирование номера: При желании пользователь может сделать бронь номера на определенный диапазон дней. \n" +
                "5.\tПросмотр брони: Пользователь может изучить список своих бронирований. \n" +
                "В приложении разработана ролевая модель. Администратор может делать все тоже, что и пользователь, но также:\n" +
                "1.\tДобавление нового отеля: Администратор может добавлять новый отель в базу данных.\n" +
                "2.\t Просмотр список бронирований: Администратор может просматривать список всех бронирований, а также удалять их из базы данных.\n");
    }
}