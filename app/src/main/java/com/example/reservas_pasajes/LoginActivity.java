package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.CargoModel;
import com.example.reservas_pasajes.models.GrupoEmpresarialModel;
import com.example.reservas_pasajes.models.LocalModel;
import com.example.reservas_pasajes.models.MenuModel;
import com.example.reservas_pasajes.models.PaisModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.TipoDocumentoModel;
import com.example.reservas_pasajes.models.TipoServicioModel;
import com.example.reservas_pasajes.models.UsuarioModel;
import com.example.reservas_pasajes.service.ApiClient;
import com.example.reservas_pasajes.service.ResponseListGeneric;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<TerminalModel> listaTerminal=new ArrayList<>();
    UsuarioModel usuarioEntidad=new UsuarioModel();
    String estado;
    String menuItems;

    EditText etUsuario;
    EditText etPassword;
    Button btnIngresar;
    AlertDialog pDialog=null;
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TAG="Login";
        etUsuario=findViewById(R.id.etUsuario);
        etPassword=findViewById(R.id.etPassword);
        btnIngresar=findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(this);
        pDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(R.string.msg_Progress_Login)
                .setCancelable(false)
                .build();

        ListarTiposDocumentos();
        ListarNacionalidades();
        ListarCargos();
        ListarTiposServicios();
        ListarGruposEmpresariales();
        ListarLocales();
    }

    private void ListarTiposDocumentos(){
        ApiClient.getInstance().ListarTipoDocumentoIdentidad(new Callback<ResponseListGeneric<TipoDocumentoModel>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<TipoDocumentoModel>> call, Response<ResponseListGeneric<TipoDocumentoModel>> response) {
                if(response.isSuccessful()) {

                    ResponseListGeneric<TipoDocumentoModel> data = response.body();
                    if (data.isSuccess()) {
                        SessionManager.setListadoTiposDocumentos(data.getData());
                    } else {
                    }
                }else{
                    Log.i(TAG, "Error en el listado de tipo de documentos");
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<TipoDocumentoModel>> call, Throwable t) {
                //hideProgress();
                Log.i(TAG, "Error en el listado de tipo de documentos");
            }
        });
    }

    private void ListarTiposServicios(){
        ApiClient.getInstance().ListarTiposServicios(new Callback<ResponseListGeneric<TipoServicioModel>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<TipoServicioModel>> call, Response<ResponseListGeneric<TipoServicioModel>> response) {
                //hideProgress();

                if(response.isSuccessful()) {

                    ResponseListGeneric<TipoServicioModel> data = response.body();
                    if (data.isSuccess()) {

                        SessionManager.setListadoTipoServicios(data.getData());
                    } else {
                    }
                }else{
                    Log.i(TAG, "Error en el listado de tipos de servicios");
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<TipoServicioModel>> call, Throwable t) {
                Log.i(TAG, "Error en el listado de tipos de servicios");
            }
        });
    }

    private void ListarNacionalidades(){
        ApiClient.getInstance().ListarPaises(new Callback<ResponseListGeneric<PaisModel>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<PaisModel>> call, Response<ResponseListGeneric<PaisModel>> response) {
                //hideProgress();

                if(response.isSuccessful()) {

                    ResponseListGeneric<PaisModel> data = response.body();
                    if (data.isSuccess()) {

                        SessionManager.setListadoPaises(data.getData());
                    } else {
                    }
                }else{
                    Log.i(TAG, "Error en el listado de paises");
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<PaisModel>> call, Throwable t) {
                Log.i(TAG, "Error en el listado de paises");
            }
        });
    }

    private void ListarCargos(){
        ApiClient.getInstance().ListarCargos(new Callback<ResponseListGeneric<CargoModel>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<CargoModel>> call, Response<ResponseListGeneric<CargoModel>> response) {
                //hideProgress();

                if(response.isSuccessful()) {

                    ResponseListGeneric<CargoModel> data = response.body();
                    if (data.isSuccess()) {

                        SessionManager.setListadoCargos(data.getData());
                    } else {
                    }
                }else{
                    Log.i(TAG, "Error en el listado de cargos");
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<CargoModel>> call, Throwable t) {
                Log.i(TAG, "Error en el listado de cargos");
            }
        });
    }

    private void ListarGruposEmpresariales(){
        ApiClient.getInstance().ListarGruposEmpresariales(new Callback<ResponseListGeneric<GrupoEmpresarialModel>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<GrupoEmpresarialModel>> call, Response<ResponseListGeneric<GrupoEmpresarialModel>> response) {
                //hideProgress();

                if(response.isSuccessful()) {

                    ResponseListGeneric<GrupoEmpresarialModel> data = response.body();
                    if (data.isSuccess()) {

                        SessionManager.setListadoGruposEmpresariales(data.getData());
                    } else {
                    }
                }else{
                    Log.i(TAG, "Error en el listado de grupos empresariales");
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<GrupoEmpresarialModel>> call, Throwable t) {
                Log.i(TAG, "Error en el listado de grupos empresariales");
            }
        });
    }

    private void ListarLocales(){
        ApiClient.getInstance().ListarLocales(new Callback<ResponseListGeneric<LocalModel>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<LocalModel>> call, Response<ResponseListGeneric<LocalModel>> response) {
                //hideProgress();

                if(response.isSuccessful()) {

                    ResponseListGeneric<LocalModel> data = response.body();
                    if (data.isSuccess()) {

                        SessionManager.setListadoLocales(data.getData());
                    } else {
                    }
                }else{
                    Log.i(TAG, "Error en el listado de locales");
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<LocalModel>> call, Throwable t) {
                Log.i(TAG, "Error en el listado de locales");
            }
        });
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
                Log.i(TAG, "Error " + e.getMessage());
            }
            return res;
        }
    }
}