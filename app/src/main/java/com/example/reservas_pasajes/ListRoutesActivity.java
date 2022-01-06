package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.ViajeModel;

import java.util.ArrayList;
import java.util.List;

public class ListRoutesActivity extends AppCompatActivity {

    private ListView lvItinerarios;
    private adaptadorItinerarios adaptador;
    TextView tvnumresultados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_routes);
        lvItinerarios=(ListView)findViewById(R.id.lvItinerarios);
        tvnumresultados=(TextView)findViewById(R.id.tvnumresultados);

        adaptador=new adaptadorItinerarios(this, SessionManager.getSalidaTurnos());
        lvItinerarios.setAdapter(adaptador);
        tvnumresultados.setText(SessionManager.getSalidaTurnos().size() +  " Resultados");
    }

}