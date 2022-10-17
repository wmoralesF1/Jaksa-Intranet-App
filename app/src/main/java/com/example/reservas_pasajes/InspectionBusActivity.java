package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.ExpReg;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.InspeccionBusModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class InspectionBusActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner spZonaInspectionBus;
    EditText etCantPasajOficinaInspectionBus;
    EditText etCantPasajPuntosVentasInspectionBus;
    EditText etCantPasajRutaInspectionBus;
    EditText etTotalVentasRutaInspectionBus;
    EditText etObservacionInspectionBus;
    Button btnGrabarInspectionBus;
    InspeccionBusModel oInspeccionBusModel;
    ArrayList<String> listZonas=new ArrayList<>();
    String ZonaInspeccion;
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_bus);
        TAG="Inspección de Bus";
        oInspeccionBusModel=new InspeccionBusModel();
        spZonaInspectionBus=findViewById(R.id.spZonaInspectionBus);
        etCantPasajOficinaInspectionBus=findViewById(R.id.etCantPasajOficinaInspectionBus);
        etCantPasajPuntosVentasInspectionBus=findViewById(R.id.etCantPasajPuntosVentasInspectionBus);
        etCantPasajRutaInspectionBus=findViewById(R.id.etCantPasajRutaInspectionBus);
        etTotalVentasRutaInspectionBus=findViewById(R.id.etTotalVentasRutaInspectionBus);
        etObservacionInspectionBus=findViewById(R.id.etObservacionInspectionBus);
        btnGrabarInspectionBus=findViewById(R.id.btnGrabarInspectionBus);
        ListarZonas();
        Bundle bundle = getIntent().getExtras();
        oInspeccionBusModel.setIdViaje(bundle.getInt("idViaje"));
        btnGrabarInspectionBus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnGrabarInspectionBus.getId()){
            if(!ValidarFormUsuario()){
                return;
            }
            oInspeccionBusModel.setZona(spZonaInspectionBus.getSelectedItem().toString());
            oInspeccionBusModel.setPaxOficina(Integer.parseInt(etCantPasajOficinaInspectionBus.getText().toString()));
            oInspeccionBusModel.setPaxPuntosVentas(Integer.parseInt(etCantPasajPuntosVentasInspectionBus.getText().toString()));
            oInspeccionBusModel.setPaxRuta(Integer.parseInt(etCantPasajRutaInspectionBus.getText().toString()));
            oInspeccionBusModel.setTotalVentaRuta(Double.parseDouble(etTotalVentasRutaInspectionBus.getText().toString()));
            oInspeccionBusModel.setObservacion(etObservacionInspectionBus.getText().toString());

            RegistroInspeccion();
        }
    }

    private void ListarZonas(){
        String ParamNombres, ParamValores;
        ParamNombres="";
        ParamValores= "";

        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("ZonasInspeccion",getString(R.string.key_method_ws_list_zona_inspection_bus),
                getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),
                ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsListarZonas(String respuesta){
        if(listZonas!=null && listZonas.size()>0){
            listZonas.clear();
        }
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split(";");
            if(Cadena.length>0){
                for (int i = 0; i <= Cadena.length-1; i++) {
                    if(i<=Cadena.length-1){
                        if((!Cadena[i].isEmpty() && !(Cadena[i] ==null)))
                        {
                            listZonas.add(Cadena[i]);

                        }
                    }
                }
                spZonaInspectionBus.setAdapter(null);
                ArrayAdapter<String> ZonasInspeccionAdapter=new ArrayAdapter<String>(InspectionBusActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, listZonas);
                spZonaInspectionBus.setAdapter(ZonasInspeccionAdapter);

                spZonaInspectionBus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ZonaInspeccion= parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_Zona_Inspection_Bus, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Zona_Inspection_Bus, Toast.LENGTH_LONG).show();
        }
    }

    private void RegistroInspeccion(){

        String ParamNombres, ParamValores;
        ParamNombres="usuario#claveUs#NroViaje#zona#paxOfi#paxPuntoV#paxRuta#MontoVentaRuta#observacion";
        ParamValores= SessionManager.getUsuario().getNomUsuario()+"#"+
                SessionManager.getUsuario().getPassUsuario()+"#"+
                String.valueOf(oInspeccionBusModel.getIdViaje())+"#"+
                oInspeccionBusModel.getZona()+"#"+
                String.valueOf(oInspeccionBusModel.getPaxOficina())+"#"+
                String.valueOf(oInspeccionBusModel.getPaxPuntosVentas())+"#"+
                String.valueOf(oInspeccionBusModel.getPaxRuta())+"#"+
                String.valueOf(oInspeccionBusModel.getTotalVentaRuta())+"#"+
                oInspeccionBusModel.getObservacion();


                TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setInspeccion",getString(R.string.key_method_ws_insert_inspection),
                getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),
                ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsRegistroInspeccion(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Save_Inspection_Bus_OK, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Save_Inspection_Bus, Toast.LENGTH_LONG).show();
        }
    }

    private boolean ValidarFormUsuario(){
        boolean estado=true;


        //Validar Pax Oficina
        if(etCantPasajOficinaInspectionBus.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese la cantidad de pasajeros de oficina de inspección de bus.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.soloNumero,etCantPasajOficinaInspectionBus.getText().toString())){
                Toast.makeText(getBaseContext(), "Ingrese una cantidad de pasajeros de oficina inspección de bus válida.", Toast.LENGTH_LONG).show();
                estado=false;
            }else{
                if(Integer.parseInt(etCantPasajOficinaInspectionBus.getText().toString())>99){
                    Toast.makeText(getBaseContext(), "Ingrese una cantidad de pasajeros de oficina de inspección de bus menor a 100.", Toast.LENGTH_LONG).show();
                    estado=false;
                }
            }
        }

        //Validar Pax Puntos Ventas
        if(etCantPasajPuntosVentasInspectionBus.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese la cantidad de pasajeros de puntos de ventas de la inspección del bus.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.monto,etCantPasajPuntosVentasInspectionBus.getText().toString())){
                Toast.makeText(getBaseContext(), "Ingrese una cantidad de pasajeros de puntos de ventas de inspección de bus válida.", Toast.LENGTH_LONG).show();
                estado=false;
            }else{
                if(Integer.parseInt(etCantPasajPuntosVentasInspectionBus.getText().toString())>99){
                    Toast.makeText(getBaseContext(), "Ingrese una cantidad de pasajeros de puntos de ventas de inspección de bus menor a 100.", Toast.LENGTH_LONG).show();
                    estado=false;
                }
            }
        }

        //Validar Pax Ruta
        if(etCantPasajRutaInspectionBus.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese la cantidad de pasajeros de ruta de inspección de bus.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.soloNumero,etCantPasajRutaInspectionBus.getText().toString())){
                Toast.makeText(getBaseContext(), "Ingrese una cantidad de pasajeros de ruta de inspección de bus válida.", Toast.LENGTH_LONG).show();
                estado=false;
            }else{
                if(Integer.parseInt(etCantPasajRutaInspectionBus.getText().toString())>99){
                    Toast.makeText(getBaseContext(), "Ingrese una cantidad de pasajeros de ruta de inspección de bus menor a 100.", Toast.LENGTH_LONG).show();
                    estado=false;
                }
            }
        }

        //Validar Total Ventas Ruta
        if(etTotalVentasRutaInspectionBus.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese el total de ventas de ruta de la inspección del bus.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.monto,etTotalVentasRutaInspectionBus.getText().toString())){
                Toast.makeText(getBaseContext(), "Ingrese una total de ventas de ruta de inspección de bus válida.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        return estado;
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo.equals(getString(R.string.key_method_ws_insert_inspection))){
            wsRegistroInspeccion(Respuesta);
        }else if(KeyMetodo.equals(getString(R.string.key_method_ws_list_zona_inspection_bus))){
            wsListarZonas(Respuesta);
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