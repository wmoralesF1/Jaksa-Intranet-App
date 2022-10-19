package com.example.reservas_pasajes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.ViajeModel;

import java.util.ArrayList;

public class AdaptadorListaProduccionBuses   extends BaseAdapter {
    private ArrayList<ViajeModel> listProduccionBuses;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public AdaptadorListaProduccionBuses(Context context, ArrayList<ViajeModel> listModel) {
        this.context = context;
        this.listProduccionBuses = listModel;
    }

    @Override
    public int getCount() {
        return listProduccionBuses.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduccionBuses.get(position);
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_list_production_bus, null);

        TextView tvProduccionBusNumBus = (TextView) convertView.findViewById(R.id.tvProduccionBusNumBus);
        TextView tvProduccionBusNumAsiento = (TextView) convertView.findViewById(R.id.tvProduccionBusNumAsiento);
        TextView tvProduccionBusHoraViaje = (TextView) convertView.findViewById(R.id.tvProduccionBusHoraViaje);
        TextView tvProduccionBusTramo = (TextView) convertView.findViewById(R.id.tvProduccionBusTramo);
        TextView tvProduccionBusPaxLima = (TextView) convertView.findViewById(R.id.tvProduccionBusPaxLima);
        TextView tvProduccionBusPaxChincha = (TextView) convertView.findViewById(R.id.tvProduccionBusPaxChincha);
        TextView tvProduccionBusPaxPisco = (TextView) convertView.findViewById(R.id.tvProduccionBusPaxPisco);
        TextView tvProduccionBusPaxIca = (TextView) convertView.findViewById(R.id.tvProduccionBusPaxIca);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        //tvProduccionBusNumBus.setText(String.valueOf(entidad.getIdViaje()));
        tvProduccionBusNumBus.setText(entidad.getNumBus());
        tvProduccionBusNumAsiento.setText(String.valueOf(entidad.getNumAsientos()));
        tvProduccionBusHoraViaje.setText(entidad.getHoraViaje());
        tvProduccionBusTramo.setText(entidad.getTramoViaje());
        tvProduccionBusPaxLima.setText(entidad.getPaxTerminalLima());
        tvProduccionBusPaxChincha.setText(entidad.getPaxTerminalChincha());
        tvProduccionBusPaxPisco.setText(entidad.getPaxTerminalPisco());
        tvProduccionBusPaxIca.setText(entidad.getPaxTerminalIca());
        return convertView;
    }


}
