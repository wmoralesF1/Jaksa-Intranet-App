package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.reservas_pasajes.helper.AdaptadorRutas;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.RutaModel;

public class SelectRouteActivity extends AppCompatActivity implements View.OnClickListener  {

    TextView tvDescRutaSelectRoute;
    TextView tvFechaHoraReservaSelectRoute;
    TextView tvServicioSelectRoute;
    TextView tvPrecioSelectRoute;
    ListView lvRutasSelectRoute;
    AdaptadorRutas adaptador;
    Button btnPreviousSelectRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        lvRutasSelectRoute=findViewById(R.id.lvRutasSelectRoute);
        btnPreviousSelectRoute=findViewById(R.id.btnAtrasSelectRoute);
        btnPreviousSelectRoute.setOnClickListener(this);


        adaptador=new AdaptadorRutas(this, SessionManager.getTurnoViaje().getListaRutas());
        lvRutasSelectRoute.setAdapter(adaptador);
        lvRutasSelectRoute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                int n;
                RutaModel itemItinerario = (RutaModel) adapterView.getItemAtPosition(index);
                if(itemItinerario!=null){
                    SessionManager.getTurnoViaje().setRutaViaje(itemItinerario);
                    Continuar();
                }

            }
        });

        DetalleReserva();

    }

    private void DetalleReserva(){
        tvDescRutaSelectRoute=findViewById(R.id.tvDescRutaSelectRoute);
        tvFechaHoraReservaSelectRoute=findViewById(R.id.tvFechaHoraReservaSelectRoute);
        tvServicioSelectRoute=findViewById(R.id.tvServicioSelectRoute);
        tvPrecioSelectRoute=findViewById(R.id.tvPrecioSelectRoute);

        tvFechaHoraReservaSelectRoute.setText(SessionManager.getTurnoViaje().getFechaReserva() + " - " + SessionManager.getTurnoViaje().getHoraReserva());
        tvServicioSelectRoute.setText(SessionManager.getTurnoViaje().getNomServicio());
        if(SessionManager.getTurnoViaje().getRutaViaje()!=null){
            tvDescRutaSelectRoute.setText(SessionManager.getTurnoViaje().getRutaViaje().getRutaDescripcion());
            tvPrecioSelectRoute.setText("S/ " + String.valueOf(SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio()));
        }
    }

    private void Continuar(){
        Intent i;
        i = new Intent(this, MapBusActivity.class);
        startActivity(i);
    }

    private void Atras(){
        Intent i;
        i = new Intent(this, ListRoutesActivity.class);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==btnPreviousSelectRoute.getId()){
            Atras();
        }
    }
}