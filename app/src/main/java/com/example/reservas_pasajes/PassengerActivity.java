package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.helper.adaptadorPasajero;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.PasajeroModel;
import com.example.reservas_pasajes.models.TipoDocumentoModel;

import java.util.ArrayList;

public class PassengerActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llContenedor;

    private adaptadorPasajero adaptador;
    ArrayList<PasajeroModel> listaPasajeros = new ArrayList<>();




    TextView tvDescRutaPassenger;
    TextView tvFechaHoraReservaPassenger;
    TextView tvServicioPassenger;
    TextView tvPrecioPassenger;
    private ListView lvPasajerosPassenger;
    Button btnReservarPassenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        btnReservarPassenger=(Button)findViewById(R.id.btnReservarPassenger);
        lvPasajerosPassenger=(ListView)findViewById(R.id.lvPasajerosPassenger);
        DetalleReserva();
        ListarTipoDocumento();
        listaPasajeros=new ArrayList<>();


        //CargarPasajeros();
        adaptador=new adaptadorPasajero(this,SessionManager.getTurnoViaje().getListaPasajeros());
        lvPasajerosPassenger.setAdapter(adaptador);
        llContenedor=findViewById(R.id.llContenedor);
        GradientDrawable border = new GradientDrawable();
        //border.setColor(0xFFFFFFFF);
        border.setStroke(2, Color.parseColor("#000000"));
        llContenedor.setBackground(border);

        btnReservarPassenger.setOnClickListener(this);
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
        listaTipoDocumentos.add(new TipoDocumentoModel(1,"D.N.I."));
        listaTipoDocumentos.add(new TipoDocumentoModel(4,"CARNET EXTRANJERIA"));
        listaTipoDocumentos.add(new TipoDocumentoModel(7,"PASAPORTE"));
        SessionManager.setListaTiposDocumentos(null);
        SessionManager.setListaTiposDocumentos(listaTipoDocumentos);

    }

    private void CargarPasajeros(){
        listaPasajeros.add(new PasajeroModel(0, "1", "43259580",
                "04",             "Williams", "Morales",
                "Caballero", 25,0, "1"));
        listaPasajeros.add(new PasajeroModel(0, "1", "43259581",
                "05",             "Milagros", "Morales",
                "Caballero", 25,0, "1"));
        listaPasajeros.add(new PasajeroModel(0, "1", "43259582",
                "14",             "Cristian", "Morales",
                "Caballero", 25,0, "1"));
        listaPasajeros.add(new PasajeroModel(0, "1", "43259583",
                "24",             "Joel", "Morales",
                "Caballero", 25,0, "1"));
        listaPasajeros.add(new PasajeroModel(0, "1", "43259584",
                "34",             "Marcial", "Morales",
                "Vidal", 25,0, "1"));
        listaPasajeros.add(new PasajeroModel(0, "1", "43259585",
                "44",             "Carmen", "Caballero",
                "Rojo", 25,0, "1"));

    }

    @Override
    public void onClick(View v) {
        Intent i;
        i = new Intent(this, DetalleReservaActivity.class);
        startActivity(i);
    }
}