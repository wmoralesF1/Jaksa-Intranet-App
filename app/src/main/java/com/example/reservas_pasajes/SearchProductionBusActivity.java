package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reservas_pasajes.Adaptadores.AdaptadorListaProduccionBuses;
import com.example.reservas_pasajes.Adaptadores.AdaptadorListaProgramacionViaje;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class SearchProductionBusActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etFechaViajeSearchProductionBus;
    Spinner spTerminalSearchProductionBus;
    Button btnBuscarViajesSearchProductionBus;
    Button btnBuscarViajesViewProductionBus;
    ListView lvListaViajeSearchProductionBus;
    ListView lvListaViajeViewProductionBus;
    HorizontalScrollView hsListViewProductionBus;
    LinearLayout llListViewProgrammingTravel;
    String FechaViaje;
    String Terminal;
    AdaptadorListaProgramacionViaje adaptador;
    AdaptadorListaProduccionBuses adaptadorListaProduccionBuses;
    ArrayList<ViajeModel> listaViajes=new ArrayList<>();
    ArrayList<ViajeModel> listaProduccionBus=new ArrayList<>();

    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_production_bus);
        TAG="Busqueda de Producción Buses";
        etFechaViajeSearchProductionBus=findViewById(R.id.etFechaViajeSearchProductionBus);
        spTerminalSearchProductionBus=findViewById(R.id.spTerminalSearchProductionBus);
        btnBuscarViajesSearchProductionBus=findViewById(R.id.btnBuscarViajesSearchProductionBus);
        btnBuscarViajesViewProductionBus=findViewById(R.id.btnBuscarViajesViewProductionBus);
        hsListViewProductionBus=findViewById(R.id.hsListViewProductionBus);
        llListViewProgrammingTravel=findViewById(R.id.llListViewProgrammingTravel);
        lvListaViajeSearchProductionBus=findViewById(R.id.lvListaViajeSearchProductionBus);
        lvListaViajeViewProductionBus=findViewById(R.id.lvListaViajeViewProductionBus);

        etFechaViajeSearchProductionBus.setText(Functions.FechaHoy());
        final int year=Functions.NumeroAñoFechaHoy();
        final int month=Functions.NumeroMesFechaHoy();
        final int day=Functions.NumeroDiaFechaHoy();
        etFechaViajeSearchProductionBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(SearchProductionBusActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                etFechaViajeSearchProductionBus.setText(Functions.Fecha(day,month+1,year));
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });
        FechaViaje=etFechaViajeSearchProductionBus.getText().toString();

        ArrayAdapter<TerminalModel> terminalesAdapter=new ArrayAdapter<TerminalModel>(SearchProductionBusActivity.this,
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getListaTerminales());

        spTerminalSearchProductionBus.setAdapter(terminalesAdapter);
        spTerminalSearchProductionBus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                Terminal=entidad.getNomTerminal();
                BuscarViajes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnBuscarViajesSearchProductionBus.setOnClickListener(this);
        btnBuscarViajesViewProductionBus.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnBuscarViajesSearchProductionBus.getId()){
            FechaViaje=etFechaViajeSearchProductionBus.getText().toString();
            BuscarViajes();
        }else if(v.getId()==btnBuscarViajesViewProductionBus.getId()){
            FechaViaje=etFechaViajeSearchProductionBus.getText().toString();
            BuscarProduccionViajes();
        }

    }

    private void BuscarViajes(){
        hsListViewProductionBus.setVisibility(View.GONE);
        llListViewProgrammingTravel.setVisibility(View.VISIBLE);
        adaptador=new AdaptadorListaProgramacionViaje(this, new ArrayList<ViajeModel>());
        lvListaViajeSearchProductionBus.setAdapter(adaptador);
        String ParamNombres, ParamValores;
        ParamNombres="Fecha#Terminal";
        ParamValores= Functions.StringFormatWsDate(FechaViaje)+"#"+Terminal;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("PRGViajes",getString(R.string.key_method_ws_search_list_viaje),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void BuscarProduccionViajes(){
        hsListViewProductionBus.setVisibility(View.VISIBLE);
        llListViewProgrammingTravel.setVisibility(View.GONE);
        adaptadorListaProduccionBuses=new AdaptadorListaProduccionBuses(this, new ArrayList<ViajeModel>());
        lvListaViajeViewProductionBus.setAdapter(adaptadorListaProduccionBuses);
        String ParamNombres, ParamValores;
        ParamNombres="Fecha#Terminal";
        ParamValores= Functions.StringFormatWsDate(FechaViaje)+"#"+Terminal;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("ProdViajes",getString(R.string.key_method_ws_view_production_bus),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsBuscarViajes(String respuesta){
        listaViajes.clear();
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Viaje_id = Cadena[1].split(";");
                String[] HoraPartida = Cadena[2].split(";");
                String[] NumBus = Cadena[3].split(";");
                String[] CodTramo = Cadena[4].split(";");
                String[] CodServicio = Cadena[5].split(";");
                if(Viaje_id.length>0){
                    for (int i = 0; i <= Viaje_id.length-1; i++) {
                        if(i<=Viaje_id.length-1
                                && i<=HoraPartida.length-1
                                && i<=NumBus.length-1
                                && i<=CodTramo.length-1
                                && i<=CodServicio.length-1){
                            if((!Viaje_id[i].isEmpty() && !(Viaje_id[i] ==null))
                                    && (!HoraPartida[i].isEmpty() && !(HoraPartida[i] ==null))
                                    && (!NumBus[i].isEmpty() && !(NumBus[i] ==null))
                                    && (!CodTramo[i].isEmpty() && !(CodTramo[i] ==null))
                                    && (!CodServicio[i].isEmpty() && !(CodServicio[i] ==null)))
                            {
                                ViajeModel oViajeModel=new ViajeModel();
                                oViajeModel.setIdViaje(Integer.parseInt(Viaje_id[i]));
                                oViajeModel.setHoraViaje(HoraPartida[i]);
                                oViajeModel.setNumBus(NumBus[i]);
                                oViajeModel.setIdTramoViaje(CodTramo[i]);
                                oViajeModel.setIdServicio(CodServicio[i]);
                                listaViajes.add(oViajeModel);
                            }
                        }
                    }
                    adaptador=new AdaptadorListaProgramacionViaje(this, listaViajes);
                    lvListaViajeSearchProductionBus.setAdapter(adaptador);
                    lvListaViajeSearchProductionBus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                            ViajeModel oViajeModel = (ViajeModel) adapterView.getItemAtPosition(index);
                            Continuar(oViajeModel);
                        }
                    });
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Viajes, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Viajes, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Viajes, Toast.LENGTH_LONG).show();
        }
    }

    private void wsBuscarProduccionBus(String respuesta){
        listaProduccionBus.clear();
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] ArrayViaje_id = Cadena[0].split(";");
                String[] ArrayBuses = Cadena[1].split(";");
                String[] ArrayHoraViajes = Cadena[2].split(";");
                String[] ArrayTramo = Cadena[3].split(";");
                String[] ArrayCodServicio = Cadena[4].split(";");
                String[] ArrayAsientos = Cadena[5].split(";");
                String[] ArrayTerminalLima = Cadena[11].split(";");
                String[] ArrayTerminalChincha = Cadena[12].split(";");
                String[] ArrayTerminalPisco = Cadena[13].split(";");
                String[] ArrayTerminalIca = Cadena[14].split(";");


                if(ArrayViaje_id.length>0){
                    for (int i = 0; i <= ArrayViaje_id.length-1; i++) {
                        if(i<=ArrayViaje_id.length-1){
                            ViajeModel oViajeModel=new ViajeModel();

                            if(i<=ArrayBuses.length-1){
                                oViajeModel.setIdViaje(Integer.parseInt(ArrayViaje_id[i]));
                            }else{
                                oViajeModel.setIdViaje(0);
                            }

                            if(i<=ArrayBuses.length-1){
                                oViajeModel.setNumBus(ArrayBuses[i]);
                            }else{
                                oViajeModel.setNumBus("");
                            }

                            if(i<=ArrayHoraViajes.length-1){
                                oViajeModel.setHoraViaje(ArrayHoraViajes[i]);
                            }else{
                                oViajeModel.setHoraViaje("");
                            }
                            if(i<=ArrayTramo.length-1){
                                oViajeModel.setTramoViaje(ArrayTramo[i]);
                            }else{
                                oViajeModel.setTramoViaje("");
                            }
                            if(i<=ArrayAsientos.length-1){
                                oViajeModel.setNumAsientos(Integer.parseInt(ArrayAsientos[i]));
                            }else{
                                oViajeModel.setNumAsientos(0);
                            }

                            if(i<=ArrayCodServicio.length-1){
                                oViajeModel.setIdServicio(ArrayCodServicio[i]);
                            }else{
                                oViajeModel.setIdServicio("");
                            }

                            if(i<=ArrayTerminalLima.length-1){
                                oViajeModel.setPaxTerminalLima(ArrayTerminalLima[i]);
                            }else{
                                oViajeModel.setPaxTerminalLima("");
                            }

                            if(i<=ArrayTerminalChincha.length-1){
                                oViajeModel.setPaxTerminalChincha(ArrayTerminalChincha[i]);
                            }else{
                                oViajeModel.setPaxTerminalChincha("");
                            }

                            if(i<=ArrayTerminalPisco.length-1){
                                oViajeModel.setPaxTerminalPisco(ArrayTerminalPisco[i]);
                            }else{
                                oViajeModel.setPaxTerminalPisco("");
                            }

                            if(i<=ArrayTerminalIca.length-1){
                                oViajeModel.setPaxTerminalIca(ArrayTerminalIca[i]);
                            }else{
                                oViajeModel.setPaxTerminalIca("");
                            }
                            listaProduccionBus.add(oViajeModel);
                        }
                    }
                    Log.i(TAG, "Error887 : " + listaProduccionBus.size());
                    adaptadorListaProduccionBuses=new AdaptadorListaProduccionBuses(this, listaProduccionBus);
                    lvListaViajeViewProductionBus.setAdapter(adaptadorListaProduccionBuses);
                    /*lvListaViajeViewProductionBus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                            ViajeModel oViajeModel = (ViajeModel) adapterView.getItemAtPosition(index);
                            Continuar(oViajeModel);
                        }
                    });*/
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Produccion_Buses, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Produccion_Buses, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Produccion_Buses, Toast.LENGTH_LONG).show();
        }
    }


    private void Continuar(ViajeModel entidad){
        Intent i;
        i = new Intent(this, ProductionBusActivity.class);
        i.putExtra("idViaje", entidad.getIdViaje());
        i.putExtra("numBus", entidad.getNumBus());
        i.putExtra("horaPartida", entidad.getHoraViaje());
        startActivity(i);
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo.equals(getString(R.string.key_method_ws_search_list_viaje))){
            wsBuscarViajes(Respuesta);
        }else if(KeyMetodo.equals(getString(R.string.key_method_ws_view_production_bus))){
            wsBuscarProduccionBus(Respuesta);
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
            if (result.equals(""))
            {
                Toast.makeText(getBaseContext(), "No se pudo conectar.", Toast.LENGTH_LONG).show();
            }
            else{
                RespuestaWS(result, KeyMetodo);
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