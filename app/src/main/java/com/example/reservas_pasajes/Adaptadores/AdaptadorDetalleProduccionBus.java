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

public class AdaptadorDetalleProduccionBus   extends BaseAdapter {
    private ArrayList<ViajeModel> listViajes;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public AdaptadorDetalleProduccionBus(Context context, ArrayList<ViajeModel> listModel) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_detail_production_bus, null);

        TextView tvRutaDetailProducctionBus = (TextView) convertView.findViewById(R.id.tvRutaDetailProducctionBus);
        TextView tvTipoVentaDetailProducctionBus = (TextView) convertView.findViewById(R.id.tvTipoVentaDetailProducctionBus);
        TextView tvNumeroPaxDetailProducctionBus = (TextView) convertView.findViewById(R.id.tvNumeroPaxDetailProducctionBus);
        TextView tvMontoDetailProducctionBus = (TextView) convertView.findViewById(R.id.tvMontoDetailProducctionBus);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvRutaDetailProducctionBus.setText(String.valueOf(entidad.getNomRutaViaje()));
        tvTipoVentaDetailProducctionBus.setText(entidad.getTipoVenta());
        tvNumeroPaxDetailProducctionBus.setText(String.valueOf(entidad.getNumPax()));
        tvMontoDetailProducctionBus.setText(String.valueOf(entidad.getMontoPax()));


        return convertView;
    }


}
