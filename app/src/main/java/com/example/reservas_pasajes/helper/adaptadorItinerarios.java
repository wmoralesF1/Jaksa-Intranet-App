package com.example.reservas_pasajes.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.MapBusActivity;
import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.SearchRoutesActivity;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.RutaModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.ViajeModel;

import java.util.ArrayList;

public class adaptadorItinerarios extends BaseAdapter{
    private ArrayList<ItinerarioModel> listItinerarios;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public adaptadorItinerarios(Context context, ArrayList<ItinerarioModel> listModel) {
        this.context = context;
        this.listItinerarios = listModel;
    }

    @Override
    public int getCount() {
        return listItinerarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listItinerarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final ItinerarioModel entidad = (ItinerarioModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_itinerarios, null);
        LinearLayout llRow = (LinearLayout) convertView.findViewById(R.id.llRow);
        TextView tvDescServicio = (TextView) convertView.findViewById(R.id.tvDescServicio);
        TextView tvHoraPartida = (TextView) convertView.findViewById(R.id.tvHoraPartida);
        //TextView tvAsientosLibres = (TextView) convertView.findViewById(R.id.tvAsientosLibres);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvDescServicio.setText("Servicio: " + entidad.getNomServicio());
        tvHoraPartida.setText("Hora Partida: " + entidad.getHoraReserva());
        //tvAsientosLibres.setText(entidad.getAsientosLibres() + " Asientos Libres");

        if(entidad.getNomServicio().toUpperCase().equals("VIP")){
            GradientDrawable gradiColor=new GradientDrawable();
            gradiColor.setColor(Color.parseColor("#EECD00"));
            llRow.setBackground(gradiColor);
        }

        return convertView;
    }
}
