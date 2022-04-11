package com.example.reservas_pasajes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.VentaModel;

import java.util.ArrayList;

public class AdaptadorListaBoletosViaje    extends BaseAdapter {
    private ArrayList<VentaModel> listBoletos;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public AdaptadorListaBoletosViaje(Context context, ArrayList<VentaModel> listModel) {
        this.context = context;
        this.listBoletos = listModel;
    }

    @Override
    public int getCount() {
        return listBoletos.size();
    }

    @Override
    public Object getItem(int position) {
        return listBoletos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final VentaModel entidad = (VentaModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_list_ticket_travel, null);

        TextView tvIdViajeListTicketTravel = (TextView) convertView.findViewById(R.id.tvIdViajeListTicketTravel);
        TextView tvNumBusListTicketTravel = (TextView) convertView.findViewById(R.id.tvNumBusListTicketTravel);
        TextView tvNumBoletoListTicketTravel = (TextView) convertView.findViewById(R.id.tvNumBoletoListTicketTravel);
        TextView tvPrecioListTicketTravel = (TextView) convertView.findViewById(R.id.tvPrecioListTicketTravel);
        TextView tvNumAsientoListTicketTravel = (TextView) convertView.findViewById(R.id.tvNumAsientoListTicketTravel);
        TextView tvEstadoListTicketTravel = (TextView) convertView.findViewById(R.id.tvEstadoListTicketTravel);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvIdViajeListTicketTravel.setText(String.valueOf(entidad.getIdViaje()));
        tvNumBusListTicketTravel.setText(entidad.getNumBus());
        tvNumBoletoListTicketTravel.setText(entidad.getNumBoleto());
        tvPrecioListTicketTravel.setText(String.valueOf(entidad.getPrecio()));
        tvNumAsientoListTicketTravel.setText(entidad.getNumAsiento());
        tvEstadoListTicketTravel.setText(entidad.getEstado());


        return convertView;
    }


}