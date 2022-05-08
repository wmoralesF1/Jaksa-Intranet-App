package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

import com.example.reservas_pasajes.Adaptadores.AdaptadorListaFiltroUsuarios;
import com.example.reservas_pasajes.helper.Enumeraciones;
import com.example.reservas_pasajes.helper.ExpReg;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.Lista;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.UsuarioModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUsuarioUser;
    EditText etClaveUser;
    EditText etClave2User;
    EditText etNumeroDocumentoUser;
    EditText etLocalUser;
    EditText etCentroOperacionesUser;
    Spinner spNivelUser;
    Spinner spEstadoUser;
    Button btnGrabarUser;
    Boolean edicion=false;
    UsuarioModel oUsuarioModel;
    String TAG="Registro de Usuario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        oUsuarioModel=new UsuarioModel();
        etUsuarioUser= findViewById(R.id.etUsuarioUser);
        etClaveUser= findViewById(R.id.etClaveUser);
        etClave2User= findViewById(R.id.etClave2User);
        etNumeroDocumentoUser= findViewById(R.id.etNumeroDocumentoUser);
        etLocalUser= findViewById(R.id.etLocalUser);
        etCentroOperacionesUser= findViewById(R.id.etCentroOperacionesUser);
        spNivelUser= findViewById(R.id.spNivelUser);
        spEstadoUser= findViewById(R.id.spEstadoUser);
        btnGrabarUser= findViewById(R.id.btnGrabarUser);
        btnGrabarUser.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        oUsuarioModel.setIdUsuario(bundle.getInt("usId"));
        oUsuarioModel.setNomUsuario(bundle.getString("usNom"));
        oUsuarioModel.setNomTerminal(bundle.getString("usLocal"));
        oUsuarioModel.setCentroOperaciones(bundle.getString("usCenOpe"));
        oUsuarioModel.setNivel(bundle.getString("usNivel"));
        oUsuarioModel.setEstado(bundle.getString("usEstado"));
        ListarEstados();
        ListarNiveles();
        if(oUsuarioModel.getIdUsuario()>0){
            edicion=true;
            etUsuarioUser.setText(oUsuarioModel.getNomUsuario());
            etNumeroDocumentoUser.setEnabled(false);
            etClaveUser.setText("");
            etClave2User.setText("");
            etNumeroDocumentoUser.setText("");
            etLocalUser.setText(oUsuarioModel.getNomTerminal());
            etCentroOperacionesUser.setText(oUsuarioModel.getCentroOperaciones());
            setNivelesAdapter();
            setEstadoAdapter();
        }else{
            etNumeroDocumentoUser.setEnabled(true);
            etClaveUser.setText("");
            etClave2User.setText("");
            etNumeroDocumentoUser.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnGrabarUser.getId()){
            if(edicion==false){
                oUsuarioModel.setNomUsuario(etUsuarioUser.getText().toString());
                oUsuarioModel.setPassUsuario(etClaveUser.getText().toString());
                oUsuarioModel.setPass2Usuario(etClave2User.getText().toString());
                oUsuarioModel.setNumeroDocumentoUsuario(etNumeroDocumentoUser.getText().toString());
                oUsuarioModel.setNomTerminal(etLocalUser.getText().toString());
                oUsuarioModel.setCentroOperaciones(etCentroOperacionesUser.getText().toString());
                if(!ValidarFormUsuario()){
                    return;
                }
                RegistroUsuario();
            }else{
                oUsuarioModel.setNomUsuario(etUsuarioUser.getText().toString());
                oUsuarioModel.setPassUsuario(etClaveUser.getText().toString());
                oUsuarioModel.setPass2Usuario(etClave2User.getText().toString());
                oUsuarioModel.setNumeroDocumentoUsuario(etNumeroDocumentoUser.getText().toString());
                oUsuarioModel.setNomTerminal(etLocalUser.getText().toString());
                oUsuarioModel.setCentroOperaciones(etCentroOperacionesUser.getText().toString());
                if(!ValidarFormUsuario()){
                    return;
                }
                ModificacionUsuario();
            }
        }
    }

    private void ListarEstados(){
        ArrayAdapter<Enumeraciones.Estado> estadoAdapter=new ArrayAdapter<Enumeraciones.Estado>(UserActivity.this,
                android.R.layout.simple_spinner_dropdown_item,Enumeraciones.Estado.values());
        spEstadoUser.setAdapter(estadoAdapter);
        spEstadoUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Enumeraciones.Estado entidad= (Enumeraciones.Estado) parent.getItemAtPosition(position);
                oUsuarioModel.setEstado(entidad.getIdEstado());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ListarNiveles(){

        ArrayAdapter<String> nivelAdapter=new ArrayAdapter<String>(UserActivity.this,
                android.R.layout.simple_spinner_dropdown_item, Lista.ListaNivelesUsuarios());
        spNivelUser.setAdapter(nivelAdapter);
        spNivelUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item= (String) parent.getItemAtPosition(position);
                oUsuarioModel.setNivel(item.toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setNivelesAdapter(){
        if(edicion){
            int i = 0;
            for (String item: Lista.ListaNivelesUsuarios()) {
                if(item.equals(oUsuarioModel.getNivel())){
                    spNivelUser.setSelection(i);
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    public void setEstadoAdapter(){
        if(edicion){
            int i = 0;
            for (Enumeraciones.Estado item: Enumeraciones.Estado.values()) {
                if(item.getIdEstado().equals(oUsuarioModel.getEstado())){
                    spEstadoUser.setSelection(i);
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    private void RegistroUsuario(){

        String ParamNombres, ParamValores;
        ParamNombres="usuarioval#claveval#DNI#usuarioreg#clavereg#clavereg2#nomlocal#copera#estado#nivel";
        ParamValores= SessionManager.getUsuario().getNomUsuario()+"#"+
                SessionManager.getUsuario().getPassUsuario()+"#"+
                oUsuarioModel.getNumeroDocumentoUsuario()+"#"+
                oUsuarioModel.getNomUsuario()+"#"+
                oUsuarioModel.getPassUsuario()+"#"+
                oUsuarioModel.getPass2Usuario()+"#"+
                oUsuarioModel.getNomTerminal()+"#"+
                oUsuarioModel.getCentroOperaciones()+"#"+
                oUsuarioModel.getEstado()+"#"+
                oUsuarioModel.getNivel();

        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setCreausuario",getString(R.string.key_method_ws_insert_usuario),
                getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),
                ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsRegistroUsuario(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Save_User_OK, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Save_User, Toast.LENGTH_LONG).show();
        }
    }

    private void ModificacionUsuario(){

        String ParamNombres, ParamValores;
        ParamNombres="usuarioval#claveval#Usuario_id#usuarioreg#clavereg#clavereg2#nomlocal#copera#estado#nivel";
        ParamValores= SessionManager.getUsuario().getNomUsuario()+"#"+
                SessionManager.getUsuario().getPassUsuario()+"#"+
                String.valueOf(oUsuarioModel.getIdUsuario())+"#"+
                oUsuarioModel.getNomUsuario()+"#"+
                oUsuarioModel.getPassUsuario()+"#"+
                oUsuarioModel.getPass2Usuario()+"#"+
                oUsuarioModel.getNomTerminal()+"#"+
                oUsuarioModel.getCentroOperaciones()+"#"+
                oUsuarioModel.getEstado()+"#"+
                oUsuarioModel.getNivel();

        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("setModificarusuario",getString(R.string.key_method_ws_update_usuario),
                getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),
                ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsModificacionUsuario(String respuesta){
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("OK")){
                Toast.makeText(getBaseContext(), R.string.msg_Modific_User_OK, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Modific_User, Toast.LENGTH_LONG).show();
        }
    }

    private boolean ValidarFormUsuario(){
        boolean estado=true;
        //Validar Nombre Usuario
        if(oUsuarioModel.getNomUsuario().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese el nombre del usuario.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.nomUsuario,oUsuarioModel.getNomUsuario())){
                Toast.makeText(getBaseContext(), "Ingrese un nombre de usuario válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        //Validar Clave
        if(edicion==false){
            if(oUsuarioModel.getPassUsuario().isEmpty()){
                Toast.makeText(getBaseContext(), "Ingrese el clave del usuario.", Toast.LENGTH_LONG).show();
                estado=false;
            }else{
                if(!Functions.ValidaRegExp(ExpReg.clave,oUsuarioModel.getPassUsuario())){
                    Toast.makeText(getBaseContext(), "Ingrese una clave de usuario válido.", Toast.LENGTH_LONG).show();
                    estado=false;
                }
            }
        }else{
            if(!oUsuarioModel.getPassUsuario().isEmpty() && !Functions.ValidaRegExp(ExpReg.clave,oUsuarioModel.getPassUsuario())){
                Toast.makeText(getBaseContext(), "Ingrese una clave de usuario válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }


        //Validar Clave 2
        if(edicion==false){
            if(oUsuarioModel.getPass2Usuario().isEmpty()){
                Toast.makeText(getBaseContext(), "Ingrese el clave 2 del usuario.", Toast.LENGTH_LONG).show();
                estado=false;
            }else{
                if(!Functions.ValidaRegExp(ExpReg.clave,oUsuarioModel.getPass2Usuario())){
                    Toast.makeText(getBaseContext(), "Ingrese una clave 2 de usuario válido.", Toast.LENGTH_LONG).show();
                    estado=false;
                }
            }
        }else{
            if(!oUsuarioModel.getPass2Usuario().isEmpty() && !Functions.ValidaRegExp(ExpReg.clave,oUsuarioModel.getPass2Usuario())){
                Toast.makeText(getBaseContext(), "Ingrese una clave 2 de usuario válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }


        //Validar Número Documento
        if(edicion==false){
            if(oUsuarioModel.getNumeroDocumentoUsuario().isEmpty()){
                Toast.makeText(getBaseContext(), "Ingrese el número de documento.", Toast.LENGTH_LONG).show();
                estado=false;
            }else{
                if(!Functions.ValidaRegExp(ExpReg.numeroDocumento,oUsuarioModel.getNumeroDocumentoUsuario())){
                    Toast.makeText(getBaseContext(), "Ingrese una número de documento válido.", Toast.LENGTH_LONG).show();
                    estado=false;
                }
            }
        }

        //Validar Local (Terminal)
        if(oUsuarioModel.getNomTerminal().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese el local del usuario.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.nomTerminal,oUsuarioModel.getNomTerminal())){
                Toast.makeText(getBaseContext(), "Ingrese un local de usuario válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        //Validar Centro Operaciones
        if(oUsuarioModel.getCentroOperaciones().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese el centro operaciones del usuario.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.nomTerminal,oUsuarioModel.getCentroOperaciones())){
                Toast.makeText(getBaseContext(), "Ingrese un centro operaciones de usuario válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        return estado;
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo.equals(getString(R.string.key_method_ws_insert_usuario))){
            wsRegistroUsuario(Respuesta);
        }else if(KeyMetodo.equals(getString(R.string.key_method_ws_update_usuario))){
            wsModificacionUsuario(Respuesta);
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