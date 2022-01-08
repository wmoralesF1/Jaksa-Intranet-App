package com.example.reservas_pasajes.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.SearchRoutesActivity;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.PasajeroModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.TipoDocumentoModel;

import java.util.ArrayList;

public class adaptadorPasajero extends BaseAdapter {
    private ArrayList<PasajeroModel> listPasajeros;
    private Context context;
    private LayoutInflater inflater;

    public adaptadorPasajero(Context context, ArrayList<PasajeroModel> listModel) {
        this.context = context;
        this.listPasajeros = listModel;
    }

    @Override
    public int getCount() {
        return listPasajeros.size();
    }

    @Override
    public Object getItem(int position) {
        return listPasajeros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final PasajeroModel entidad = (PasajeroModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_pasajero, null);
        Spinner spTipoDocumentoPasajero = (Spinner) convertView.findViewById(R.id.spTipoDocumentoPasajero);
        EditText etNumeroDocumentoPasajero = (EditText) convertView.findViewById(R.id.etNumeroDocumentoPasajero);
        Button btnAsientoPassenger = (Button) convertView.findViewById(R.id.btnAsientoPassenger);

        EditText etNombresPasajero = (EditText) convertView.findViewById(R.id.etNombresPasajero);
        EditText etApellidoPaternoPasajero = (EditText) convertView.findViewById(R.id.etApellidoPaternoPasajero);
        EditText etApellidoMaternoPasajero = (EditText) convertView.findViewById(R.id.etApellidoMaternoPasajero);
        EditText etPrecioPromocionalPasajero = (EditText) convertView.findViewById(R.id.etPrecioPromocionalPasajero);


        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        etNumeroDocumentoPasajero.setText(entidad.getNumDocumento());
        etNombresPasajero.setText(entidad.getNombrePasajero());
        etApellidoPaternoPasajero.setText(entidad.getApellidoPaternoPasajero());
        etApellidoMaternoPasajero.setText(entidad.getApellidoMaternoPasajero());
        etPrecioPromocionalPasajero.setText(String.valueOf(entidad.getPrecioPromocionalAsiento()));
        ArrayAdapter<TipoDocumentoModel> listaTiposDocumentosAdapter=new ArrayAdapter<TipoDocumentoModel>(parent.getContext(),
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getListaTiposDocumentos());

        spTipoDocumentoPasajero.setAdapter(listaTiposDocumentosAdapter);
        btnAsientoPassenger.setText(entidad.getNumAsiento());
        /*spTipoDocumentoPasajero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                TipoDocumentoModel entidad= (TipoDocumentoModel) parent.getItemAtPosition(index);
                sDestino.setAdapter(null);
                viaje.setNomOrigen(entidad.getNomTerminal());
                BuscarDestinosTerminal(entidad.getNomTerminal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        return convertView;
    }
}
