package com.example.reservas_pasajes.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.ListRoutesActivity;
import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.SearchRoutesActivity;
import com.example.reservas_pasajes.models.AsientoModel;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.PasajeroModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.TipoDocumentoModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class adaptadorPasajero extends BaseAdapter {
    private ArrayList<PasajeroModel> listPasajeros;
    private Context context;
    private LayoutInflater inflater;
    EditText etNombresPasajero;
    String TAG="Adaptador de Pasajero";
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
        LinearLayout llfilaPassenger = (LinearLayout) convertView.findViewById(R.id.llfilaPassenger);
        Spinner spTipoDocumentoPasajero = (Spinner) convertView.findViewById(R.id.spTipoDocumentoPasajero);
        EditText etNumeroDocumentoPasajero = (EditText) convertView.findViewById(R.id.etNumeroDocumentoPasajero);
        Button btnAsientoPassenger = (Button) convertView.findViewById(R.id.btnAsientoPassenger);
        etNombresPasajero = (EditText) convertView.findViewById(R.id.etNombresPasajero);
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

        etNumeroDocumentoPasajero.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(etNumeroDocumentoPasajero.getText().length()==8){
                    BuscarPasajero(etNumeroDocumentoPasajero.getText().toString());
                    Toast.makeText(parent.getContext(),"Buscando Datos Pasajero", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return convertView;
    }

    private void BuscarPasajero(String NumeroDocumento){
        String ParamNombres, ParamValores;
        ParamNombres="dni";
        ParamValores=NumeroDocumento;
        adaptadorPasajero.TaskCallWS ws=new adaptadorPasajero.TaskCallWS();
        ws.Parametros("Pasajero","key_method_ws_search_passenger","http://www.grupovaldez.com/","http://www.grupovaldez.pe/WSintranet/WSIntranet.asmx",ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsBuscaDatosPasajero(String respuesta){
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("|");
            if(Cadena.length>0){
                etNombresPasajero.setText(Cadena[2]);
            }


        }
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo=="key_method_ws_search_passenger"){
            wsBuscaDatosPasajero(Respuesta);
        }

    }
    public class TaskCallWS extends AsyncTask<Void, Integer, String> {

        String Metodo;
        String KeyMetodo;
        String Namespace;
        String URL;
        String ParametroNombres, ParametroValores;
        String [] Nombres;
        String [] Valores;
        int NroViaje;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String res = this.consumingWS();
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.isEmpty() && KeyMetodo=="key_method_ws_search_passenger"){
                result=";";
            }
            if (result.equals(""))
            {
                Log.i(TAG, "Error : " + KeyMetodo);
                //Toast.makeText(getBaseContext(), "No se pudo conectar.", Toast.LENGTH_LONG).show();
            }
            else{
                RespuestaWS(result,KeyMetodo);
            }
        }

        public void Parametros(String Metodo,String KeyMetodo,String Namespace, String URL, String ParametroNombres, String ParametroValores)
        {
            this.Metodo=Metodo;
            this.KeyMetodo=KeyMetodo;
            this.Namespace=Namespace;
            this.URL=URL;
            this.ParametroNombres=ParametroNombres;
            this.ParametroValores=ParametroValores;
            this.Nombres=ParametroNombres.split("#");
            this.Valores=ParametroValores.split("#");
        }

        public String consumingWS()
        {
            String accionSoap = Namespace+ Metodo;
            String res = "";

            try {
                SoapObject request = new SoapObject(Namespace, Metodo);

                for(int i=0;i<Nombres.length;i++)
                {
                    if(!Nombres[i].equals(""))
                    {
                        request.addProperty(Nombres[i], Valores[i]);
                    }
                }

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);
                HttpTransportSE transporte = new HttpTransportSE(URL);
                transporte.call(accionSoap, sobre);
                SoapPrimitive resultado_xml =(SoapPrimitive)sobre.getResponse();
                res = resultado_xml.toString();
            }
            catch (Exception e)
            {
                Log.i(TAG, "Error : " + e.getMessage());
            }
            return res;
        }
    }
}
