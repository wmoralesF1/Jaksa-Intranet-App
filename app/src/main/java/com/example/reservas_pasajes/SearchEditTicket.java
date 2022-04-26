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

import com.example.reservas_pasajes.Adaptadores.AdaptadorListaBoletosViaje;
import com.example.reservas_pasajes.Adaptadores.AdaptadorListaProgramacionViaje;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.TramoModel;
import com.example.reservas_pasajes.models.UsuarioModel;
import com.example.reservas_pasajes.models.VentaModel;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class SearchEditTicket extends AppCompatActivity  implements View.OnClickListener {
    EditText etFechaViajeSearchEditTicket;
    Spinner spTerminalSearchEditTicket;
    Spinner spUsuarioSearchEditTicket;
    Button btnBuscarTicketSearchEditTicket;
    Button btnAnulaTicketSearchEditTicket;
    Button btnEditarTicketSearchEditTicket;
    ListView lvListaBoletosViajeSearchEditTicket;
    AdaptadorListaBoletosViaje adaptador;
    ArrayList<UsuarioModel> listUsuario=new ArrayList<>();
    ArrayList<VentaModel> listaBoletosViaje=new ArrayList<>();
    VentaModel oVentaModel=new VentaModel();
    UsuarioModel oUsuarioModel=new UsuarioModel();

    String FechaViaje;
    String Terminal;
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_edit_ticket);

        TAG="Busqueda de Boletos de Viaje";
        etFechaViajeSearchEditTicket=findViewById(R.id.etFechaViajeSearchEditTicket);
        spTerminalSearchEditTicket=findViewById(R.id.spTerminalSearchEditTicket);
        spUsuarioSearchEditTicket=findViewById(R.id.spUsuarioSearchEditTicket);
        lvListaBoletosViajeSearchEditTicket=findViewById(R.id.lvListaBoletosViajeSearchEditTicket);
        btnBuscarTicketSearchEditTicket=findViewById(R.id.btnBuscarTicketSearchEditTicket);
        btnAnulaTicketSearchEditTicket=findViewById(R.id.btnAnulaTicketSearchEditTicket);
        btnEditarTicketSearchEditTicket=findViewById(R.id.btnEditarTicketSearchEditTicket);

        etFechaViajeSearchEditTicket.setText(Functions.FechaHoy());
        final int year=Functions.NumeroAñoFechaHoy();
        final int month=Functions.NumeroMesFechaHoy();
        final int day=Functions.NumeroDiaFechaHoy();
        etFechaViajeSearchEditTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(SearchEditTicket.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                etFechaViajeSearchEditTicket.setText(Functions.Fecha(day,month+1,year));
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });
        FechaViaje=etFechaViajeSearchEditTicket.getText().toString();

        ArrayAdapter<TerminalModel> terminalesAdapter=new ArrayAdapter<TerminalModel>(SearchEditTicket.this,
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getListaTerminales());

        spTerminalSearchEditTicket.setAdapter(terminalesAdapter);
        spTerminalSearchEditTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                Terminal=entidad.getNomTerminal();
                BuscarUsuariosTerminal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBuscarTicketSearchEditTicket.setOnClickListener(this);
        btnAnulaTicketSearchEditTicket.setOnClickListener(this);
        btnEditarTicketSearchEditTicket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnBuscarTicketSearchEditTicket.getId()){
            FechaViaje=etFechaViajeSearchEditTicket.getText().toString();
            UsuarioModel oUsuarioModel=(UsuarioModel) spUsuarioSearchEditTicket.getSelectedItem();
            BuscarBoletosViaje(oUsuarioModel.getNomUsuario());
        }else if(v.getId()==btnAnulaTicketSearchEditTicket.getId()){
            Intent i;
            i = new Intent(this, EditTicket.class);
            i.putExtra("idViaje", oVentaModel.getIdViaje());
            i.putExtra("idBoleto", oVentaModel.getIdBoleto());
            i.putExtra("precio", oVentaModel.getPrecio());
            i.putExtra("asiento", oVentaModel.getNumAsiento());
            i.putExtra("operacion", "0");
            startActivity(i);
        }else if(v.getId()==btnEditarTicketSearchEditTicket.getId()){
            Intent i;
            i = new Intent(this, EditTicket.class);
            i.putExtra("idViaje", oVentaModel.getIdViaje());
            i.putExtra("idBoleto", oVentaModel.getIdBoleto());
            i.putExtra("precio", oVentaModel.getPrecio());
            i.putExtra("asiento", oVentaModel.getNumAsiento());
            i.putExtra("operacion", "1");
            startActivity(i);
        }

    }

    private void BuscarUsuariosTerminal(){
        String ParamNombres, ParamValores;
        ParamNombres="terminal#fecha";
        ParamValores=Terminal+"#"+Functions.StringFormatWsDate(FechaViaje);
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("listaUsuarios",getString(R.string.key_method_ws_search_usuarios_terminal),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsBuscarUsuariosTerminal(String respuesta){

        if(listUsuario!=null && listUsuario.size()>0){
            listUsuario.clear();
        }
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] NomUsuario = Cadena[0].split(";");
                if(NomUsuario.length>0){
                    for (int i = 0; i <= NomUsuario.length-1; i++) {
                        if(i<=NomUsuario.length-1 && i<=NomUsuario.length-1){
                            if((!NomUsuario[i].isEmpty() && !(NomUsuario[i] ==null)))
                            {
                                UsuarioModel entidad=new UsuarioModel();
                                entidad.setNomUsuario(NomUsuario[i]);
                                listUsuario.add(entidad);
                            }
                        }
                    }

                    spUsuarioSearchEditTicket.setAdapter(null);
                    ArrayAdapter<UsuarioModel> UsuarioAdapter=new ArrayAdapter<UsuarioModel>(SearchEditTicket.this,
                            android.R.layout.simple_spinner_dropdown_item, listUsuario);
                    spUsuarioSearchEditTicket.setAdapter(UsuarioAdapter);

                    spUsuarioSearchEditTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            oUsuarioModel= (UsuarioModel) parent.getItemAtPosition(position);
                            BuscarBoletosViaje(oUsuarioModel.getNomUsuario());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Usuarios, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Usuarios, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Usuarios, Toast.LENGTH_LONG).show();
        }
    }

    private void BuscarBoletosViaje(String nomUsuario){
        adaptador=new AdaptadorListaBoletosViaje(this, new ArrayList<VentaModel>());
        lvListaBoletosViajeSearchEditTicket.setAdapter(adaptador);
        String ParamNombres, ParamValores;
        ParamNombres="terminal#fecha#usuario";
        ParamValores= Terminal+"#"+Functions.StringFormatWsDate(FechaViaje)+"#"+nomUsuario;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("boletosUsuario",getString(R.string.key_method_ws_search_tickets_travel),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsBuscarBoletosViaje(String respuesta){
        listaBoletosViaje.clear();
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Id_Boleto = Cadena[0].split(";");
                String[] Id_Viaje = Cadena[1].split(";");
                String[] NumBus = Cadena[2].split(";");
                String[] NumBoleto = Cadena[3].split(";");
                String[] Precio = Cadena[4].split(";");
                String[] NumAsiento = Cadena[5].split(";");
                String[] Estado = Cadena[6].split(";");
                String[] Dni = Cadena[7].split(";");
                if(Id_Boleto.length>0){
                    for (int i = 0; i <= Id_Boleto.length-1; i++) {
                        if(i<=Id_Boleto.length-1
                                && i<=Id_Viaje.length-1
                                && i<=NumBus.length-1
                                && i<=NumBoleto.length-1
                                && i<=NumAsiento.length-1
                                && i<=Estado.length-1
                                && i<=Dni.length-1){
                            if((!Id_Boleto[i].isEmpty() && !(Id_Boleto[i] ==null))
                                    && (!Id_Viaje[i].isEmpty() && !(Id_Viaje[i] ==null))
                                    && (!NumBus[i].isEmpty() && !(NumBus[i] ==null))
                                    && (!NumBoleto[i].isEmpty() && !(NumBoleto[i] ==null))
                                    && (!Precio[i].isEmpty() && !(Precio[i] ==null))
                                    && (!NumAsiento[i].isEmpty() && !(NumAsiento[i] ==null))
                                    && (!Estado[i].isEmpty() && !(Estado[i] ==null))
                                    && (!Dni[i].isEmpty() && !(Dni[i] ==null)))
                            {
                                VentaModel entidad=new VentaModel();
                                entidad.setIdBoleto(Integer.parseInt(Id_Boleto[i]));
                                entidad.setIdViaje(Integer.parseInt(Id_Viaje[i]));
                                entidad.setNumBus(NumBus[i]);
                                entidad.setNumBoleto(NumBoleto[i]);
                                entidad.setPrecio(Double.parseDouble(Precio[i]));
                                entidad.setNumAsiento(NumAsiento[i]);
                                entidad.setEstado(Estado[i]);
                                entidad.setDocumentoIdentidad(Dni[i]);
                                listaBoletosViaje.add(entidad);
                            }
                        }
                    }
                    adaptador=new AdaptadorListaBoletosViaje(this, listaBoletosViaje);
                    lvListaBoletosViajeSearchEditTicket.setAdapter(adaptador);
                    lvListaBoletosViajeSearchEditTicket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                            oVentaModel = (VentaModel) adapterView.getItemAtPosition(index);
                            for (int i = 0; i <= lvListaBoletosViajeSearchEditTicket.getChildCount()-1; i++) {
                                if(index == i ){
                                    lvListaBoletosViajeSearchEditTicket.getChildAt(index).setBackgroundColor(Color.YELLOW);
                                    btnAnulaTicketSearchEditTicket.setEnabled(true);
                                    btnEditarTicketSearchEditTicket.setEnabled(true);
                                }else{
                                    lvListaBoletosViajeSearchEditTicket.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                }
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
        if(KeyMetodo==getString(R.string.key_method_ws_search_usuarios_terminal)){
            wsBuscarUsuariosTerminal(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_search_tickets_travel)){
            wsBuscarBoletosViaje(Respuesta);
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