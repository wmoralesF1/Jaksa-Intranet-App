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
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reservas_pasajes.Adaptadores.AdaptadorListaFiltroUsuarios;
import com.example.reservas_pasajes.Adaptadores.AdaptadorListaProgramacionViaje;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.EmpleadoModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.UsuarioModel;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class FilterUserActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUsuarioFilterUser;
    Spinner spTerminalFilterUser;
    Button btnBuscarFilterUser;
    Button btnAgregarFilterUser;
    Button btnEditarFilterUser;
    ListView lvListaUsuariosFilterUser;
    UsuarioModel oUsuarioModel;
    AdaptadorListaFiltroUsuarios adaptador;
    ArrayList<UsuarioModel> listaUsuarios=new ArrayList<>();
    String TAG="Busqueda de Usuarios";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_user);
        etUsuarioFilterUser=findViewById(R.id.etUsuarioFilterUser);
        btnBuscarFilterUser=findViewById(R.id.btnBuscarFilterUser);
        btnAgregarFilterUser=findViewById(R.id.btnAgregarFilterUser);
        btnEditarFilterUser=findViewById(R.id.btnEditarFilterUser);
        lvListaUsuariosFilterUser=findViewById(R.id.lvListaUsuariosFilterUser);
        btnBuscarFilterUser.setOnClickListener(this);
        btnAgregarFilterUser.setOnClickListener(this);
        btnEditarFilterUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnBuscarFilterUser.getId()){
            FiltrarUsuarios();
        }else if(v.getId()==btnAgregarFilterUser.getId()){
            Intent i;
            i = new Intent(this, UserActivity.class);
            i.putExtra("usId", 0);
            i.putExtra("usNom", "");
            i.putExtra("usLocal", "");
            i.putExtra("usCenOpe", "");
            i.putExtra("usNivel", "");
            i.putExtra("usEstado", "");
            startActivity(i);
        }else if(v.getId()==btnEditarFilterUser.getId()){
            Intent i;
            i = new Intent(this, UserActivity.class);
            i.putExtra("usId", oUsuarioModel.getIdUsuario());
            i.putExtra("usNom", oUsuarioModel.getNomUsuario());
            i.putExtra("usLocal", oUsuarioModel.getNomTerminal());
            i.putExtra("usCenOpe", oUsuarioModel.getCentroOperaciones());
            i.putExtra("usNivel", oUsuarioModel.getNivel());
            i.putExtra("usEstado", oUsuarioModel.getEstado());
            startActivity(i);
        }
    }

    private void FiltrarUsuarios(){
        adaptador=new AdaptadorListaFiltroUsuarios(this, new ArrayList<UsuarioModel>());
        lvListaUsuariosFilterUser.setAdapter(adaptador);
        String ParamNombres, ParamValores;
        ParamNombres="usuariolog#clavelog#userEdit";
        ParamValores= SessionManager.getUsuario().getNomUsuario()+"#"+
                SessionManager.getUsuario().getPassUsuario()+"#"+etUsuarioFilterUser.getText();
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("listarUsuarios",getString(R.string.key_method_ws_search_list_usuario),
                getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),
                ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsFiltrarUsuarios(String respuesta){
        listaUsuarios.clear();
        if(!respuesta.isEmpty()) {
            if(respuesta.contains("#")){
                String[] Cadena = respuesta.split("#");
                if(Cadena.length>0){
                    String[] Usuario_id = Cadena[0].split(";");
                    String[] NomUsuario = Cadena[1].split(";");
                    String[] NomTerminal = Cadena[2].split(";");
                    String[] CentroOperaciones = Cadena[3].split(";");
                    String[] Nivel = Cadena[4].split(";");
                    String[] Estado = Cadena[5].split(";");
                    if(Usuario_id.length>0){
                        for (int i = 0; i <= Usuario_id.length-1; i++) {
                            if(i<=Usuario_id.length-1
                                    && i<=NomUsuario.length-1
                                    && i<=NomTerminal.length-1
                                    && i<=CentroOperaciones.length-1
                                    && i<=Nivel.length-1
                                    && i<=Estado.length-1){
                                if((!Usuario_id[i].isEmpty() && !(Usuario_id[i] ==null))
                                        && (!NomUsuario[i].isEmpty() && !(NomUsuario[i] ==null))
                                        && (!NomTerminal[i].isEmpty() && !(NomTerminal[i] ==null))
                                        && (!CentroOperaciones[i].isEmpty() && !(CentroOperaciones[i] ==null))
                                        && (!Nivel[i].isEmpty() && !(Nivel[i] ==null))
                                        && (!Estado[i].isEmpty() && !(Estado[i] ==null)))
                                {
                                    UsuarioModel oUsuarioModel=new UsuarioModel();
                                    oUsuarioModel.setIdUsuario(Integer.parseInt(Usuario_id[i]));
                                    oUsuarioModel.setNomUsuario(NomUsuario[i]);
                                    oUsuarioModel.setNomTerminal(NomTerminal[i]);
                                    oUsuarioModel.setCentroOperaciones(CentroOperaciones[i]);
                                    oUsuarioModel.setNivel(Nivel[i]);
                                    oUsuarioModel.setEstado(Estado[i]);
                                    listaUsuarios.add(oUsuarioModel);
                                }
                            }
                        }
                        adaptador=new AdaptadorListaFiltroUsuarios(this, listaUsuarios);
                        lvListaUsuariosFilterUser.setAdapter(adaptador);
                        lvListaUsuariosFilterUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                                oUsuarioModel = (UsuarioModel) adapterView.getItemAtPosition(index);
                                for (int i = 0; i <= lvListaUsuariosFilterUser.getChildCount()-1; i++) {
                                    if(index == i ){
                                        lvListaUsuariosFilterUser.getChildAt(index).setBackgroundColor(Color.YELLOW);
                                        btnAgregarFilterUser.setEnabled(true);
                                        btnEditarFilterUser.setEnabled(true);
                                    }else{
                                        lvListaUsuariosFilterUser.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                    }
                                }
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
            }else{
                Toast.makeText(getBaseContext(), respuesta, Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.msg_Error_No_Usuarios, Toast.LENGTH_LONG).show();
        }
    }

    private void RespuestaWS(String Respuesta,String KeyMetodo){
        if(KeyMetodo.equals(getString(R.string.key_method_ws_search_list_usuario))){
            wsFiltrarUsuarios(Respuesta);
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