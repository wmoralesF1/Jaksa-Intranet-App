package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.helper.adaptadorPasajero;
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

public class PassengerActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG;
    String TipoDocumento;
    TextView tvDescRutaPassenger;
    TextView tvFechaHoraReservaPassenger;
    TextView tvServicioPassenger;
    TextView tvPrecioPassenger;
    Spinner spTipoDocumentoPassenger;
    EditText etNroDocumentoPassenger;
    EditText etNombresPassenger;
    EditText etApellidoPaternoPassenger;
    EditText etApellidoMaternoPassenger;
    //EditText etPrecioPromocionalPassenger;
    Button btnNuevoPassenger;
    Button btnBuscarPassenger;
    Button btnGuardarPasajeroPassenger;
    Button btnReservarPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG="PASAJEROS";
        setContentView(R.layout.activity_passenger);

        spTipoDocumentoPassenger = (Spinner)findViewById(R.id.spTipoDocumentoPassenger);
        etNroDocumentoPassenger = (EditText)findViewById(R.id.etNroDocumentoPassenger);
        etNombresPassenger = (EditText) findViewById(R.id.etNombresPassenger);
        etApellidoPaternoPassenger = (EditText) findViewById(R.id.etApellidoPaternoPassenger);
        etApellidoMaternoPassenger = (EditText) findViewById(R.id.etApellidoMaternoPassenger);
        //etPrecioPromocionalPassenger = (EditText) findViewById(R.id.etPrecioPromocionalPassenger);
        btnNuevoPassenger=(Button)findViewById(R.id.btnNuevoPassenger);
        btnBuscarPassenger=(Button)findViewById(R.id.btnBuscarPassenger);
        btnGuardarPasajeroPassenger=(Button)findViewById(R.id.btnGuardarPasajeroPassenger);
        btnReservarPassenger=(Button)findViewById(R.id.btnReservarPassenger);


        DetalleReserva();
        ListarTipoDocumento();


        //LLENAR DATOS
        ArrayAdapter<TipoDocumentoModel> listaTiposDocumentosAdapter=new ArrayAdapter<TipoDocumentoModel>(PassengerActivity.this,
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getListaTiposDocumentos());
        spTipoDocumentoPassenger.setAdapter(listaTiposDocumentosAdapter);
        //etPrecioPromocionalPassenger.setText(String.valueOf(SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio()));



        //Agregar Eventos a Controles
        spTipoDocumentoPassenger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoDocumentoModel entidad= (TipoDocumentoModel) parent.getItemAtPosition(position);
                TipoDocumento=entidad.getNomTipoDocumento();
                if(TipoDocumento.equals("DNI")){
                    if(etNroDocumentoPassenger.getText().length()==8){
                        BuscarPasajero(TipoDocumento,etNroDocumentoPassenger.getText().toString());
                    }
                }else{
                    if(etNroDocumentoPassenger.getText().length()>6){
                        BuscarPasajero(TipoDocumento,etNroDocumentoPassenger.getText().toString());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnNuevoPassenger.setOnClickListener(this);
        btnBuscarPassenger.setOnClickListener(this);
        btnGuardarPasajeroPassenger.setOnClickListener(this);
        btnReservarPassenger.setOnClickListener(this);

        etNroDocumentoPassenger.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TipoDocumento.equals("DNI")){
                    if(etNroDocumentoPassenger.getText().length()==8){
                        BuscarPasajero(TipoDocumento,etNroDocumentoPassenger.getText().toString());
                    }
                }else{
                    if(etNroDocumentoPassenger.getText().length()>6){
                        BuscarPasajero(TipoDocumento,etNroDocumentoPassenger.getText().toString());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void DetalleReserva(){
        tvDescRutaPassenger=findViewById(R.id.tvDescRutaPassenger);
        tvFechaHoraReservaPassenger=findViewById(R.id.tvFechaHoraReservaPassenger);
        tvServicioPassenger=findViewById(R.id.tvServicioPassenger);
        tvPrecioPassenger=findViewById(R.id.tvPrecioPassenger);

        tvDescRutaPassenger.setText(SessionManager.getTurnoViaje().getRutaViaje().getRutaDescripcion());
        tvFechaHoraReservaPassenger.setText(SessionManager.getTurnoViaje().getFechaReserva() + " - " + SessionManager.getTurnoViaje().getHoraReserva());
        tvServicioPassenger.setText(SessionManager.getTurnoViaje().getNomServicio());
        tvPrecioPassenger.setText("S/ " + String.valueOf(SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio()));
    }

    private void ListarTipoDocumento(){
        ArrayList<TipoDocumentoModel> listaTipoDocumentos=new ArrayList<>();
        listaTipoDocumentos.add(new TipoDocumentoModel(1,"DNI"));
        listaTipoDocumentos.add(new TipoDocumentoModel(4,"CARNET EXTRANJERIA"));
        listaTipoDocumentos.add(new TipoDocumentoModel(7,"PASAPORTE"));
        SessionManager.setListaTiposDocumentos(null);
        SessionManager.setListaTiposDocumentos(listaTipoDocumentos);

    }

    private boolean ValidateGrabarPasajero(String Nombres,String ApellidoPaterno,String ApellidoMaterno){
        if(Nombres==null || Nombres.trim().isEmpty()){
            Toast.makeText(getBaseContext(), R.string.msg_Incomplet_Nombres_Passenger, Toast.LENGTH_LONG).show();
            return false;
        }
        if(ApellidoPaterno==null || ApellidoPaterno.trim().isEmpty()){
            Toast.makeText(getBaseContext(), R.string.msg_Incomplet_ApellidoPaterno_Passenger, Toast.LENGTH_LONG).show();
            return false;
        }
        if(TipoDocumento.toUpperCase().equals("DNI") && (ApellidoMaterno==null || ApellidoMaterno.trim().isEmpty())){
            Toast.makeText(getBaseContext(), R.string.msg_Incomplet_ApellidoMaterno_Passenger, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean ValidateBuscarPasajero(String NumeroDocumento){
        if(NumeroDocumento==null || NumeroDocumento.trim().isEmpty()){
            Toast.makeText(getBaseContext(), R.string.msg_Incomplet_Numero_Documento_Passenger, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==btnNuevoPassenger.getId()){
            nuevoPasajero();
        }else if(v.getId()==btnBuscarPassenger.getId()){
            String NumeroDocumento=etNroDocumentoPassenger.getText().toString();
            if(ValidateBuscarPasajero(NumeroDocumento)==true){
                BuscarPasajero(TipoDocumento,NumeroDocumento);
            }

        }else if(v.getId()==btnGuardarPasajeroPassenger.getId()){
            String Nombres=etNombresPassenger.getText().toString();
            String ApellidoPaterno=etApellidoPaternoPassenger.getText().toString();
            String ApellidoMaterno=etApellidoMaternoPassenger.getText().toString();

            if(ValidateGrabarPasajero(Nombres,ApellidoPaterno,ApellidoMaterno)==true){
                String NombresApellidos=ApellidoPaterno+ "|"+ "|" + ApellidoMaterno + "|"+ "|" + Nombres;
                GuardarPasajero(etNroDocumentoPassenger.getText().toString(),NombresApellidos);
            }
        }else if(v.getId()==btnReservarPassenger.getId()){
            btnBuscarPassenger.setEnabled(false);
            reservarVenta(SessionManager.getUsuario().getNomUsuario(),
                    SessionManager.getUsuario().getPassUsuario(),
                    SessionManager.getTurnoViaje().getNomOrigen(),
                    String.valueOf(SessionManager.getTurnoViaje().getIdViaje()),
                    String.valueOf(SessionManager.getTurnoViaje().getRutaViaje().getRutaId()),
                    SessionManager.getTurnoViaje().getListaPasajeros().get(0).getNumDocumento(),
                    String.valueOf(SessionManager.getTurnoViaje().getListaPasajeros().get(0).getPrecioAsiento()),
                    SessionManager.getTurnoViaje().getListaPasajeros().get(0).getNumAsiento());
        }
    }



    private void BuscarPasajero(String TipoDocumento,String NumeroDocumento){
        String ParamNombres, ParamValores;
        ParamNombres="tipo#dni";
        ParamValores=TipoDocumento + "#"+NumeroDocumento;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("Pasajero",getString(R.string.key_method_ws_search_passenger),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void GuardarPasajero(String NumeroDocumento,String NombresApellidos){
        String ParamNombres, ParamValores;
        ParamNombres="Ndoc#Apellidosnombre";
        ParamValores=NumeroDocumento + "#"+NombresApellidos;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setPasajero",getString(R.string.key_method_ws_save_passenger),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void reservarVenta(String NomUsuario,String ClaveUsuario,String NomTerminal,
                               String IdViaje,String CodiRuta,String NumeroDocumento,
                               String Precio,String NumeroAsiento){
        String ParamNombres, ParamValores;
        ParamNombres="usuario#clave#nombrelocal#viaje#CodigoRuta#DNI#Precio#asiento";
        ParamValores=NomUsuario + "#" + ClaveUsuario + "#" + NomTerminal + "#" +
                IdViaje + "#" + CodiRuta + "#" + NumeroDocumento + "#" +
                Precio + "#" + NumeroAsiento;

        Log.i(TAG, "Error887 para: " + ParamValores);
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setGrabarReserva",getString(R.string.key_method_ws_save_reserva),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsBuscaDatosPasajero(String respuesta){
        if(!respuesta.isEmpty()) {

            if(respuesta.equals("Nuevo")){
                Toast.makeText(getBaseContext(), R.string.msg_New_Passenger, Toast.LENGTH_LONG).show();
                btnGuardarPasajeroPassenger.setEnabled(true);
                etNombresPassenger.setEnabled(true);
                etApellidoPaternoPassenger.setEnabled(true);
                etApellidoMaternoPassenger.setEnabled(true);
                etNombresPassenger.setText("");
                etApellidoPaternoPassenger.setText("");
                etApellidoMaternoPassenger.setText("");
                guardarDatosPasajero("","","");
                btnReservarPassenger.setEnabled(false);
            }else{
                if(respuesta.contains("|")){
                    etNombresPassenger.setEnabled(false);
                    etApellidoPaternoPassenger.setEnabled(false);
                    etApellidoMaternoPassenger.setEnabled(false);
                    respuesta=respuesta.replace("|",";");
                    String[] Cadena = respuesta.split(";");
                    if(Cadena.length>0){
                        etNombresPassenger.setText(Cadena[2]);
                        etApellidoPaternoPassenger.setText(Cadena[0]);
                        etApellidoMaternoPassenger.setText(Cadena[1]);
                        guardarDatosPasajero(Cadena[2],Cadena[0],Cadena[1]);
                        if(SessionManager.getTurnoViaje().getListaPasajeros()!=null &&
                                SessionManager.getTurnoViaje().getListaPasajeros().size()>0){
                            btnReservarPassenger.setEnabled(true);
                        }
                        if(TipoDocumento.toUpperCase().equals("DNI") && (Cadena[1]==null || Cadena[1].isEmpty())){
                            btnGuardarPasajeroPassenger.setEnabled(true);
                            etNombresPassenger.setEnabled(true);
                            etApellidoPaternoPassenger.setEnabled(true);
                            etApellidoMaternoPassenger.setEnabled(true);
                        }
                    }
                }else{
                    Toast.makeText(getBaseContext(), R.string.msg_Error_Doc_Passenger, Toast.LENGTH_LONG).show();
                    btnGuardarPasajeroPassenger.setEnabled(true);
                    etNombresPassenger.setEnabled(true);
                    etApellidoPaternoPassenger.setEnabled(true);
                    etApellidoMaternoPassenger.setEnabled(true);
                    etNombresPassenger.setText("");
                    etApellidoPaternoPassenger.setText("");
                    etApellidoMaternoPassenger.setText("");
                    guardarDatosPasajero("","","");
                    btnReservarPassenger.setEnabled(false);
                }

            }



        }
    }

    private void wsGrabarDatosPasajero(String respuesta){
        if(!respuesta.isEmpty()) {
            try {
                Integer IdPasajero=Integer.parseInt(respuesta);
                if(IdPasajero>0){
                    Toast.makeText(getBaseContext(), R.string.msg_New_Passenger_Success, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), R.string.msg_Error_Save_Passenger, Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                Toast.makeText(getBaseContext(), R.string.msg_Error_Save_Passenger, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void wsGrabarReservaVenta(String respuesta){
        btnBuscarPassenger.setEnabled(true);

        if(!respuesta.isEmpty()){
            if(respuesta.contains("error")){
                Toast.makeText(getBaseContext(), respuesta.toUpperCase(), Toast.LENGTH_LONG).show();
            }else{
                Intent i;
                i = new Intent(this, DetalleReservaActivity.class);
                startActivity(i);
            }
        }else{
            Toast.makeText(getBaseContext(), getText(R.string.msg_Error_Conexion), Toast.LENGTH_LONG).show();
        }
    }

    private void nuevoPasajero(){
        etNroDocumentoPassenger.setText("");
        etNombresPassenger.setText("");
        etApellidoPaternoPassenger.setText("");
        etApellidoMaternoPassenger.setText("");
        //etPrecioPromocionalPassenger.setText(String.valueOf(SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio()));
    }

    private void guardarDatosPasajero(String Nombres,String ApellidoPaterno,String ApellidoMaterno){
        ArrayList<PasajeroModel> listaPasajero= SessionManager.getTurnoViaje().getListaPasajeros();
        listaPasajero.get(0).setTipoDocumento(TipoDocumento);
        listaPasajero.get(0).setNumDocumento(etNroDocumentoPassenger.getText().toString());
        listaPasajero.get(0).setNombrePasajero(Nombres);
        listaPasajero.get(0).setApellidoPaternoPasajero(ApellidoPaterno);
        if(ApellidoMaterno==null || ApellidoMaterno.trim().equals("")){
            listaPasajero.get(0).setApellidoMaternoPasajero("");
        }else{
            listaPasajero.get(0).setApellidoMaternoPasajero(ApellidoMaterno);
        }
        listaPasajero.get(0).setPrecioPromocionalAsiento(listaPasajero.get(0).getPrecioAsiento());
        SessionManager.getTurnoViaje().setListaPasajeros(null);
        SessionManager.getTurnoViaje().setListaPasajeros(listaPasajero);
    }


    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo==getString(R.string.key_method_ws_search_passenger)){
            wsBuscaDatosPasajero(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_save_passenger)){
            wsGrabarDatosPasajero(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_save_reserva)){
            wsGrabarReservaVenta(Respuesta);
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
            if(result.isEmpty() && KeyMetodo==getString(R.string.key_method_ws_search_passenger)){
                result=";";
            }
            if (result.equals(""))
            {
                Toast.makeText(getBaseContext(), getText(R.string.msg_Error_Conexion), Toast.LENGTH_LONG).show();
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