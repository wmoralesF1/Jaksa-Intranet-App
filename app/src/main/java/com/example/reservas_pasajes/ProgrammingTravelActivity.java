package com.example.reservas_pasajes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.AdaptadorListaViajes;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.TipoServicioModel;
import com.example.reservas_pasajes.models.TramoModel;
import com.example.reservas_pasajes.models.UnidadModel;
import com.example.reservas_pasajes.models.UsuarioModel;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgrammingTravelActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFechaProgramminTravel;
    EditText etHoraProgramminTravel;
    Spinner sOrigenProgramminTravel;
    Spinner sTramoProgramminTravel;
    Spinner sServicioProgramminTravel;
    Spinner sBusProgramminTravel;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    Button btnGrabarNuevoViaje;
    ArrayList<TipoServicioModel> listTipoServicios=new ArrayList<>();
    ArrayList<UnidadModel> listUnidades=new ArrayList<>();
    ArrayList<TramoModel> listTramos=new ArrayList<>();
    TipoServicioModel oTipoServicioModel=new TipoServicioModel();
    UnidadModel oUnidadModel=new UnidadModel();
    TramoModel oTramoModel=new TramoModel();
    ViajeModel oViajeModel=new ViajeModel();
    int Id_Viaje=0;
    String nomTerminal="";
    String nroUnidad="";
    boolean edicion=false;
    String TAG="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_travel);
        TAG="Edicion de Viaje";
        etFechaProgramminTravel= findViewById(R.id.etFechaProgramminTravel);
        etHoraProgramminTravel= findViewById(R.id.etHoraProgramminTravel);
        sOrigenProgramminTravel= findViewById(R.id.sOrigenProgramminTravel);
        sTramoProgramminTravel= findViewById(R.id.sTramoProgramminTravel);
        sServicioProgramminTravel= findViewById(R.id.sServicioProgramminTravel);
        sBusProgramminTravel= findViewById(R.id.sBusProgramminTravel);
        btnGrabarNuevoViaje= findViewById(R.id.btnGrabarNuevoViaje);
        //Calendario
        Calendar calendarActual= Calendar.getInstance();
        Calendar calendarMaximo= Calendar.getInstance();
        datePicker = new DatePickerDialog(ProgrammingTravelActivity.this);


        final String año=String.valueOf(calendarActual.get(Calendar.YEAR));
        final String mes=new DecimalFormat("00").format(calendarActual.get(Calendar.MONTH)+1);
        final String dia=new DecimalFormat("00").format(calendarActual.get(Calendar.DAY_OF_MONTH));

        final int year=calendarActual.get(Calendar.YEAR);
        final int month=calendarActual.get(Calendar.MONTH);
        final int day=calendarActual.get(Calendar.DAY_OF_MONTH);

        //Hora
        Calendar calendarHoraActual= Calendar.getInstance();
        final int hora=calendarHoraActual.get(Calendar.HOUR_OF_DAY);
        final int minuto=calendarHoraActual.get(Calendar.MINUTE);
        //timePicker = new TimePickerDialog(ProgrammingTravelActivity.this);

        etFechaProgramminTravel.setText(dia + "/" + mes+ "/" + año);
        etHoraProgramminTravel.setText(Functions.leftzeros( hora,2)  + ":" + Functions.leftzeros( minuto,2) ) ;

        etFechaProgramminTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(ProgrammingTravelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String año=String.valueOf(year);
                        String mes=new DecimalFormat("00").format(month);
                        String dia=new DecimalFormat("00").format(day);
                        String date=dia+"/"+ mes + "/"+ año;
                        etFechaProgramminTravel.setText(date);
                    }
                }, year, month, day);

                calendarMaximo.setTime(calendarActual.getTime());
                datePicker.getDatePicker().setMinDate(calendarActual.getTimeInMillis());
                calendarMaximo.add(Calendar.DATE,1);
                datePicker.getDatePicker().setMaxDate(calendarMaximo.getTimeInMillis());
                datePicker.show();
            }
        });

        etHoraProgramminTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = new TimePickerDialog(ProgrammingTravelActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etHoraProgramminTravel.setText(Functions.leftzeros( hourOfDay,2) + ":" + Functions.leftzeros( minute,2) );
                    }
                }, hora, minuto, true);
                timePicker.show();
            }
        });

        ArrayAdapter<TerminalModel> terminalesAdapter=new ArrayAdapter<TerminalModel>(ProgrammingTravelActivity.this,
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getListaTerminales());
        sOrigenProgramminTravel.setAdapter(terminalesAdapter);

        sOrigenProgramminTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);

                BuscarTramos(entidad.getNomTerminal());
                /*sDestino.setAdapter(null);
                viaje.setNomOrigen(entidad.getNomTerminal());
                BuscarDestinosTerminal(entidad.getNomTerminal());*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ListarTipoServicios();

        ListarUnidades();

        btnGrabarNuevoViaje.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        Id_Viaje =Integer.parseInt(bundle.getString("idViaje"));
        nomTerminal=bundle.getString("nomTerminal").toString();
        nroUnidad=bundle.getString("nroUnidad").toString();
        if(Id_Viaje>0){
            edicion=true;
            BuscarViaje(Id_Viaje);
            etFechaProgramminTravel.setEnabled(false);
            sBusProgramminTravel.setEnabled(true);
        }else{
            sBusProgramminTravel.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnGrabarNuevoViaje.getId()){
            if(edicion==false){
                ViajeModel entidad=new ViajeModel();
                TerminalModel entTerminalModel=(TerminalModel) sOrigenProgramminTravel.getSelectedItem();
                TipoServicioModel entTipoServicio=(TipoServicioModel) sServicioProgramminTravel.getSelectedItem();
                TramoModel entTramo=(TramoModel) sTramoProgramminTravel.getSelectedItem();
                UsuarioModel entUsuarioModel=SessionManager.getUsuario();
                entidad.setFechaViaje(etFechaProgramminTravel.getText().toString());
                entidad.setNomTerminal(entTerminalModel.getNomTerminal());
                entidad.setNomUsuario(entUsuarioModel.getNomUsuario());
                entidad.setClaveUsuario(entUsuarioModel.getPassUsuario());
                entidad.setHoraViaje(etHoraProgramminTravel.getText().toString());
                entidad.setIdTramoViaje(entTramo.getIdTramo());
                entidad.setIdServicio(entTipoServicio.getIdServicio());

                GrabarViaje(entidad);
            }else{
                ViajeModel entidad=new ViajeModel();
                TerminalModel entTerminalModel=(TerminalModel) sOrigenProgramminTravel.getSelectedItem();
                TipoServicioModel entTipoServicio=(TipoServicioModel) sServicioProgramminTravel.getSelectedItem();
                TramoModel entTramo=(TramoModel) sTramoProgramminTravel.getSelectedItem();
                UsuarioModel entUsuarioModel=SessionManager.getUsuario();
                entidad.setIdViaje(Id_Viaje);
                entidad.setFechaViaje(etFechaProgramminTravel.getText().toString());
                entidad.setNomTerminal(entTerminalModel.getNomTerminal());
                entidad.setNomUsuario(entUsuarioModel.getNomUsuario());
                entidad.setClaveUsuario(entUsuarioModel.getPassUsuario());
                entidad.setHoraViaje(etHoraProgramminTravel.getText().toString());
                entidad.setIdTramoViaje(entTramo.getIdTramo());
                entidad.setIdServicio(entTipoServicio.getIdServicio());
                ModificarViaje(entidad);
            }

        }/*else if(v.getId()==btnNuevoViajeOpenTravel.getId()){
            Intent i;
            i = new Intent(this, ProgrammingTravelActivity.class);
            startActivity(i);
        }*/

    }

    private void ListarUnidades(){
        String ParamNombres, ParamValores;
        ParamNombres="sentido";
        ParamValores="1";
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("BusesActivos",getString(R.string.key_method_ws_list_buses),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void ListarTipoServicios(){
        String ParamNombres, ParamValores;
        ParamNombres="";
        ParamValores="";
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("TServivios",getString(R.string.key_method_ws_list_services),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void BuscarTramos(String terminal){

        String ParamNombres, ParamValores;
        ParamNombres="Terminal";
        ParamValores=terminal;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("Tramos",getString(R.string.key_method_ws_search_list_tramos),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void GrabarViaje(ViajeModel entidad){

        String ParamNombres, ParamValores;
        ParamNombres="FechaApertura#Terminal#usuario#clave#FechaHoraSalida#Tram_id#TipoServ_id";
        ParamValores= Functions.StringFormatWsDate(entidad.getFechaViaje())+"#"+
                entidad.getNomTerminal()+"#"+entidad.getNomUsuario()+"#"+entidad.getClaveUsuario()+"#"+
                Functions.StringFormatWsDate(entidad.getFechaViaje()) + " " + entidad.getHoraViaje()+"#"+
                entidad.getIdTramoViaje()+"#"+entidad.getIdServicio();

        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setHojaruta",getString(R.string.key_method_ws_insert_viaje),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void ModificarViaje(ViajeModel entidad){

        String ParamNombres, ParamValores;
        ParamNombres="usuario#clave#Viaje#Terminal#Tram_id#TipoServ_id#FechaHoraSalida#unidad";
        ParamValores= entidad.getNomUsuario()+"#"+entidad.getClaveUsuario()+"#"+
                String.valueOf(entidad.getIdViaje()) +"#"+ entidad.getNomTerminal()+"#"+
                entidad.getIdTramoViaje()+"#"+entidad.getIdServicio()+"#"+
                Functions.StringFormatWsDate(entidad.getFechaViaje()) + " " + entidad.getHoraViaje() +"#"+
                oUnidadModel.getNroUnidad();


        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setModificarHR",getString(R.string.key_method_ws_update_viaje),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void BuscarViaje(int Viaje_Id){

        String ParamNombres, ParamValores;
        ParamNombres="NroViaje";
        ParamValores= String.valueOf(Viaje_Id);
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("VerHojaRuta",getString(R.string.key_method_ws_search_viaje_id),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsListarUnidades(String respuesta){

        if(listUnidades!=null && listUnidades.size()>0){
            listUnidades.clear();
        }
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] IdUnidad = Cadena[0].split(";");
                String[] NroUnidad = Cadena[1].split(";");
                if(IdUnidad.length>0){
                    for (int i = 0; i <= IdUnidad.length-1; i++) {
                        if(i<=IdUnidad.length-1 && i<=NroUnidad.length-1){
                            if((!IdUnidad[i].isEmpty() && !(IdUnidad[i] ==null)) && (!NroUnidad[i].isEmpty() && !(NroUnidad[i] ==null)))
                            {
                                UnidadModel entidad=new UnidadModel();
                                entidad.setIdUnidad(Integer.parseInt(IdUnidad[i]));
                                entidad.setNroUnidad(NroUnidad[i].toUpperCase());
                                listUnidades.add(entidad);
                            }
                        }
                    }

                    sBusProgramminTravel.setAdapter(null);
                    ArrayAdapter<UnidadModel> UnidadesAdapter=new ArrayAdapter<UnidadModel>(ProgrammingTravelActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, listUnidades);
                    sBusProgramminTravel.setAdapter(UnidadesAdapter);

                    sBusProgramminTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            oUnidadModel= (UnidadModel) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Unidades, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Unidades, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Unidades, Toast.LENGTH_LONG).show();
        }
    }

    private void wsListarTipoServicios(String respuesta){

        if(listTipoServicios!=null && listTipoServicios.size()>0){
            listTipoServicios.clear();
        }
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] TipoServicio_id = Cadena[0].split(";");
                String[] Servicio = Cadena[1].split(";");
                if(TipoServicio_id.length>0){
                    for (int i = 0; i <= TipoServicio_id.length-1; i++) {
                        if(i<=TipoServicio_id.length-1 && i<=Servicio.length-1){
                            if((!TipoServicio_id[i].isEmpty() && !(TipoServicio_id[i] ==null)) && (!Servicio[i].isEmpty() && !(Servicio[i] ==null)))
                            {
                                TipoServicioModel oTipoServicioModel=new TipoServicioModel();
                                oTipoServicioModel.setIdServicio(TipoServicio_id[i]);
                                oTipoServicioModel.setNomServicio(Servicio[i].toUpperCase());
                                listTipoServicios.add(oTipoServicioModel);
                            }
                        }
                    }

                    sServicioProgramminTravel.setAdapter(null);
                    ArrayAdapter<TipoServicioModel> TipoServiciosAdapter=new ArrayAdapter<TipoServicioModel>(ProgrammingTravelActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, listTipoServicios);
                    sServicioProgramminTravel.setAdapter(TipoServiciosAdapter);

                    sServicioProgramminTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            oTipoServicioModel= (TipoServicioModel) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tipo_Servicios, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tipo_Servicios, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tipo_Servicios, Toast.LENGTH_LONG).show();
        }
    }

    private void wsBuscarTramos(String respuesta){

        if(listTramos!=null && listTramos.size()>0){
            listTramos.clear();
        }
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Tramo_id = Cadena[0].split(";");
                String[] Tramo = Cadena[1].split(";");
                if(Tramo_id.length>0){
                    for (int i = 0; i <= Tramo_id.length-1; i++) {
                        if(i<=Tramo_id.length-1 && i<=Tramo.length-1){
                            if((!Tramo_id[i].isEmpty() && !(Tramo_id[i] ==null)) && (!Tramo[i].isEmpty() && !(Tramo[i] ==null)))
                            {
                                TramoModel entidad=new TramoModel();
                                entidad.setIdTramo(Tramo_id[i]);
                                entidad.setNomTramo(Tramo[i].toUpperCase());
                                listTramos.add(entidad);
                            }
                        }
                    }

                    sTramoProgramminTravel.setAdapter(null);
                    ArrayAdapter<TramoModel> TramoAdapter=new ArrayAdapter<TramoModel>(ProgrammingTravelActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, listTramos);
                    sTramoProgramminTravel.setAdapter(TramoAdapter);

                    sTramoProgramminTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            oTramoModel= (TramoModel) parent.getItemAtPosition(position);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    if(edicion){
                        for (int i = 0; i <= sTramoProgramminTravel.getCount()-1; i++){
                            if(((TramoModel)sTramoProgramminTravel.getItemAtPosition(i)).getNomTramo().equals(oViajeModel.getTramoViaje())){
                                sTramoProgramminTravel.setSelection(i);
                            }
                        }
                    }

                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tramos, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tramos, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tramos, Toast.LENGTH_LONG).show();
        }
    }

    private void wsGrabarViaje(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Save_Travel_OK, Toast.LENGTH_LONG).show();
                Intent i;
                i = new Intent(this, OpenTravelActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Save_Travel, Toast.LENGTH_LONG).show();
        }
    }

    private void wsModificarViaje(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Modific_Travel_OK, Toast.LENGTH_LONG).show();
                Intent i;
                i = new Intent(this, OpenTravelActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Modific_Travel, Toast.LENGTH_LONG).show();
        }
    }

    private void wsBuscarViaje(String respuesta){
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Viaje = Cadena[0].split(";");
                String[] FechaViaje = Cadena[1].split(";");
                String[] Tramo = Cadena[2].split(";");
                String[] Servicio = Cadena[3].split(";");
                String[] HoraViaje = Cadena[4].split(";");
                if(Viaje.length>0){
                    for (int i = 0; i <= Viaje.length-1; i++) {
                        if(i<=Viaje.length-1 && i<=FechaViaje.length-1){
                            if((!Viaje[i].isEmpty() && !(Viaje[i] ==null)) &&
                                    (!FechaViaje[i].isEmpty() && !(FechaViaje[i] ==null)) &&
                                    (!Tramo[i].isEmpty() && !(Tramo[i] ==null)) &&
                                    (!Servicio[i].isEmpty() && !(Servicio[i] ==null)) &&
                                    (!HoraViaje[i].isEmpty() && !(HoraViaje[i] ==null)))
                            {
                                oViajeModel.setIdViaje(Integer.parseInt(Viaje[i]));
                                oViajeModel.setFechaViaje(FechaViaje[i].replace(" 12:00:00 a.m.",""));
                                oViajeModel.setTramoViaje(Tramo[i]);
                                oViajeModel.setNomTerminal(nomTerminal);
                                oViajeModel.setNomServicio(Servicio[i]);
                                oViajeModel.setNumBus(nroUnidad);
                                oViajeModel.setHoraViaje(HoraViaje[i]);
                            }
                        }
                    }

                    if(oViajeModel!=null){
                        etFechaProgramminTravel.setText(oViajeModel.getFechaViaje());
                        etHoraProgramminTravel.setText(oViajeModel.getHoraViaje());

                        for (int i = 0; i <= sOrigenProgramminTravel.getCount()-1; i++){
                            if(((TerminalModel)sOrigenProgramminTravel.getItemAtPosition(i)).getNomTerminal().equals(nomTerminal)){
                                sOrigenProgramminTravel.setSelection(i);
                                BuscarTramos(nomTerminal);
                            }
                        }

                        for (int i = 0; i <= sServicioProgramminTravel.getCount()-1; i++){
                            if(((TipoServicioModel)sServicioProgramminTravel.getItemAtPosition(i)).getNomServicio().equals(oViajeModel.getNomServicio())){
                                sServicioProgramminTravel.setSelection(i);
                            }
                        }

                        for (int i = 0; i <= sBusProgramminTravel.getCount()-1; i++){
                            if(((UnidadModel)sBusProgramminTravel.getItemAtPosition(i)).getNroUnidad().equals(oViajeModel.getNumBus())){
                                sBusProgramminTravel.setSelection(i);
                            }
                        }




                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tramos, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tramos, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Tramos, Toast.LENGTH_LONG).show();
        }
    }


    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo==getString(R.string.key_method_ws_list_buses)){
            wsListarUnidades(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_list_services)){
            wsListarTipoServicios(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_search_list_tramos)){
            wsBuscarTramos(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_insert_viaje)){
            wsGrabarViaje(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_update_viaje)){
            wsModificarViaje(Respuesta);
        }else if(KeyMetodo==getString(R.string.key_method_ws_search_viaje_id)){
            wsBuscarViaje(Respuesta);
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
            /*if(KeyMetodo==getString(R.string.key_method_ws_search_destino)){
                pDialog.setMessage(getString(R.string.msg_Search_Destinos));
            }else if(KeyMetodo==getString(R.string.key_method_ws_search_salidas)){
                pDialog.setMessage(getString(R.string.msg_Search_Routes));
            }
            pDialog.show();*/
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