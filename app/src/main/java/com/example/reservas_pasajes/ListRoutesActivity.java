package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.models.AsientoModel;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.RutaModel;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ListRoutesActivity extends AppCompatActivity {

    private ListView lvItinerarios;
    private adaptadorItinerarios adaptador;
    TextView tvnumresultados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_routes);
        lvItinerarios=(ListView)findViewById(R.id.lvItinerarios);
        tvnumresultados=(TextView)findViewById(R.id.tvnumresultados);

        adaptador=new adaptadorItinerarios(this, SessionManager.getSalidaTurnos());
        lvItinerarios.setAdapter(adaptador);
        lvItinerarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int n;
                ItinerarioModel itemItinerario = (ItinerarioModel) adapterView.getItemAtPosition(i);
                if(itemItinerario!=null){
                    NextSelectRouteTravel(itemItinerario);
                }

            }
        });
        tvnumresultados.setText(SessionManager.getSalidaTurnos().size() +  " Resultados");
    }

    private void NextSelectRouteTravel(ItinerarioModel itemItinerario){
        //itemItinerario.setIdViaje(SessionManager.getViaje().getIdViaje());
        itemItinerario.setNomOrigen(SessionManager.getViaje().getNomOrigen());
        itemItinerario.setNomDestino(SessionManager.getViaje().getNomDestino());
        itemItinerario.setFechaReserva(SessionManager.getViaje().getFechaReservaFormat());
        itemItinerario.setFechaReservaFormat(SessionManager.getViaje().getFechaReserva());
        SessionManager.setTurnoViaje(itemItinerario);
        BuscarAsientosOcupados(String.valueOf(itemItinerario.getIdViaje()));
    }

    private void BuscarAsientosOcupados(String NroViaje){
        String ParamNombres, ParamValores;
        ParamNombres="NroViaje";
        ParamValores=NroViaje;
        ListRoutesActivity.TaskCallWS ws=new ListRoutesActivity.TaskCallWS();
        ws.Parametros("AsientosOcupados",getString(R.string.key_method_ws_list_asientos_ocupados),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void BuscarRutas(String NroViaje,String Destino){
        String ParamNombres, ParamValores;
        ParamNombres="Viaje#pDestino";
        ParamValores=NroViaje + "#" + Destino;
        ListRoutesActivity.TaskCallWS ws=new ListRoutesActivity.TaskCallWS();

        ws.Parametros("Tarifas",getString(R.string.key_method_ws_search_routes_viaje),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsListarAsientosOcupados(String respuesta){
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split(";");
            ArrayList<AsientoModel> listaAsientosOcupados=new ArrayList();
            for (String item: Cadena) {
                boolean exiteAsiento=false;
                if(!item.isEmpty()){
                    for(int i=0;i<=listaAsientosOcupados.size()-1;i++){
                        if(listaAsientosOcupados.get(i).getNroAsiento()==Integer.parseInt(item)){
                            exiteAsiento=true;
                            break;
                        }
                    }
                    if(!exiteAsiento){
                        listaAsientosOcupados.add(new AsientoModel(Integer.parseInt(item)));
                    }
                }
            }

            SessionManager.getTurnoViaje().setListaAsientosOcupados(listaAsientosOcupados);

            //Buscar Rutas del Viaje

            BuscarRutas(String.valueOf(SessionManager.getTurnoViaje().getIdViaje()),SessionManager.getTurnoViaje().getNomDestino());

        }
    }

    private void wsBuscarRutasViaje(String respuesta){
        ArrayList<RutaModel> listaRutasViaje=new ArrayList<>();
        ArrayList<RutaModel> listaRutasViajeEmpty=new ArrayList<>();
        RutaModel oRutaModelEmpty = new RutaModel(0,"Sin Precio" , 0);
        listaRutasViajeEmpty.add(oRutaModelEmpty);
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Ruta_id = Cadena[0].split(";");
                String[] Ruta_Descripcion = Cadena[1].split(";");
                String[] Ruta_Precio = Cadena[2].split(";");
                for (int i = 0; i < Ruta_id.length; i++) {
                    if(!(Ruta_id[i] ==null) && !(Ruta_Descripcion[i] ==null) && !(Ruta_Precio[i]==null)){
                        RutaModel oRutaModel=new RutaModel();
                        oRutaModel.setRutaId(Integer.parseInt(Ruta_id[i]));
                        oRutaModel.setRutaDescripcion(Ruta_Descripcion[i].toUpperCase());
                        oRutaModel.setRutaPrecio(Double.parseDouble(Ruta_Precio[i]));
                        listaRutasViaje.add(oRutaModel);
                    }else{
                        listaRutasViaje.add(oRutaModelEmpty);
                        break;
                    }
                }

            }else{
                listaRutasViaje.add(oRutaModelEmpty);
            }
        }

        SessionManager.getTurnoViaje().setListaRutas(listaRutasViaje);
        SessionManager.getTurnoViaje().setAsientosLibres(SessionManager.getTurnoViaje().getAsientosBus()-SessionManager.getTurnoViaje().getListaAsientosOcupados().size());

        if(SessionManager.getTurnoViaje().getListaRutas().get(0).getRutaPrecio()>0 &&
                SessionManager.getTurnoViaje().getListaRutas().size()>1){
            Intent i;
            i = new Intent(this.getApplicationContext(), SelectRouteActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(getBaseContext(), getString(R.string.msg_Error_Price_Zero), Toast.LENGTH_LONG).show();
        }

    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo==getString(R.string.key_method_ws_list_asientos_ocupados)){
            wsListarAsientosOcupados(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_search_routes_viaje)){
            wsBuscarRutasViaje(Respuesta);
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
            if(result.isEmpty() && KeyMetodo==getString(R.string.key_method_ws_list_asientos_ocupados)){
                result=";";
            }
            if (result.equals(""))
            {
                Log.i("Hola", "Error887 : " + KeyMetodo);
                Toast.makeText(getBaseContext(), "No se pudo conectar.", Toast.LENGTH_LONG).show();
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
                Log.i("Hola", "Error : " + e.getMessage());
            }
            return res;
        }
    }
}