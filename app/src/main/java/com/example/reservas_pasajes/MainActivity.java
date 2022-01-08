package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.reservas_pasajes.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SessionManager.setViaje(null);
        SessionManager.setUsuario(null);
        SessionManager.setListaTerminales(null);
        Intent i;
        i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}