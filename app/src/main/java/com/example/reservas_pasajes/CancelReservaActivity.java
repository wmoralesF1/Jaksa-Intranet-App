package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reservas_pasajes.Adaptadores.AdaptadorListaReservas;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.models.ReservaModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;

public class CancelReservaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etFechaReservaCancelReserva;
    Button btnBuscarReservaCancelReserva;
    ListView lvListaReservasCancelReserva;
    String FechaReserva;
    AdaptadorListaReservas adaptador;
    String TAG;
    ArrayList<ReservaModel> listaReservas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reserva);
        TAG="ANULACION RESERVA";
        etFechaReservaCancelReserva=findViewById(R.id.etFechaReservaCancelReserva);
        btnBuscarReservaCancelReserva=findViewById(R.id.btnBuscarReservaCancelReserva);
        lvListaReservasCancelReserva=findViewById(R.id.lvListaReservasCancelReserva);
        etFechaReservaCancelReserva.setText(Functions.FechaHoy());
        final int year=Functions.NumeroAñoFechaHoy();
        final int month=Functions.NumeroMesFechaHoy();
        final int day=Functions.NumeroDiaFechaHoy();
        etFechaReservaCancelReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(CancelReservaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                etFechaReservaCancelReserva.setText(Functions.Fecha(day,month+1,year));
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });
        FechaReserva=etFechaReservaCancelReserva.getText().toString();
        BuscarReservas();
        btnBuscarReservaCancelReserva.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        FechaReserva=etFechaReservaCancelReserva.getText().toString();
        BuscarReservas();
    }
    private void BuscarReservas(){
        adaptador=new AdaptadorListaReservas(this, new ArrayList<ReservaModel>());
        lvListaReservasCancelReserva.setAdapter(adaptador);
        String ParamNombres, ParamValores;
        ParamNombres="Fecha";
        ParamValores= Functions.StringFormatWsDate(FechaReserva);
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("VerReservas",getString(R.string.key_method_ws_search_cancel_reserva),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    public void AnularReserva(String nroReserva){

        String ParamNombres, ParamValores;
        ParamNombres="NroReserva";
        ParamValores= nroReserva;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setAnulaReserva","Cancel_Reserva",getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsBuscarReservas(String respuesta){
        listaReservas.clear();
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Viaje_id = Cadena[0].split(";");
                String[] FechaReserva = Cadena[1].split(";");
                String[] Dni = Cadena[2].split(";");
                String[] Pasajero = Cadena[3].split(";");
                String[] Asiento = Cadena[4].split(";");
                if(Viaje_id.length>0){
                    for (int i = 0; i <= Viaje_id.length-1; i++) {
                        if(i<=Viaje_id.length-1
                                && i<=FechaReserva.length-1
                                && i<=Dni.length-1
                                && i<=Pasajero.length-1
                                && i<=Asiento.length-1){
                            if((!Viaje_id[i].isEmpty() && !(Viaje_id[i] ==null))
                                    && (!FechaReserva[i].isEmpty() && !(FechaReserva[i] ==null))
                                    && (!Dni[i].isEmpty() && !(Dni[i] ==null))
                                    && (!Pasajero[i].isEmpty() && !(Pasajero[i] ==null))
                                    && (!Asiento[i].isEmpty() && !(Asiento[i] ==null)))
                            {
                                ReservaModel oReservaModel=new ReservaModel();
                                oReservaModel.setIdViaje(Integer.parseInt(Viaje_id[i]));
                                oReservaModel.setFechaViaje(FechaReserva[i]);
                                oReservaModel.setNumeroDocumento(Dni[i]);
                                oReservaModel.setPasajero(Pasajero[i]);
                                oReservaModel.setNumeroAsiento(Asiento[i]);
                                listaReservas.add(oReservaModel);
                            }
                        }
                    }
                    adaptador=new AdaptadorListaReservas(this, listaReservas);
                    lvListaReservasCancelReserva.setAdapter(adaptador);
                    lvListaReservasCancelReserva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ReservaModel itemReserva = (ReservaModel) adapterView.getItemAtPosition(i);
                            if(itemReserva!=null){
                                AnularReserva(String.valueOf(itemReserva.getIdViaje()));
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Reservas, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Reservas, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Reservas, Toast.LENGTH_LONG).show();
        }
    }

    private void wsAnularrReservas(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Delete_Reservas, Toast.LENGTH_LONG).show();
                BuscarReservas();
            }else{
                Toast.makeText(getBaseContext(), R.string.msg_Error_Delete_Reservas, Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Reservas, Toast.LENGTH_LONG).show();
        }
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo.equals(getString(R.string.key_method_ws_search_cancel_reserva))){
            wsBuscarReservas(Respuesta);
        }else if(KeyMetodo.equals(getString(R.string.key_method_ws_cancel_reserva))){
            wsAnularrReservas(Respuesta);
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