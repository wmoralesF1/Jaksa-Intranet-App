package com.example.reservas_pasajes.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.ViajeModel;

import java.util.ArrayList;

public class AdaptadorListaProgramacionViaje   extends BaseAdapter {
    private ArrayList<ViajeModel> listViajes;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public AdaptadorListaProgramacionViaje(Context context, ArrayList<ViajeModel> listModel) {
        this.context = context;
        this.listViajes = listModel;
    }

    @Override
    public int getCount() {
        return listViajes.size();
    }

    @Override
    public Object getItem(int position) {
        return listViajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final ViajeModel entidad = (ViajeModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_list_prog_travel, null);

        TextView tvViajeProgrammingTravel = (TextView) convertView.findViewById(R.id.tvViajeProgrammingTravel);
        TextView tvHoraSalidaProgrammingTravel = (TextView) convertView.findViewById(R.id.tvHoraSalidaProgrammingTravel);
        TextView tvNumeroBusProgrammingTravel = (TextView) convertView.findViewById(R.id.tvNumeroBusProgrammingTravel);
        TextView tvTramoProgrammingTravel = (TextView) convertView.findViewById(R.id.tvTramoProgrammingTravel);
        TextView tvServicioProgrammingTravel = (TextView) convertView.findViewById(R.id.tvServicioProgrammingTravel);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvViajeProgrammingTravel.setText(String.valueOf(entidad.getIdViaje()));
        tvHoraSalidaProgrammingTravel.setText(entidad.getHoraViaje());
        tvNumeroBusProgrammingTravel.setText(entidad.getNumBus());
        tvTramoProgrammingTravel.setText(entidad.getIdTramoViaje());
        tvServicioProgrammingTravel.setText(entidad.getIdServicio());


        return convertView;
    }


}
