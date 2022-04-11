package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.Adaptadores.AdaptadorDetalleProduccionBus;
import com.example.reservas_pasajes.Adaptadores.AdaptadorListaProgramacionViaje;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.models.ViajeModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class ProductionBusActivity extends AppCompatActivity {
    TextView tvtituloProductionBus;
    TextView tvTotalPaxDetailProducctionBus;
    TextView tvTotalMontoDetailProducctionBus;
    TextView tvHoraPartidaDetailProducctionBus;
    TextView tvIdViajeDetailProducctionBus;
    ListView lvListaProduccionProductionBus;
    AdaptadorDetalleProduccionBus adaptador;
    ArrayList<ViajeModel> listaDetalleProduccionBus=new ArrayList<>();
    int TotalPax=0;
    double TotalMonto=0;
    int Id_Viaje=0;
    String NumBus="";
    String HoraPartida="";
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_bus);
        TAG="Producción de Bus";
        tvtituloProductionBus=findViewById(R.id.tvtituloProductionBus);
        tvHoraPartidaDetailProducctionBus=findViewById(R.id.tvHoraPartidaDetailProducctionBus);
        tvIdViajeDetailProducctionBus=findViewById(R.id.tvIdViajeDetailProducctionBus);
        tvTotalPaxDetailProducctionBus=findViewById(R.id.tvTotalPaxDetailProducctionBus);
        tvTotalMontoDetailProducctionBus=findViewById(R.id.tvTotalMontoDetailProducctionBus);
        lvListaProduccionProductionBus=findViewById(R.id.lvListaProduccionProductionBus);

        Bundle bundle = getIntent().getExtras();
        Id_Viaje = bundle.getInt("idViaje");
        NumBus = bundle.getString("numBus");
        HoraPartida = bundle.getString("horaPartida");
        tvtituloProductionBus.setText(tvtituloProductionBus.getText().toString() + NumBus );
        tvHoraPartidaDetailProducctionBus.setText(HoraPartida);
        tvIdViajeDetailProducctionBus.setText(String.valueOf(Id_Viaje));
        DetalleProduccionViaje(Id_Viaje);
    }

    private void DetalleProduccionViaje(Integer Id_Viaje){
        adaptador=new AdaptadorDetalleProduccionBus(this, new ArrayList<ViajeModel>());
        lvListaProduccionProductionBus.setAdapter(adaptador);
        TotalPax=0;
        TotalMonto=0;
        String ParamNombres, ParamValores;
        ParamNombres="NroViaje";
        ParamValores= String.valueOf(Id_Viaje);
        TaskCallWS ws=new TaskCallWS();
        ws.Parametros("ProduccionBus",getString(R.string.key_method_ws_detail_production_bus),getString(R.string.url_web_service_namespace),getString(R.string.url_web_service),ParamNombres,ParamValores);
        ws.execute();
    }

    private void wsDetalleProduccionViaje(String respuesta){
        listaDetalleProduccionBus.clear();
        if(!respuesta.isEmpty()) {
            String[] Cadena = respuesta.split("#");
            if(Cadena.length>0){
                String[] Tipo = Cadena[0].split(";");
                String[] Ruta = Cadena[1].split(";");
                String[] NumPax = Cadena[2].split(";");
                String[] Monto = Cadena[3].split(";");
                if(Tipo.length>0){
                    for (int i = 0; i <= Tipo.length-1; i++) {
                        if(i<=Tipo.length-1
                                && i<=Ruta.length-1
                                && i<=NumPax.length-1
                                && i<=Monto.length-1){
                            if((!Tipo[i].isEmpty() && !(Tipo[i] ==null))
                                    && (!Ruta[i].isEmpty() && !(Ruta[i] ==null))
                                    && (!NumPax[i].isEmpty() && !(NumPax[i] ==null))
                                    && (!Monto[i].isEmpty() && !(Monto[i] ==null)))
                            {
                                ViajeModel oViajeModel=new ViajeModel();
                                oViajeModel.setTipoVenta(Tipo[i]);
                                oViajeModel.setNomRutaViaje(Ruta[i]);
                                oViajeModel.setNumPax(Integer.parseInt(NumPax[i]));
                                oViajeModel.setMontoPax(Double.parseDouble(Monto[i]));
                                listaDetalleProduccionBus.add(oViajeModel);
                                TotalPax+=Integer.parseInt(NumPax[i]);
                                TotalMonto+=Double.parseDouble(Monto[i]);

                            }
                        }
                    }
                    adaptador=new AdaptadorDetalleProduccionBus(this, listaDetalleProduccionBus);
                    lvListaProduccionProductionBus.setAdapter(adaptador);
                    tvTotalPaxDetailProducctionBus.setText(String.valueOf(TotalPax));
                    tvTotalMontoDetailProducctionBus.setText(String.valueOf(TotalMonto));

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
        if(KeyMetodo.equals(getString(R.string.key_method_ws_detail_production_bus))){
            wsDetalleProduccionViaje(Respuesta);
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