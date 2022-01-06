package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.AsientoModel;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.MapBusModel;
import com.example.reservas_pasajes.models.RutaModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class SearchRoutesActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<TerminalModel> listaDestinos=new ArrayList<>();
    ArrayList<ItinerarioModel> salidaTurnos=new ArrayList<>();
    ViajeModel viaje = new ViajeModel();
    EditText etFechaReserva;
    Spinner sOrigen;
    Spinner sDestino;
    Button btnBuscarItinerarios;
    AlertDialog pDialog=null;
    String fechaReserva;
    int IndexAsientosOcupados=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_routes);

        sOrigen=findViewById(R.id.sOrigen);
        sDestino=findViewById(R.id.sDestino);
        etFechaReserva= findViewById(R.id.etFechaReserva);
        Calendar calendar= Calendar.getInstance();
        pDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .build();
        final String año=String.valueOf(calendar.get(Calendar.YEAR));
        final String mes=new DecimalFormat("00").format(calendar.get(Calendar.MONTH)+1);
        final String dia=new DecimalFormat("00").format(calendar.get(Calendar.DAY_OF_MONTH));

        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        fechaReserva= año + mes + dia;
        etFechaReserva.setText(dia + "/" + mes+ "/" + año);
        viaje.setFechaReservaFormat(etFechaReserva.getText().toString());
        closeTecladoMovil();
        etFechaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(SearchRoutesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month=month+1;
                                String año=String.valueOf(year);
                                String mes=new DecimalFormat("00").format(month);
                                String dia=new DecimalFormat("00").format(day);
                                String date=dia+"/"+ mes + "/"+ año;
                                fechaReserva= año + mes + dia;
                                etFechaReserva.setText(date);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }


        });

        ArrayAdapter<TerminalModel> terminalesAdapter=new ArrayAdapter<TerminalModel>(SearchRoutesActivity.this,
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getListaTerminales());

        sOrigen.setAdapter(terminalesAdapter);
        sDestino.setAdapter(terminalesAdapter);
        sOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                sDestino.setAdapter(null);
                viaje.setNomOrigen(entidad.getNomTerminal());
                BuscarDestinosTerminal(entidad.getNomTerminal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                viaje.setNomDestino(entidad.getNomTerminal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBuscarItinerarios=findViewById(R.id.btnBuscarItinerarios);
        btnBuscarItinerarios.setOnClickListener(this);

    }

    private void closeTecladoMovil() {
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void BuscarDestinosTerminal(String Terminal){
        String ParamNombres, ParamValores;
        ParamNombres="Terminal";
        ParamValores=Terminal;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("Destinos",getString(R.string.key_method_ws_search_destino),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void BuscarTurnos(){
        String ParamNombres, ParamValores;
        ParamNombres="Fecha#Terminal#destino";
        ParamValores=SessionManager.getViaje().getFechaReserva() + "#" +  SessionManager.getViaje().getNomOrigen() + "#" + SessionManager.getViaje().getNomDestino();
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("Salidas",getString(R.string.key_method_ws_search_salidas),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void BuscarAsientosOcupados(String NroViaje){
        String ParamNombres, ParamValores;
        ParamNombres="NroViaje";
        ParamValores=NroViaje;
        TaskCallWS ws=new TaskCallWS();
        if(!NroViaje.isEmpty()){
            ws.NroViaje=Integer.parseInt(NroViaje);
        }
        ws.Parametros("AsientosOcupados",getString(R.string.key_method_ws_list_asientos_ocupados),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void BuscarRutas(String NroViaje,String Destino){
        String ParamNombres, ParamValores;
        ParamNombres="Viaje#pDestino";
        ParamValores=NroViaje + "#" + Destino;
        TaskCallWS ws=new TaskCallWS();
        if(!NroViaje.isEmpty()){
            ws.NroViaje=Integer.parseInt(NroViaje);
        }
        ws.Parametros("Tarifas",getString(R.string.key_method_ws_search_routes_viaje),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }



    @Override
    public void onClick(View v) {

        viaje.setFechaReserva(fechaReserva);
        SessionManager.setViaje(viaje);
        BuscarTurnos();
    }

    private void wsBuscarTurnos(String respuesta){
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Viaje_id = Cadena[0].split(";");
                String[] Servicio = Cadena[1].split(";");
                String[] HoraReserva = Cadena[2].split(";");
                String[] AsientoBus = Cadena[3].split(";");
                String[] AsientoBusPrimerPiso = Cadena[3].split(";");

                for (int i = 0; i < Viaje_id.length; i++) {
                    if(!(Viaje_id[i] ==null) && !(Servicio[i] ==null) && !(AsientoBus[i]==null)){
                        ItinerarioModel oItinerarioModel=new ItinerarioModel();
                        oItinerarioModel.setIdViaje(Integer.parseInt(Viaje_id[i]));
                        oItinerarioModel.setNomServicio(Servicio[i].toUpperCase());
                        oItinerarioModel.setHoraReserva(HoraReserva[i]);
                        oItinerarioModel.setAsientosBus(Integer.parseInt(AsientoBus[i]));
                        oItinerarioModel.setAsientosBusPrimerPiso(Integer.parseInt(AsientoBusPrimerPiso[i]));
                        oItinerarioModel.setAsientosBusSegundoPiso(Integer.parseInt(AsientoBus[i])-Integer.parseInt(AsientoBusPrimerPiso[i]));
                        salidaTurnos.add(oItinerarioModel);
                    }

                }
                //Obtener Asientos Ocupados
                IndexAsientosOcupados=0;
                for (ItinerarioModel item:salidaTurnos){
                    BuscarAsientosOcupados(String.valueOf(item.getIdViaje()));
                }
            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Salidas, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Salidas, Toast.LENGTH_LONG).show();
        }
    }

    private void wsBuscarDestinos(String respuesta){
        listaDestinos.clear();
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split(";");
            if(Cadena.length>0){
                for (String item: Cadena) {
                    if(!item.toString().isEmpty()){
                        TerminalModel oTerminalModel=new TerminalModel(0,item.toString());
                        listaDestinos.add(oTerminalModel);
                    }
                }
                if(listaDestinos.size()<1){
                    Toast.makeText(getBaseContext(), R.string.msg_Error_Destinos, Toast.LENGTH_LONG).show();
                }else{
                    sDestino.setAdapter(null);
                    ArrayAdapter<TerminalModel> destinosAdapter=new ArrayAdapter<TerminalModel>(SearchRoutesActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, listaDestinos);
                    sDestino.setAdapter(destinosAdapter);
                }
            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_Destinos, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Destinos, Toast.LENGTH_LONG).show();
        }
    }

    private void wsListarAsientosOcupados(String respuesta,String NroViaje){
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

            for(int i=0;i<=salidaTurnos.size()-1;i++){
                if(salidaTurnos.get(i).getIdViaje()==Integer.parseInt(NroViaje)){
                    salidaTurnos.get(i).setListaAsientosOcupados(listaAsientosOcupados);
                    salidaTurnos.get(i).setAsientosLibres(salidaTurnos.get(i).getAsientosBus()-salidaTurnos.get(i).getListaAsientosOcupados().size());
                    break;
                }
            }

            if(salidaTurnos.size()<1){
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Salidas, Toast.LENGTH_LONG).show();
            }else{
                if(IndexAsientosOcupados==salidaTurnos.size()){
                    IndexAsientosOcupados=0;
                    //Obtener Rutas de los Viajes
                    for (ItinerarioModel item:salidaTurnos){
                        BuscarRutas(String.valueOf(item.getIdViaje()),SessionManager.getViaje().getNomDestino());
                    }
                }
            }
        }
    }

    private void wsBuscarRutasViaje(String respuesta,String NroViaje){
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
        for(int i=0;i<=salidaTurnos.size()-1;i++){
            if(salidaTurnos.get(i).getIdViaje()==Integer.parseInt(NroViaje)){
                salidaTurnos.get(i).setListaRutas(listaRutasViaje);
                break;
            }
        }
        if(IndexAsientosOcupados==salidaTurnos.size()){
            for(int i=0;i<=salidaTurnos.size()-1;i++){
                if(salidaTurnos.get(i).getListaRutas()==null){
                    salidaTurnos.get(i).setListaRutas(listaRutasViajeEmpty);
                    break;
                }
            }
            SessionManager.setSalidaTurnos(salidaTurnos);
            Intent i;
            i = new Intent(this.getApplicationContext(), ListRoutesActivity.class);
            startActivity(i);
        }
    }

    private void RespuestaWS(String Respuesta,String NroViaje,String KeyMetodo){
        if(KeyMetodo==getString(R.string.key_method_ws_search_destino)){
            wsBuscarDestinos(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_search_salidas)){
            wsBuscarTurnos(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_list_asientos_ocupados)){
            wsListarAsientosOcupados(Respuesta,NroViaje);
        }else if(KeyMetodo==getString(R.string.key_method_ws_search_routes_viaje)){
            wsBuscarRutasViaje(Respuesta,NroViaje);
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
            if(KeyMetodo==getString(R.string.key_method_ws_search_destino)){
                pDialog.setMessage(getString(R.string.msg_Search_Destinos));
                pDialog.show();
            }else if(KeyMetodo==getString(R.string.key_method_ws_search_salidas) ||
                    KeyMetodo==getString(R.string.key_method_ws_list_asientos_ocupados) ||
                    KeyMetodo==getString(R.string.key_method_ws_search_routes_viaje)){
                pDialog.setMessage(getString(R.string.msg_Search_Routes));
                pDialog.show();
            }
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
                if(KeyMetodo!=getString(R.string.key_method_ws_list_asientos_ocupados) &&
                        KeyMetodo!=getString(R.string.key_method_ws_search_routes_viaje)){
                    Toast.makeText(getBaseContext(), "No se pudo conectar.", Toast.LENGTH_LONG).show();
                }
            }
            else{
                if(KeyMetodo==getString(R.string.key_method_ws_list_asientos_ocupados) ||
                        KeyMetodo==getString(R.string.key_method_ws_search_routes_viaje)){
                    RespuestaWS(result,String.valueOf(NroViaje), KeyMetodo);
                }else{
                    RespuestaWS(result,"",KeyMetodo);
                    if(KeyMetodo==getString(R.string.key_method_ws_search_destino)){
                        pDialog.dismiss();
                    }

                }
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
            IndexAsientosOcupados++;
            return res;
        }
    }
}