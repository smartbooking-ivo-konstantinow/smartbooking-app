package com.smartbooking.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbooking.app.R;
import com.smartbooking.app.data.model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ViewHolder> {

    public interface OnReservaClick {
        void onClick(Reserva reserva);
    }

    private final List<Reserva> reservas = new ArrayList<>();
    private final OnReservaClick listener;

    public ReservasAdapter(OnReservaClick listener) {
        this.listener = listener;
    }

    // Reemplaza la lista que se muestra
    public void setReservas(List<Reserva> nuevas) {
        reservas.clear();
        reservas.addAll(nuevas);
        notifyDataSetChanged();
    }

    // Crea una fila nueva (solo las que entran en pantalla)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reserva, parent, false);
        return new ViewHolder(vista);
    }

    // Llena una fila (nueva o reciclada) con los datos de la reserva de esa posición
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reserva reserva = reservas.get(position);
        holder.tvFecha.setText(reserva.getFecha());
        holder.tvHorario.setText(holder.itemView.getContext().getString(
                R.string.reserva_horario,
                reserva.getHoraInicio().substring(0, 5),
                reserva.getHoraFin().substring(0, 5)));
        holder.tvMotivo.setText(reserva.getMotivo());
        holder.itemView.setOnClickListener(v -> listener.onClick(reserva));
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    // Guarda las referencias a los elementos de una fila, para no buscarlos cada vez
    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvFecha, tvHorario, tvMotivo;

        ViewHolder(View vista) {
            super(vista);
            tvFecha = vista.findViewById(R.id.tvFecha);
            tvHorario = vista.findViewById(R.id.tvHorario);
            tvMotivo = vista.findViewById(R.id.tvMotivo);
        }
    }
}