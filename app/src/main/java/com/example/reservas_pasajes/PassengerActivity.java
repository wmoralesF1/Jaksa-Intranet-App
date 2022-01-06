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

import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.helper.adaptadorPasajero;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.PasajeroModel;

import java.util.ArrayList;

public class PassengerActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llContenedor;
    private ListView lvPasajeros;
    private adaptadorPasajero adaptador;
    ArrayList<PasajeroModel> listaPasajeros = new ArrayList<>();
    Button btnReservar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        listaPasajeros=new ArrayList<>();
        lvPasajeros=(ListView)findViewById(R.id.lvPasajeros);
        btnReservar=(Button)findViewById(R.id.btnReservar);
        CargarPasajeros();
        adaptador=new adaptadorPasajero(this,listaPasajeros);
        lvPasajeros.setAdapter(adaptador);
        llContenedor=findViewById(R.id.llContenedor);
        GradientDrawable border = new GradientDrawable();
        //border.setColor(0xFFFFFFFF);
        border.setStroke(2, Color.parseColor("#000000"));
        llContenedor.setBackground(border);

        btnReservar.setOnClickListener(this);
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