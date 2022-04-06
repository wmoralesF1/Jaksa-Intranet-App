package com.example.reservas_pasajes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.reservas_pasajes.CancelReservaActivity;
import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.ReservaModel;

import java.util.ArrayList;

public class AdaptadorListaReservas  extends BaseAdapter {
    private ArrayList<ReservaModel> listReservas;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public AdaptadorListaReservas(Context context, ArrayList<ReservaModel> listModel) {
        this.context = context;
        this.listReservas = listModel;
    }

    @Override
    public int getCount() {
        return listReservas.size();
    }

    @Override
    public Object getItem(int position) {
        return listReservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final ReservaModel entidad = (ReservaModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_anulacion_reserva, null);
        TextView tvPasajeroAnulaReserva = (TextView) convertView.findViewById(R.id.tvPasajeroAnulaReserva);
        TextView tvNumeroDocumentoAnulaReserva = (TextView) convertView.findViewById(R.id.tvNumeroDocumentoAnulaReserva);
        TextView tvNumeroAsientoAnulaReserva = (TextView) convertView.findViewById(R.id.tvNumeroAsientoAnulaReserva);
        TextView tvIdViajeAnulaReserva = (TextView) convertView.findViewById(R.id.tvIdViajeAnulaReserva);
        TextView tvFechaViajeAnulaReserva = (TextView) convertView.findViewById(R.id.tvFechaViajeAnulaReserva);
        Button btnCancelaAnularReserva = (Button) convertView.findViewById(R.id.btnCancelaAnularReserva);
        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvPasajeroAnulaReserva.setText(entidad.getPasajero());
        tvNumeroDocumentoAnulaReserva.setText(entidad.getNumeroDocumento());
        tvNumeroAsientoAnulaReserva.setText(entidad.getNumeroAsiento());
        tvIdViajeAnulaReserva.setText(String.valueOf(entidad.getIdViaje()));
        tvFechaViajeAnulaReserva.setText(entidad.getFechaViaje());

        btnCancelaAnularReserva.setFocusable(false);
        btnCancelaAnularReserva.setClickable(false);
        return convertView;
    }
}
