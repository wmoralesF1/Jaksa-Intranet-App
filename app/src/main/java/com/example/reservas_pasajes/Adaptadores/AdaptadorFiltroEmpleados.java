package com.example.reservas_pasajes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.EmpleadoModel;
import com.example.reservas_pasajes.models.VentaModel;
import com.example.reservas_pasajes.service.ResponseListGeneric;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class AdaptadorFiltroEmpleados extends BaseAdapter {
    private ArrayList<EmpleadoModel> listEmpleados;
    private Context context;
    public AdaptadorFiltroEmpleados(Context context, ArrayList<EmpleadoModel> listModel) {
        this.context = context;
        this.listEmpleados = listModel;
    }

    @Override
    public int getCount() {
        return listEmpleados.size();
    }

    @Override
    public Object getItem(int position) {
        return listEmpleados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final EmpleadoModel entidad = (EmpleadoModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from((Context) context).inflate(R.layout.item_listview_filtro_empleados, null);

        TextView tvEmpleadoFilterEmployee = (TextView) convertView.findViewById(R.id.tvEmpleadoFilterEmployee);
        TextView tvNumeroDocumentoFilterEmployee = (TextView) convertView.findViewById(R.id.tvNumeroDocumentoFilterEmployee);
        TextView tvCargoFilterEmployee = (TextView) convertView.findViewById(R.id.tvCargoFilterEmployee);
        TextView tvEstadoFilterEmployee = (TextView) convertView.findViewById(R.id.tvEstadoFilterEmployee);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvEmpleadoFilterEmployee.setText(entidad.getEmpNombre() + " " + entidad.getEmpApellidos());
        tvNumeroDocumentoFilterEmployee.setText(entidad.getEmpNumeroDocumento());
        tvCargoFilterEmployee.setText(entidad.getEmpCargoNombre());
        tvEstadoFilterEmployee.setText(entidad.getEmpEstado());


        return convertView;
    }


}
