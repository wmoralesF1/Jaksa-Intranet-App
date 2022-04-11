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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
    ListView lvListaViajeSearchProductionBus;
    String FechaViaje;
    String Terminal;
    AdaptadorListaProgramacionViaje adaptador;
    ArrayList<ViajeModel> listaViajes=new ArrayList<>();
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_production_bus);
        TAG="Busqueda de Producción Buses";
        etFechaViajeSearchProductionBus=findViewById(R.id.etFechaViajeSearchProductionBus);
        spTerminalSearchProductionBus=findViewById(R.id.spTerminalSearchProductionBus);
        btnBuscarViajesSearchProductionBus=findViewById(R.id.btnBuscarViajesSearchProductionBus);
        lvListaViajeSearchProductionBus=findViewById(R.id.lvListaViajeSearchProductionBus);

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
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnBuscarViajesSearchProductionBus.getId()){
            FechaViaje=btnBuscarViajesSearchProductionBus.getText().toString();
            BuscarViajes();
        }

    }

    private void BuscarViajes(){
        adaptador=new AdaptadorListaProgramacionViaje(this, new ArrayList<ViajeModel>());
        lvListaViajeSearchProductionBus.setAdapter(adaptador);
        String ParamNombres, ParamValores;
        ParamNombres="Fecha#Terminal";
        ParamValores= Functions.StringFormatWsDate(FechaViaje)+"#"+Terminal;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("PRGViajes",getString(R.string.key_method_ws_search_list_viaje),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
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