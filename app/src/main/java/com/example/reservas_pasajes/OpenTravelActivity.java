package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class OpenTravelActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etFechaViajeOpenTravel;
    Spinner spTerminalOpenTravel;
    Button btnBuscarViajesOpenTravel;
    Button btnNuevoViajeOpenTravel;
    Button btnEditarViajeOpenTravel;
    ListView lvListaViajeOpenTravel;
    AdaptadorListaProgramacionViaje adaptador;
    String FechaViaje;
    String Terminal;
    ArrayList<ViajeModel> listaViajes=new ArrayList<>();
    ViajeModel oViajeModel=new ViajeModel();
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_travel);
        TAG="Lista Programacion Viaje";
        etFechaViajeOpenTravel=findViewById(R.id.etFechaViajeOpenTravel);
        spTerminalOpenTravel=findViewById(R.id.spTerminalOpenTravel);
        btnBuscarViajesOpenTravel=findViewById(R.id.btnBuscarViajesOpenTravel);
        btnNuevoViajeOpenTravel=findViewById(R.id.btnNuevoViajeOpenTravel);
        btnEditarViajeOpenTravel=findViewById(R.id.btnEditarViajeOpenTravel);
        lvListaViajeOpenTravel=findViewById(R.id.lvListaViajeOpenTravel);



        etFechaViajeOpenTravel.setText(Functions.FechaHoy());
        final int year=Functions.NumeroAñoFechaHoy();
        final int month=Functions.NumeroMesFechaHoy();
        final int day=Functions.NumeroDiaFechaHoy();
        etFechaViajeOpenTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(OpenTravelActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                etFechaViajeOpenTravel.setText(Functions.Fecha(day,month+1,year));
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });
        lvListaViajeOpenTravel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                oViajeModel = (ViajeModel) adapterView.getItemAtPosition(index);
                for (int i = 0; i <= lvListaViajeOpenTravel.getChildCount()-1; i++) {
                    if(index == i ){
                        //lvListaViajeOpenTravel.getChildAt(index).setBackgroundColor(Color.YELLOW);
                        btnEditarViajeOpenTravel.setEnabled(true);
                    }else{
                        lvListaViajeOpenTravel.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        FechaViaje=etFechaViajeOpenTravel.getText().toString();
        ArrayAdapter<TerminalModel> terminalesAdapter=new ArrayAdapter<TerminalModel>(OpenTravelActivity.this,
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getListaTerminales());

        spTerminalOpenTravel.setAdapter(terminalesAdapter);
        spTerminalOpenTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btnBuscarViajesOpenTravel.setOnClickListener(this);
        btnNuevoViajeOpenTravel.setOnClickListener(this);
        btnEditarViajeOpenTravel.setOnClickListener(this);



    }

    private void BuscarViajes(){
        adaptador=new AdaptadorListaProgramacionViaje(this, new ArrayList<ViajeModel>());
        lvListaViajeOpenTravel.setAdapter(adaptador);
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
                    lvListaViajeOpenTravel.setAdapter(adaptador);
                    lvListaViajeOpenTravel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                            oViajeModel = (ViajeModel) adapterView.getItemAtPosition(index);
                            if(oViajeModel!=null){
                                btnEditarViajeOpenTravel.setEnabled(true);
                            }else{
                                btnEditarViajeOpenTravel.setEnabled(false);
                            }
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

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo.equals(getString(R.string.key_method_ws_search_list_viaje))){
            wsBuscarViajes(Respuesta);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnBuscarViajesOpenTravel.getId()){
            FechaViaje=etFechaViajeOpenTravel.getText().toString();
            BuscarViajes();
        }else if(v.getId()==btnNuevoViajeOpenTravel.getId()){
            Intent i;
            i = new Intent(this, ProgrammingTravelActivity.class);
            i.putExtra("idViaje", "0");
            i.putExtra("nomTerminal", "");
            startActivity(i);
        }else if(v.getId()==btnEditarViajeOpenTravel.getId()){
            Intent i;
            i = new Intent(this, ProgrammingTravelActivity.class);
            i.putExtra("idViaje", String.valueOf(oViajeModel.getIdViaje()));
            i.putExtra("nomTerminal", ((TerminalModel)spTerminalOpenTravel.getSelectedItem()).getNomTerminal());
            startActivity(i);
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