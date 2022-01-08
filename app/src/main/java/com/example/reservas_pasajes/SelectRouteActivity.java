package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.RutaModel;

public class SelectRouteActivity extends AppCompatActivity implements View.OnClickListener  {

    TextView tvDescRutaSelectRoute;
    TextView tvFechaHoraReservaSelectRoute;
    TextView tvServicioSelectRoute;
    TextView tvPrecioSelectRoute;
    Spinner sRutaSelectRoute;
    TextView tvAsientosLibresSelectRoute;
    Button btnPreviousSelectRoute;
    Button btnNextSelectRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        sRutaSelectRoute=findViewById(R.id.sRutaSelectRoute);
        btnPreviousSelectRoute=findViewById(R.id.btnAtrasSelectRoute);
        btnNextSelectRoute=findViewById(R.id.btnContinuarSelectRoute);
        btnPreviousSelectRoute.setOnClickListener(this);
        btnNextSelectRoute.setOnClickListener(this);

        ArrayAdapter<RutaModel> rutasAdapter=new ArrayAdapter<RutaModel>(SelectRouteActivity.this,
                android.R.layout.simple_spinner_dropdown_item, SessionManager.getTurnoViaje().getListaRutas());

        sRutaSelectRoute.setAdapter(rutasAdapter);
        sRutaSelectRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                RutaModel entidad= (RutaModel) parent.getItemAtPosition(index);
                SessionManager.getTurnoViaje().setRutaViaje(entidad);
                DetalleReserva();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void DetalleReserva(){
        tvDescRutaSelectRoute=findViewById(R.id.tvDescRutaSelectRoute);
        tvFechaHoraReservaSelectRoute=findViewById(R.id.tvFechaHoraReservaSelectRoute);
        tvServicioSelectRoute=findViewById(R.id.tvServicioSelectRoute);
        tvPrecioSelectRoute=findViewById(R.id.tvPrecioSelectRoute);
        tvAsientosLibresSelectRoute=findViewById(R.id.tvAsientosLibresSelectRoute);

        tvFechaHoraReservaSelectRoute.setText(SessionManager.getTurnoViaje().getFechaReserva() + " - " + SessionManager.getTurnoViaje().getHoraReserva());
        tvServicioSelectRoute.setText(SessionManager.getTurnoViaje().getNomServicio());
        tvAsientosLibresSelectRoute.setText(String.valueOf(SessionManager.getTurnoViaje().getAsientosLibres()));
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
        }else if(v.getId()==btnNextSelectRoute.getId()){
            Continuar();
        }
    }
}