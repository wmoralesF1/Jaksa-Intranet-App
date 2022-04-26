package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.Adaptadores.AdaptadorFiltroEmpleados;
import com.example.reservas_pasajes.helper.ExpReg;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.models.EmpleadoModel;
import com.example.reservas_pasajes.service.ApiClient;
import com.example.reservas_pasajes.service.ResponseListGeneric;
import com.example.reservas_pasajes.service.request.RequestFiltroEmpleado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView etNombresFilterEmployee;
    TextView etApellidosFilterEmployee;
    TextView etNumeroDocumentoFilterEmployee;
    Button btnBuscarFilterEmployee;
    Button btnAgregarFilterEmployee;
    Button btnEditarFilterEmployee;
    ListView lvListaEmpleadosFilterEmployee;
    AdaptadorFiltroEmpleados adaptador;
    String Nombres;
    String Apellidos;
    String NumeroDocumento;
    String TAG="Filtro de Empleados";
    EmpleadoModel oEmpleadoModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_employee);
        etNombresFilterEmployee= findViewById(R.id.etNombresFilterEmployee);
        etApellidosFilterEmployee= findViewById(R.id.etApellidosFilterEmployee);
        etNumeroDocumentoFilterEmployee= findViewById(R.id.etNumeroDocumentoFilterEmployee);
        btnBuscarFilterEmployee= findViewById(R.id.btnBuscarFilterEmployee);
        btnAgregarFilterEmployee= findViewById(R.id.btnAgregarFilterEmployee);
        btnEditarFilterEmployee= findViewById(R.id.btnEditarFilterEmployee);
        lvListaEmpleadosFilterEmployee= findViewById(R.id.lvListaEmpleadosFilterEmployee);

        btnBuscarFilterEmployee.setOnClickListener(this);
        btnAgregarFilterEmployee.setOnClickListener(this);
        btnEditarFilterEmployee.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnBuscarFilterEmployee.getId()){
            Nombres=etNombresFilterEmployee.getText().toString();
            Apellidos=etApellidosFilterEmployee.getText().toString();
            NumeroDocumento=etNumeroDocumentoFilterEmployee.getText().toString();
            if(ValidarFormBusqueda()==false){
                return;
            }
            RequestFiltroEmpleado oRequestFiltroEmpleado=new RequestFiltroEmpleado();
            oRequestFiltroEmpleado.setEmpNombre(Nombres);
            oRequestFiltroEmpleado.setEmpApellidos(Apellidos);
            oRequestFiltroEmpleado.setEmpNumeroDocumento(NumeroDocumento);
            FiltrarEmpleados(oRequestFiltroEmpleado);

        }else if(v.getId()==btnAgregarFilterEmployee.getId()){
            Intent i;
            i = new Intent(this, EmployeeActivity.class);
            i.putExtra("empId", 0);
            startActivity(i);
        }else if(v.getId()==btnEditarFilterEmployee.getId()){
            Intent i;
            i = new Intent(this, EmployeeActivity.class);
            i.putExtra("empId", oEmpleadoModel.getEmpId());
            startActivity(i);
        }

    }

    private void FiltrarEmpleados(RequestFiltroEmpleado request){
        ApiClient.getInstance().BuscarEmpleados(request, new Callback<ResponseListGeneric<EmpleadoModel>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<EmpleadoModel>> call, Response<ResponseListGeneric<EmpleadoModel>> response) {

                ResponseListGeneric<EmpleadoModel> data = response.body();
                if (response.isSuccessful()) {
                    if (data.isSuccess()) {
                        if(data.getData()!=null && data.getData().size()>0){
                            adaptador=new AdaptadorFiltroEmpleados(getApplicationContext(), data.getData());
                            lvListaEmpleadosFilterEmployee.setAdapter(adaptador);
                            lvListaEmpleadosFilterEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                                    oEmpleadoModel = (EmpleadoModel) adapterView.getItemAtPosition(index);
                                    for (int i = 0; i <= lvListaEmpleadosFilterEmployee.getChildCount()-1; i++) {
                                        if(index == i ){
                                            lvListaEmpleadosFilterEmployee.getChildAt(index).setBackgroundColor(Color.YELLOW);
                                            btnAgregarFilterEmployee.setEnabled(true);
                                            btnEditarFilterEmployee.setEnabled(true);
                                        }else{
                                            lvListaEmpleadosFilterEmployee.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                        }
                                    }
                                }
                            });
                        }else{
                            adaptador=new AdaptadorFiltroEmpleados(getApplicationContext(), new ArrayList<EmpleadoModel>());
                            lvListaEmpleadosFilterEmployee.setAdapter(adaptador);
                            Toast.makeText(getBaseContext(), "No se encontraron resultados.", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<EmpleadoModel>> call, Throwable t) {
                Log.v(TAG, "Error: " + t.getMessage());
            }
        });

    }

    private boolean ValidarFormBusqueda(){
        Boolean estado=true;
        if(Nombres.isEmpty() && Apellidos.isEmpty() && NumeroDocumento.isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese algun filtro de búsqueda.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Nombres.isEmpty() && !Functions.ValidaRegExp(ExpReg.nombresApellidos,Nombres))  {
                Toast.makeText(getBaseContext(), "Ingrese un nombre válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }

            if(!Apellidos.isEmpty() && !Functions.ValidaRegExp(ExpReg.nombresApellidos,Apellidos))  {
                Toast.makeText(getBaseContext(), "Ingrese apellidos válidos.", Toast.LENGTH_LONG).show();
                estado=false;
            }

            if(!NumeroDocumento.isEmpty() && !Functions.ValidaRegExp(ExpReg.numeroDocumento,NumeroDocumento)){
                Toast.makeText(getBaseContext(), "Ingrese número de documento válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }else{
                if(!NumeroDocumento.isEmpty() && !(NumeroDocumento.length()>=7 && NumeroDocumento.length()<=15))
                {
                    Toast.makeText(getBaseContext(), "Ingrese número de documento válido.", Toast.LENGTH_LONG).show();
                    estado=false;
                }
            }
        }
        return estado;
    }

}