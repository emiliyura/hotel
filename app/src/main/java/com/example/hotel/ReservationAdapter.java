package com.example.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private Context mContext;
    private List<Reservation> mReservationList;
    private DatabaseReference mDatabaseReference;

    // Конструктор адаптера
    public ReservationAdapter(Context context, List<Reservation> reservationList, DatabaseReference databaseReference) {
        this.mContext = context;
        this.mReservationList = reservationList;
        this.mDatabaseReference = databaseReference;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = mReservationList.get(position);

        // Установка данных бронирования в элементы макета
        holder.recTitle.setText(reservation.getHotelName());
        holder.recDesc.setText(reservation.getRoomNumber());
        holder.tvCheckInDate.setText(reservation.getCheckInDate());
        holder.tvCheckOutDate.setText(reservation.getCheckOutDate());
        holder.tvUserName.setText(reservation.getUserName());
        holder.tvUserEmail.setText(reservation.getEmail());

        // Обработчик для кнопки удаления
        holder.btnDelete.setOnClickListener(v -> {
            String reservationId = reservation.getId();
            mDatabaseReference.child(reservationId).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Успешное удаление элемента
                        Toast.makeText(mContext, "Бронь удалена", Toast.LENGTH_SHORT).show();

                        // Удаление элемента из списка и обновление адаптера
                        mReservationList.remove(reservation);
                        notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        // Ошибка при удалении элемента
                        Toast.makeText(mContext, "Ошибка при удалении брони: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return mReservationList.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView recTitle, recDesc, tvCheckInDate, tvCheckOutDate, tvUserName, tvUserEmail;
        Button btnDelete;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            recTitle = itemView.findViewById(R.id.recTitle);
            recDesc = itemView.findViewById(R.id.recDesc);
            tvCheckInDate = itemView.findViewById(R.id.tv_check_in_date);
            tvCheckOutDate = itemView.findViewById(R.id.tv_check_out_date);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserEmail = itemView.findViewById(R.id.tv_user_email);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
