package com.example.reservas_pasajes.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.ReservaModel;
import com.example.reservas_pasajes.models.ViajeModel;

import java.util.ArrayList;

public class AdaptadorListaViajes  extends BaseAdapter {
    private ArrayList<ViajeModel> listViajes;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public AdaptadorListaViajes(Context context, ArrayList<ViajeModel> listModel) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_list_travel, null);
        TextView tvViajeItemProgrammingTravel = (TextView) convertView.findViewById(R.id.tvViajeItemProgrammingTravel);
        TextView tvTramaItemProgrammingTravel = (TextView) convertView.findViewById(R.id.tvTramaItemProgrammingTravel);
        TextView tvFechaViajeItemProgrammingTravel = (TextView) convertView.findViewById(R.id.tvFechaViajeItemProgrammingTravel);
        TextView tvHoraViajeItemProgrammingTravel = (TextView) convertView.findViewById(R.id.tvHoraViajeItemProgrammingTravel);
        TextView tvOrigenViajeItemProgrammingTravel = (TextView) convertView.findViewById(R.id.tvOrigenViajeItemProgrammingTravel);
        TextView tvServicioViajeItemProgrammingTravel = (TextView) convertView.findViewById(R.id.tvServicioViajeItemProgrammingTravel);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvViajeItemProgrammingTravel.setText(String.valueOf(entidad.getIdViaje()));
        tvTramaItemProgrammingTravel.setText(entidad.getTramoViaje());
        tvFechaViajeItemProgrammingTravel.setText(entidad.getFechaViaje());
        tvHoraViajeItemProgrammingTravel.setText(entidad.getHoraViaje());
        tvOrigenViajeItemProgrammingTravel.setText(entidad.getNomOrigen());
        tvServicioViajeItemProgrammingTravel.setText(entidad.getNomServicio());


        /*if(entidad.getNomServicio().toUpperCase().equals("VIP")){
            GradientDrawable gradiColor=new GradientDrawable();
            gradiColor.setColor(Color.parseColor("#EECD00"));
            llRow.setBackground(gradiColor);
        }*/

        return convertView;
    }
}
