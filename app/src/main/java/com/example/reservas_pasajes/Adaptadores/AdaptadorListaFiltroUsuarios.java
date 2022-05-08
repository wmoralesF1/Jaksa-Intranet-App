package com.example.reservas_pasajes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.EmpleadoModel;
import com.example.reservas_pasajes.models.UsuarioModel;

import java.util.ArrayList;

public class AdaptadorListaFiltroUsuarios extends BaseAdapter {
    private ArrayList<UsuarioModel> listUsuarios;
    private Context context;
    public AdaptadorListaFiltroUsuarios(Context context, ArrayList<UsuarioModel> listModel) {
        this.context = context;
        this.listUsuarios = listModel;
    }

    @Override
    public int getCount() {
        return listUsuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final UsuarioModel entidad = (UsuarioModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from((Context) context).inflate(R.layout.item_listview_filtro_usuarios, null);

        TextView tvNomUsuarioFilterUser = (TextView) convertView.findViewById(R.id.tvNomUsuarioFilterUser);
        TextView tvLocalFilterUser = (TextView) convertView.findViewById(R.id.tvLocalFilterUser);
        TextView tvEstadoFilterUser = (TextView) convertView.findViewById(R.id.tvEstadoFilterUser);

        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        tvNomUsuarioFilterUser.setText(entidad.getNomUsuario());
        tvLocalFilterUser.setText(entidad.getNomTerminal());
        tvEstadoFilterUser.setText(entidad.getEstado());


        return convertView;
    }


}
