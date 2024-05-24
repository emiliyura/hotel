package com.example.hotel;

import static androidx.core.content.ContextCompat.startActivity;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private Context mContext;
    private List<Reservation> mReservationList;
    private DatabaseReference mDatabaseReference;

    // Конструктор адаптера
    public ReservationAdapter(Context context, List<Reservation> reservationsList) {
        mContext = context;
        mReservationList = reservationsList;
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
        holder.hotelName.setText(reservation.getHotelName());
        holder.roomNumber.setText(reservation.getRoomNumber());
        holder.InDate.setText(reservation.getCheckInDate());
        holder.OutDate.setText(reservation.getCheckOutDate());
        holder.userName.setText(reservation.getUserName());
        holder.userEmail.setText(reservation.getEmail());


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reservationId = reservation.getId();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("reservations");

                reference.child(reservationId).removeValue();
                Toast.makeText(mContext, "Бронь удалена", Toast.LENGTH_SHORT).show();

                mReservationList.remove(reservation);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReservationList.size();
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
