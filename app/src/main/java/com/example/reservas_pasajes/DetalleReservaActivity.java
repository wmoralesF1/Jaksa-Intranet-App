package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.reservas_pasajes.helper.SessionManager;

public class DetalleReservaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvRutaDetalleReserva;
    TextView tvDescServicioDetalleReserva;
    TextView tvFechaHoraReservaDetalleReserva;
    TextView tvNomPasajeroDetalleReserva;
    TextView tvNumDocumentoDetalleReserva;
    TextView tvNumAsientoDetalleReserva;
    Button btnNuevaReservaDetalleReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reserva);

        tvRutaDetalleReserva = (TextView)findViewById(R.id.tvRutaDetalleReserva);
        tvDescServicioDetalleReserva = (TextView)findViewById(R.id.tvDescServicioDetalleReserva);
        tvFechaHoraReservaDetalleReserva = (TextView) findViewById(R.id.tvFechaHoraReservaDetalleReserva);
        tvNomPasajeroDetalleReserva = (TextView)findViewById(R.id.tvNomPasajeroDetalleReserva);
        tvNumDocumentoDetalleReserva = (TextView)findViewById(R.id.tvNumDocumentoDetalleReserva);
        tvNumAsientoDetalleReserva = (TextView) findViewById(R.id.tvNumAsientoDetalleReserva);
        btnNuevaReservaDetalleReserva = (Button) findViewById(R.id.btnNuevaReservaDetalleReserva);

        tvRutaDetalleReserva.setText(SessionManager.getTurnoViaje().getRutaViaje().getRutaDescripcion());
        tvDescServicioDetalleReserva.setText(SessionManager.getTurnoViaje().getNomServicio());
        tvFechaHoraReservaDetalleReserva.setText(SessionManager.getTurnoViaje().getHoraReserva() +" - " +
                SessionManager.getTurnoViaje().getFechaReserva());


        DetallePasajero();
        btnNuevaReservaDetalleReserva.setOnClickListener(this);
    }

    private void DetallePasajero(){
        tvNomPasajeroDetalleReserva.setText(SessionManager.getTurnoViaje().getListaPasajeros().get(0).getNombrePasajero() + " " +
                SessionManager.getTurnoViaje().getListaPasajeros().get(0).getApellidoPaternoPasajero() + " " +
                SessionManager.getTurnoViaje().getListaPasajeros().get(0).getApellidoMaternoPasajero());
        tvNumDocumentoDetalleReserva.setText(SessionManager.getTurnoViaje().getListaPasajeros().get(0).getNumDocumento());
        tvNumAsientoDetalleReserva.setText(SessionManager.getTurnoViaje().getListaPasajeros().get(0).getNumAsiento());
    }

    private void NuevaReserva(){
        SessionManager.setViaje(null);
        SessionManager.setSalidaTurnos(null);
        SessionManager.setTurnoViaje(null);
        Intent i;
        i = new Intent(this, SearchRoutesActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnNuevaReservaDetalleReserva.getId()){
            NuevaReserva();
        }
    }
}