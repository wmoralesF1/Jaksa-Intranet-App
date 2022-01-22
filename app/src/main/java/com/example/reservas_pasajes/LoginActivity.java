package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.MenuModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.UsuarioModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<TerminalModel> listaTerminal=new ArrayList<>();
    UsuarioModel usuarioEntidad=new UsuarioModel();
    String estado;
    String menuItems;

    EditText etUsuario;
    EditText etPassword;
    Button btnIngresar;
    AlertDialog pDialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsuario=findViewById(R.id.etUsuario);
        etPassword=findViewById(R.id.etPassword);
        btnIngresar=findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(this);
        pDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(R.string.msg_Progress_Login)
                .setCancelable(false)
                .build();
    }

    private void wsValidarLogin(String respuesta){
        if(respuesta.contains("|")){
            String CadenaVerificar=respuesta.replace("|","#");
            if(!CadenaVerificar.isEmpty()) {
                String[] Cadena = CadenaVerificar.split("#");
                estado = Cadena[0];
                menuItems = Cadena[2];
                if(estado.equals("True")){
                    UsuarioModel oUsuarioModel=new UsuarioModel();
                    oUsuarioModel.setListaMenu(listaMenus(menuItems));
                    oUsuarioModel.setNomUsuario(etUsuario.getText().toString());
                    oUsuarioModel.setPassUsuario(etPassword.getText().toString());
                    SessionManager.setUsuario(oUsuarioModel);
                    listarTerminales();
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.msg_Error_Credenciales_Login, Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getBaseContext(), R.string.msg_Error_Credenciales_Login, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_Credenciales_Login, Toast.LENGTH_LONG).show();
        }
    }

    private void wslistaTerminales(String respuesta){
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split(";");
            ArrayList<TerminalModel> listaTerminales=new ArrayList<>();
            for (String item: Cadena) {
                TerminalModel oTerminalModel=new TerminalModel(0,item.toString().toUpperCase());
                listaTerminales.add(oTerminalModel);
            }
            SessionManager.setListaTerminales(listaTerminales);
            if(SessionManager.getListaTerminales().size()>0){
                Intent i;
                i = new Intent(this.getApplicationContext(), ListaMenuActivity.class);
                startActivity(i);
            }

        }
    }

    private void validarLogin(){

        String ParamNombres, ParamValores;
        String Usuario, Password;
        Usuario=etUsuario.getText().toString();
        Password=etPassword.getText().toString();
        ParamNombres="us#pwd";
        ParamValores= Usuario + "#" + Password;
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("getParametro",getString(R.string.key_method_ws_login_user),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void listarTerminales(){

        String ParamNombres, ParamValores;
        ParamNombres="";
        ParamValores="";
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("Terminales",getString(R.string.key_method_ws_list_terminales),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){

        if(KeyMetodo.equals(getString(R.string.key_method_ws_login_user))){
            wsValidarLogin(Respuesta);
            return;
        }else if(KeyMetodo.equals(getString(R.string.key_method_ws_list_terminales))){
            wslistaTerminales(Respuesta);
            return;
        }
    }

    private ArrayList<MenuModel> listaMenus(String Textmenu){
        ArrayList<MenuModel> listaOpciones = new ArrayList<>();
        listaOpciones = new ArrayList<MenuModel>();
        listaOpciones.add(new MenuModel(getImagenMenu(Textmenu),
                Textmenu.toUpperCase(),"",Textmenu.toUpperCase(),getIdMenuOpcion(Textmenu),0));
        return listaOpciones;
    }

    private int getIdMenuOpcion(String keyImagen){
        int Id=0;
        switch(keyImagen.toUpperCase())
        {
            case "ADMINISTRATIVOS" :
                Id=1;
                break;

            case "OPERACIONES" :
                Id=2;
                break;
            case "VENTAS" :
                Id=3;
                break;

            case "SISTEMAS" :
                Id=4;
                break;

        }
        return Id;
    }

    private Drawable getImagenMenu(String keyImagen){
        Drawable imagen=null;
        switch(keyImagen.toUpperCase())
        {
            case "ADMINISTRATIVOS" :
                imagen=getResources().getDrawable(R.drawable.ic_manager);
                break;

            case "OPERACIONES" :
                imagen=getResources().getDrawable(R.drawable.ic_planning);
                break;
            case "VENTAS" :
                imagen=getResources().getDrawable(R.drawable.ic_sales);
                break;

            case "SISTEMAS" :
                imagen=getResources().getDrawable(R.drawable.ic_settings);
                break;

        }
        return imagen;
    }

    @Override
    public void onClick(View v) {
        validarLogin();

    }

    public class TaskCallWS extends AsyncTask<Void, Integer, String> {

        String Metodo;
        String Namespace;
        String URL;
        String ParametroNombres, ParametroValores;
        String [] Nombres;
        String [] Valores;
        String KeyMetodo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
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
                RespuestaWS(result,KeyMetodo);
            }

            pDialog.dismiss();
        }



        public void Parametros(String Metodo,String KeyMetodo, String Namespace, String URL, String ParametroNombres, String ParametroValores)
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
                Log.i("Hola", "Error " + e.getMessage());
            }
            return res;
        }
    }
}