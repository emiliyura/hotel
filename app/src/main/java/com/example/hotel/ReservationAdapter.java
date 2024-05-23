package com.example.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    Context context;
    List<Reservation> reservationList;

    // Конструктор адаптера
    public ReservationAdapter(Context context, List<Reservation> reservationsList) {
        this.context = context;
        this.reservationList = reservationsList;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);

        // Установка данных бронирования в элементы макета
//        holder.hotelName.setText(reservation.getHotelName());
//        holder.roomNumber.setText(reservation.getRoomNumber());
//        holder.InDate.setText(reservation.getCheckInDate());
//        holder.OutDate.setText(reservation.getCheckOutDate());
//        holder.userName.setText(reservation.getUserName());
//        holder.userEmail.setText(reservation.getEmail());

        holder.hotelName.setText(reservationList.get(position).getHotelName());
        holder.roomNumber.setText(reservationList.get(position).getRoomNumber());
        holder.InDate.setText(reservationList.get(position).getCheckInDate());
        holder.OutDate.setText(reservationList.get(position).getCheckOutDate());
        holder.userName.setText(reservationList.get(position).getUserName());
        holder.userEmail.setText(reservationList.get(position).getEmail());

        // Обработчик для кнопки удаления
//        holder.btnDelete.setOnClickListener(v -> {
//            String reservationId = reservation.getId();
//            mDatabaseReference.child(reservationId).removeValue()
//                    .addOnSuccessListener(aVoid -> {
//                        // Успешное удаление элемента
//                        Toast.makeText(mContext, "Бронь удалена", Toast.LENGTH_SHORT).show();
//
//                        // Удаление элемента из списка и обновление адаптера
//                        mReservationList.remove(reservation);
//                        notifyDataSetChanged();
//                    })
//                    .addOnFailureListener(e -> {
//                        // Ошибка при удалении элемента
//                        Toast.makeText(mContext, "Ошибка при удалении брони: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    });
//        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, roomNumber, InDate, OutDate, userName, userEmail;
        Button btnDelete;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);


            hotelName = itemView.findViewById(R.id.hotelName);
            roomNumber = itemView.findViewById(R.id.roomNumber);
            InDate = itemView.findViewById(R.id.tv_check_in_date);
            OutDate = itemView.findViewById(R.id.tv_check_out_date);
            userName = itemView.findViewById(R.id.tv_user_name);
            userEmail = itemView.findViewById(R.id.tv_user_email);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}