package com.example.reservas_pasajes.helper;

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
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.RutaModel;

import java.util.ArrayList;

public class AdaptadorRutas  extends BaseAdapter {
    private ArrayList<RutaModel> listRutas;
    private ArrayList<String> itemsSpinner;
    private Context context;

    public AdaptadorRutas(Context context, ArrayList<RutaModel> listModel) {
        this.context = context;
        this.listRutas = listModel;
    }

    @Override
    public int getCount() {
        return listRutas.size();
    }

    @Override
    public Object getItem(int position) {
        return listRutas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final RutaModel entidad = (RutaModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_rutas, null);
        TextView tvRutaSelectRoute = (TextView) convertView.findViewById(R.id.tvRutaSelectRoute);
        TextView tvPrecioSelectRoute = (TextView) convertView.findViewById(R.id.tvPrecioSelectRoute);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvRutaSelectRoute.setText("Ruta : " + entidad.getRutaDescripcion());
        tvPrecioSelectRoute.setText("Precio Ruta: " + entidad.getRutaPrecio());

        return convertView;
    }
}

