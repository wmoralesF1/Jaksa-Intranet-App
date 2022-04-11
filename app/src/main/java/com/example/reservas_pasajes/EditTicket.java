package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.UsuarioModel;
import com.example.reservas_pasajes.models.VentaModel;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class EditTicket extends AppCompatActivity implements View.OnClickListener {
    EditText etPrecioEditTicket;
    EditText etPrecioNuevoEditTicket;
    EditText etNumeroAsientoEditTicket;
    EditText etNumeroAsientoNuevoEditTicket;
    EditText etIdViajeEditTicket;
    EditText etIdViajeNuevoEditTicket;
    Button btnAnularEditTicket;
    Button btnModificarEditTicket;

    VentaModel oVentaModel=new VentaModel();
    String Operacion="";
    String TAG="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);
        TAG="Edicion de Boleto de Viaje";
        etPrecioEditTicket= findViewById(R.id.etPrecioEditTicket);
        etPrecioNuevoEditTicket= findViewById(R.id.etPrecioNuevoEditTicket);
        etNumeroAsientoEditTicket= findViewById(R.id.etNumeroAsientoEditTicket);
        etNumeroAsientoNuevoEditTicket= findViewById(R.id.etNumeroAsientoNuevoEditTicket);
        etIdViajeEditTicket= findViewById(R.id.etIdViajeEditTicket);
        etIdViajeNuevoEditTicket= findViewById(R.id.etIdViajeNuevoEditTicket);
        btnAnularEditTicket= findViewById(R.id.btnAnularEditTicket);
        btnModificarEditTicket= findViewById(R.id.btnModificarEditTicket);


        Bundle bundle = getIntent().getExtras();
        oVentaModel.setIdViaje(bundle.getInt("idViaje"));
        oVentaModel.setIdBoleto(bundle.getInt("idBoleto"));
        oVentaModel.setPrecio(bundle.getDouble("precio"));
        oVentaModel.setNumAsiento(bundle.getString("asiento"));
        Operacion=bundle.getString("operacion");
        oVentaModel.setNomUsuario(SessionManager.getUsuario().getNomUsuario());
        oVentaModel.setClaveUsuario(SessionManager.getUsuario().getPassUsuario());

        etPrecioEditTicket.setText(String.valueOf(oVentaModel.getPrecio()));
        etNumeroAsientoEditTicket.setText(oVentaModel.getNumAsiento());
        etIdViajeEditTicket.setText(String.valueOf(oVentaModel.getIdViaje()));
        if(Operacion.equals("0")){
            btnAnularEditTicket.setEnabled(true);
            btnModificarEditTicket.setEnabled(false);
            etIdViajeNuevoEditTicket.setEnabled(false);
            etNumeroAsientoNuevoEditTicket.setEnabled(false);
            etPrecioNuevoEditTicket.setEnabled(false);
        }else if(Operacion.equals("1")){
            btnAnularEditTicket.setEnabled(false);
            btnModificarEditTicket.setEnabled(true);
            etIdViajeNuevoEditTicket.setEnabled(true);
            etNumeroAsientoNuevoEditTicket.setEnabled(true);
            etPrecioNuevoEditTicket.setEnabled(true);

        }
        btnAnularEditTicket.setOnClickListener(this);
        btnModificarEditTicket.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnAnularEditTicket.getId()){
            AnularBoletoViaje(oVentaModel);
        }else if(v.getId()==btnModificarEditTicket.getId()){
           if(etIdViajeNuevoEditTicket.getText().toString()!=null && !etIdViajeNuevoEditTicket.getText().toString().isEmpty()
                    && Functions.IsNumeric(etIdViajeNuevoEditTicket.getText().toString())){
                oVentaModel.setIdViaje(Integer.parseInt(etIdViajeNuevoEditTicket.getText().toString()));
            }

            if(etNumeroAsientoNuevoEditTicket.getText().toString()!=null && !etNumeroAsientoNuevoEditTicket.getText().toString().isEmpty()
                    && Functions.IsNumeric(etNumeroAsientoNuevoEditTicket.getText().toString())){
                oVentaModel.setNumAsiento(etNumeroAsientoNuevoEditTicket.getText().toString());
            }

            if(etPrecioNuevoEditTicket.getText().toString()!=null && !etPrecioNuevoEditTicket.getText().toString().isEmpty()
                    && Functions.IsDouble(etPrecioNuevoEditTicket.getText().toString())){
                oVentaModel.setPrecio(Double.parseDouble(etPrecioNuevoEditTicket.getText().toString()));
            }

            ModificarBoletoViaje(oVentaModel);
        }

    }

    private void AnularBoletoViaje(VentaModel entidad){

        String ParamNombres, ParamValores;
        ParamNombres="usuario#clave#NroBoleto#Viaje";
        ParamValores= entidad.getNomUsuario()+"#"+
                entidad.getClaveUsuario()+"#"+
                String.valueOf(entidad.getIdBoleto())+"#"+
                String.valueOf(entidad.getIdViaje());

        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setAnulaBoleto",getString(R.string.key_method_ws_cancel_ticket_travel),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsAnularBoletoViaje(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Calcel_Ticket_Travel_OK, Toast.LENGTH_LONG).show();
                Intent i;
                i = new Intent(this, SearchEditTicket.class);
                startActivity(i);
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Cancel_Ticket_Travel, Toast.LENGTH_LONG).show();
        }
    }

    private void ModificarBoletoViaje(VentaModel entidad){

        String ParamNombres, ParamValores;
        ParamNombres="usuario#clave#NroBoleto#Viaje#precio#asiento";
        ParamValores= entidad.getNomUsuario()+"#"+
                entidad.getClaveUsuario()+"#"+
                String.valueOf(entidad.getIdBoleto())+"#"+
                String.valueOf(entidad.getIdViaje())+"#"+
                String.valueOf(entidad.getPrecio())+"#"+
                entidad.getNumAsiento();
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setmodifyBoleto",getString(R.string.key_method_ws_update_ticket_travel),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsModificarBoletoViaje(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Modific_Ticket_Travel_OK, Toast.LENGTH_LONG).show();
                Intent i;
                i = new Intent(this, SearchEditTicket.class);
                startActivity(i);
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Modific_Ticket_Travel, Toast.LENGTH_LONG).show();
        }
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo==getString(R.string.key_method_ws_cancel_ticket_travel)){
            wsAnularBoletoViaje(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_update_ticket_travel)){
            wsModificarBoletoViaje(Respuesta);
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