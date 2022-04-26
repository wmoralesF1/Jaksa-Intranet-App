package com.example.reservas_pasajes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reservas_pasajes.Adaptadores.AdaptadorFiltroEmpleados;
import com.example.reservas_pasajes.helper.Enumeraciones;
import com.example.reservas_pasajes.helper.ExpReg;
import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.CargoModel;
import com.example.reservas_pasajes.models.EmpleadoModel;
import com.example.reservas_pasajes.models.GrupoEmpresarialModel;
import com.example.reservas_pasajes.models.LocalModel;
import com.example.reservas_pasajes.models.PaisModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.TipoDocumentoModel;
import com.example.reservas_pasajes.models.TipoServicioModel;
import com.example.reservas_pasajes.service.ApiClient;
import com.example.reservas_pasajes.service.ResponseGeneric;
import com.example.reservas_pasajes.service.ResponseListGeneric;
import com.example.reservas_pasajes.service.request.RequestFiltroEmpleado;
import com.example.reservas_pasajes.service.request.RequestModificaEmpleado;
import com.example.reservas_pasajes.service.request.RequestRegistroEmpleado;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener{
    DatePickerDialog datePicker;
    EditText etNombresEmployee;
    EditText etApellidosEmployee;
    Spinner spTipoDocumentoEmployee;
    EditText etNumeroDocumentoEmployee;
    Spinner spEstadoCivilEmployee;
    EditText etFechaNacimientoEmployee;
    Spinner spNacionalidadEmployee;
    Spinner spCargoEmployee;
    Spinner spCategoriaLicenciaEmployee;
    EditText etNumeroLicenciaEmployee;
    Spinner spTipoServicioEmployee;
    Spinner spGrupoEmpresarialEmployee;
    Spinner spLocalEmployee;
    Spinner spEstadoEmployee;
    Button btnGrabarEmployee;
    EmpleadoModel oEmpleadoModel;
    Boolean edicion=false;
    String TAG="Registro de Empleado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        etNombresEmployee= findViewById(R.id.etNombresEmployee);
        etApellidosEmployee= findViewById(R.id.etApellidosEmployee);
        spTipoDocumentoEmployee= findViewById(R.id.spTipoDocumentoEmployee);
        etNumeroDocumentoEmployee= findViewById(R.id.etNumeroDocumentoEmployee);
        spEstadoCivilEmployee= findViewById(R.id.spEstadoCivilEmployee);
        etFechaNacimientoEmployee= findViewById(R.id.etFechaNacimientoEmployee);
        spNacionalidadEmployee= findViewById(R.id.spNacionalidadEmployee);
        spCargoEmployee= findViewById(R.id.spCargoEmployee);
        spCategoriaLicenciaEmployee= findViewById(R.id.spCategoriaLicenciaEmployee);
        etNumeroLicenciaEmployee= findViewById(R.id.etNumeroLicenciaEmployee);
        spTipoServicioEmployee= findViewById(R.id.spTipoServicioEmployee);
        spGrupoEmpresarialEmployee= findViewById(R.id.spGrupoEmpresarialEmployee);
        spLocalEmployee= findViewById(R.id.spLocalEmployee);
        spEstadoEmployee= findViewById(R.id.spEstadoEmployee);
        btnGrabarEmployee= findViewById(R.id.btnGrabarEmployee);
        btnGrabarEmployee.setOnClickListener(this);
        oEmpleadoModel=new EmpleadoModel();

        etFechaNacimientoEmployee.setText(Functions.FechaHoy());
        final int year=Functions.NumeroAñoFechaHoy();
        final int month=Functions.NumeroMesFechaHoy();
        final int day=Functions.NumeroDiaFechaHoy();
        etFechaNacimientoEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(EmployeeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                etFechaNacimientoEmployee.setText(Functions.Fecha(day,month+1,year));
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });
        oEmpleadoModel.setEmpNacionalidad(etFechaNacimientoEmployee.getText().toString());

        Bundle bundle = getIntent().getExtras();
        oEmpleadoModel.setEmpId(bundle.getInt("empId"));
        ListarTiposDocumentos();
        ListarNacionalidades();
        ListarCargos();
        ListarTiposServicios();
        ListarGruposEmpresariales();
        ListarLocales();
        ListarEstadosCiviles();
        ListarCategoriaLicencias();
        ListarEstados();
        if(oEmpleadoModel.getEmpId()>0){
            edicion=true;
            ObtenerEmpleado(oEmpleadoModel.getEmpId());
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnGrabarEmployee.getId()){
            if(edicion==false){
                oEmpleadoModel.setEmpNombre(etNombresEmployee.getText().toString());
                oEmpleadoModel.setEmpApellidos(etApellidosEmployee.getText().toString());
                oEmpleadoModel.setEmpNumeroDocumento(etNumeroDocumentoEmployee.getText().toString());
                oEmpleadoModel.setEmpFechaNacimiento(etFechaNacimientoEmployee.getText().toString());
                oEmpleadoModel.setEmpNumeroLicencia(etNumeroLicenciaEmployee.getText().toString());
                if(!ValidarFormEmpleado()){
                    return;
                }
                GrabarEmpleado();
            }else{
                oEmpleadoModel.setEmpNombre(etNombresEmployee.getText().toString());
                oEmpleadoModel.setEmpApellidos(etApellidosEmployee.getText().toString());
                oEmpleadoModel.setEmpNumeroDocumento(etNumeroDocumentoEmployee.getText().toString());
                oEmpleadoModel.setEmpFechaNacimiento(etFechaNacimientoEmployee.getText().toString());
                oEmpleadoModel.setEmpNumeroLicencia(etNumeroLicenciaEmployee.getText().toString());
                if(!ValidarFormEmpleado()){
                    return;
                }
                ModificarEmpleado();
            }
        }
    }

    private void ListarTiposDocumentos(){
        if(SessionManager.getListadoTiposDocumentos().size()>0){
            ArrayAdapter<TipoDocumentoModel> tipoDocumentosAdapter=new ArrayAdapter<TipoDocumentoModel>(EmployeeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, SessionManager.getListadoTiposDocumentos());
            spTipoDocumentoEmployee.setAdapter(tipoDocumentosAdapter);
            spTipoDocumentoEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TipoDocumentoModel entidad= (TipoDocumentoModel) parent.getItemAtPosition(position);
                    oEmpleadoModel.setEmpTipoDocumentoId(entidad.getIdTipoDocumento());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ApiClient.getInstance().ListarTipoDocumentoIdentidad(new Callback<ResponseListGeneric<TipoDocumentoModel>>() {
                @Override
                public void onResponse(Call<ResponseListGeneric<TipoDocumentoModel>> call, Response<ResponseListGeneric<TipoDocumentoModel>> response) {
                    if(response.isSuccessful()) {

                        ResponseListGeneric<TipoDocumentoModel> data = response.body();
                        if (data.isSuccess()) {
                            SessionManager.setListadoTiposDocumentos(data.getData());
                            ArrayAdapter<TipoDocumentoModel> tipoDocumentosAdapter=new ArrayAdapter<TipoDocumentoModel>(EmployeeActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, SessionManager.getListadoTiposDocumentos());
                            spTipoDocumentoEmployee.setAdapter(tipoDocumentosAdapter);
                            spTipoDocumentoEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                                    setTipoDocumentoAdapter();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseListGeneric<TipoDocumentoModel>> call, Throwable t) {
                    //hideProgress();
                    Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                }
            });
        }



    }

    private void ListarTiposServicios(){
        if(SessionManager.getListadoTipoServicios().size()>0){
            ArrayAdapter<TipoServicioModel> tiposerviciosAdapter=new ArrayAdapter<TipoServicioModel>(EmployeeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoTipoServicios());
            spTipoServicioEmployee.setAdapter(tiposerviciosAdapter);
            spTipoServicioEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TipoServicioModel entidad= (TipoServicioModel) parent.getItemAtPosition(position);
                    oEmpleadoModel.setEmpTipoServicioId(entidad.getIdServicio());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ApiClient.getInstance().ListarTiposServicios(new Callback<ResponseListGeneric<TipoServicioModel>>() {
                @Override
                public void onResponse(Call<ResponseListGeneric<TipoServicioModel>> call, Response<ResponseListGeneric<TipoServicioModel>> response) {
                    //hideProgress();

                    if(response.isSuccessful()) {

                        ResponseListGeneric<TipoServicioModel> data = response.body();
                        if (data.isSuccess()) {
                            SessionManager.setListadoTipoServicios(data.getData());
                            ArrayAdapter<TipoServicioModel> tiposerviciosAdapter=new ArrayAdapter<TipoServicioModel>(EmployeeActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoTipoServicios());
                            spTipoServicioEmployee.setAdapter(tiposerviciosAdapter);
                            spTipoServicioEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                                    setTipoServiciosAdapter();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseListGeneric<TipoServicioModel>> call, Throwable t) {
                    //hideProgress();
                    Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void ListarNacionalidades(){

        if(SessionManager.getListadoPaises().size()>0){
            ArrayAdapter<PaisModel> paisesAdapter=new ArrayAdapter<PaisModel>(EmployeeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoPaises());
            spNacionalidadEmployee.setAdapter(paisesAdapter);
            spNacionalidadEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    PaisModel entidad= (PaisModel) parent.getItemAtPosition(position);
                    oEmpleadoModel.setEmpNacionalidad(entidad.getPaisNombre());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ApiClient.getInstance().ListarPaises(new Callback<ResponseListGeneric<PaisModel>>() {
                @Override
                public void onResponse(Call<ResponseListGeneric<PaisModel>> call, Response<ResponseListGeneric<PaisModel>> response) {
                    if(response.isSuccessful()) {
                        ResponseListGeneric<PaisModel> data = response.body();
                        if (data.isSuccess()) {
                            SessionManager.setListadoPaises(data.getData());
                            ArrayAdapter<PaisModel> paisesAdapter=new ArrayAdapter<PaisModel>(EmployeeActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoPaises());
                            spNacionalidadEmployee.setAdapter(paisesAdapter);
                            spNacionalidadEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                                    setNacionalidadesAdapter();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseListGeneric<PaisModel>> call, Throwable t) {
                    //hideProgress();
                    Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void ListarCargos(){
        if(SessionManager.getListadoCargos().size()>0){
            ArrayAdapter<CargoModel> cargosAdapter=new ArrayAdapter<CargoModel>(EmployeeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoCargos());
            spCargoEmployee.setAdapter(cargosAdapter);
            spCargoEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CargoModel entidad= (CargoModel) parent.getItemAtPosition(position);
                    oEmpleadoModel.setEmpCargoId(entidad.getCargoId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ApiClient.getInstance().ListarCargos(new Callback<ResponseListGeneric<CargoModel>>() {
                @Override
                public void onResponse(Call<ResponseListGeneric<CargoModel>> call, Response<ResponseListGeneric<CargoModel>> response) {
                    if(response.isSuccessful()) {
                        ResponseListGeneric<CargoModel> data = response.body();
                        if (data.isSuccess()) {
                            SessionManager.setListadoCargos(data.getData());
                            ArrayAdapter<CargoModel> cargosAdapter=new ArrayAdapter<CargoModel>(EmployeeActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoCargos());
                            spCargoEmployee.setAdapter(cargosAdapter);
                            spCargoEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                                    setCargosAdapter();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseListGeneric<CargoModel>> call, Throwable t) {
                    //hideProgress();
                    Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void ListarGruposEmpresariales(){
        if(SessionManager.getListadoGruposEmpresariales().size()>0){
            ArrayAdapter<GrupoEmpresarialModel> grupoEmpresarialAdapter=new ArrayAdapter<GrupoEmpresarialModel>(EmployeeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoGruposEmpresariales());
            spGrupoEmpresarialEmployee.setAdapter(grupoEmpresarialAdapter);
            spGrupoEmpresarialEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    GrupoEmpresarialModel entidad= (GrupoEmpresarialModel) parent.getItemAtPosition(position);
                    oEmpleadoModel.setEmpGrpEmpresarialId(entidad.getGrpempId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ApiClient.getInstance().ListarGruposEmpresariales(new Callback<ResponseListGeneric<GrupoEmpresarialModel>>() {
                @Override
                public void onResponse(Call<ResponseListGeneric<GrupoEmpresarialModel>> call, Response<ResponseListGeneric<GrupoEmpresarialModel>> response) {
                    //hideProgress();

                    if(response.isSuccessful()) {

                        ResponseListGeneric<GrupoEmpresarialModel> data = response.body();
                        if (data.isSuccess()) {
                            SessionManager.setListadoGruposEmpresariales(data.getData());
                            ArrayAdapter<GrupoEmpresarialModel> grupoEmpresarialAdapter=new ArrayAdapter<GrupoEmpresarialModel>(EmployeeActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoGruposEmpresariales());
                            spGrupoEmpresarialEmployee.setAdapter(grupoEmpresarialAdapter);
                            spGrupoEmpresarialEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                                    setGruposEmpresarialesAdapter();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseListGeneric<GrupoEmpresarialModel>> call, Throwable t) {
                    //hideProgress();
                    Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void ListarLocales(){
        if(SessionManager.getListadoLocales().size()>0){
            ArrayAdapter<LocalModel> localesAdapter=new ArrayAdapter<LocalModel>(EmployeeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoLocales());
            spLocalEmployee.setAdapter(localesAdapter);
            spLocalEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    LocalModel entidad= (LocalModel) parent.getItemAtPosition(position);
                    oEmpleadoModel.setEmpLocalId(entidad.getLocalId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ApiClient.getInstance().ListarLocales(new Callback<ResponseListGeneric<LocalModel>>() {
                @Override
                public void onResponse(Call<ResponseListGeneric<LocalModel>> call, Response<ResponseListGeneric<LocalModel>> response) {
                    //hideProgress();

                    if(response.isSuccessful()) {

                        ResponseListGeneric<LocalModel> data = response.body();
                        if (data.isSuccess()) {
                            SessionManager.setListadoLocales(data.getData());
                            ArrayAdapter<LocalModel> localesAdapter=new ArrayAdapter<LocalModel>(EmployeeActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,SessionManager.getListadoLocales());
                            spLocalEmployee.setAdapter(localesAdapter);
                            spLocalEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //TerminalModel entidad= (TerminalModel) parent.getItemAtPosition(position);
                                    setLocalesAdapter();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseListGeneric<LocalModel>> call, Throwable t) {
                    //hideProgress();
                    Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void ListarEstadosCiviles(){
        ArrayAdapter<Enumeraciones.EstadoCivil> estadosCivilesAdapter=new ArrayAdapter<Enumeraciones.EstadoCivil>(EmployeeActivity.this,
                android.R.layout.simple_spinner_dropdown_item,Enumeraciones.EstadoCivil.values());
        spEstadoCivilEmployee.setAdapter(estadosCivilesAdapter);
        spEstadoCivilEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Enumeraciones.EstadoCivil entidad= (Enumeraciones.EstadoCivil) parent.getItemAtPosition(position);
                oEmpleadoModel.setEmpEstadoCivil(entidad.getIdEstadoCivil());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void ListarCategoriaLicencias(){
        ArrayAdapter<Enumeraciones.CategoriaLicencia> categoriaLicenciasAdapter=new ArrayAdapter<Enumeraciones.CategoriaLicencia>(EmployeeActivity.this,
                android.R.layout.simple_spinner_dropdown_item,Enumeraciones.CategoriaLicencia.values());
        spCategoriaLicenciaEmployee.setAdapter(categoriaLicenciasAdapter);
        spCategoriaLicenciaEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Enumeraciones.CategoriaLicencia entidad= (Enumeraciones.CategoriaLicencia) parent.getItemAtPosition(position);
                oEmpleadoModel.setEmpLicenciaCategoriaId(entidad.getIdCategoriaLicencia());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ListarEstados(){
        ArrayAdapter<Enumeraciones.Estado> estadoAdapter=new ArrayAdapter<Enumeraciones.Estado>(EmployeeActivity.this,
                android.R.layout.simple_spinner_dropdown_item,Enumeraciones.Estado.values());
        spEstadoEmployee.setAdapter(estadoAdapter);
        spEstadoEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Enumeraciones.Estado entidad= (Enumeraciones.Estado) parent.getItemAtPosition(position);
                oEmpleadoModel.setEmpEstado(entidad.getIdEstado());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ObtenerEmpleado(int EmpId){
        ApiClient.getInstance().ObtenerEmpleado(EmpId,new Callback<ResponseGeneric<EmpleadoModel>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<EmpleadoModel>> call, Response<ResponseGeneric<EmpleadoModel>> response) {
                if(response.isSuccessful()) {
                    ResponseGeneric<EmpleadoModel> data = response.body();
                    if (data.isSuccess()) {
                        oEmpleadoModel=data.getData();
                        etNombresEmployee.setText(oEmpleadoModel.getEmpNombre());
                        etApellidosEmployee.setText(oEmpleadoModel.getEmpApellidos());
                        etNumeroDocumentoEmployee.setText(oEmpleadoModel.getEmpNumeroDocumento());
                        etFechaNacimientoEmployee.setText(oEmpleadoModel.getEmpFechaNacimiento());
                        etNumeroLicenciaEmployee.setText(oEmpleadoModel.getEmpNumeroLicencia());
                        setTipoDocumentoAdapter();
                        setTipoServiciosAdapter();
                        setNacionalidadesAdapter();
                        setCargosAdapter();
                        setGruposEmpresarialesAdapter();
                        setLocalesAdapter();
                        setEstadoCivilAdapter();
                        setCategoriaLicenciaAdapter();
                        setEstadoAdapter();

                    } else {
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<EmpleadoModel>> call, Throwable t) {
                //hideProgress();
                Toast.makeText(getBaseContext(), "Ocurrio en error en el registro.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setTipoDocumentoAdapter(){
        if(edicion){
            if(SessionManager.getListadoTiposDocumentos().size() > 0){
                int i = 0;
                for (TipoDocumentoModel item: SessionManager.getListadoTiposDocumentos()) {
                    if(item.getIdTipoDocumento() == oEmpleadoModel.getEmpTipoDocumentoId()){
                        spTipoDocumentoEmployee.setSelection(i);
                        return;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    public void setTipoServiciosAdapter(){
        if(edicion){
            if(SessionManager.getListadoTipoServicios().size() > 0){
                int i = 0;
                for (TipoServicioModel item: SessionManager.getListadoTipoServicios()) {
                    if(item.getIdServicio().equals(oEmpleadoModel.getEmpTipoServicioId())){
                        spTipoServicioEmployee.setSelection(i);
                        return;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    public void setNacionalidadesAdapter(){
        if(edicion){
            if(SessionManager.getListadoPaises().size() > 0){
                int i = 0;
                for (PaisModel item: SessionManager.getListadoPaises()) {
                    if(item.getPaisNombre().equals(oEmpleadoModel.getEmpNacionalidad())){
                        spNacionalidadEmployee.setSelection(i);
                        return;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    public void setCargosAdapter(){
        if(edicion){
            if(SessionManager.getListadoCargos().size() > 0){
                int i = 0;
                for (CargoModel item: SessionManager.getListadoCargos()) {
                    if(item.getCargoId()==oEmpleadoModel.getEmpCargoId()){
                        spCargoEmployee.setSelection(i);
                        return;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    public void setGruposEmpresarialesAdapter(){
        if(edicion){
            if(SessionManager.getListadoGruposEmpresariales().size() > 0){
                int i = 0;
                for (GrupoEmpresarialModel item: SessionManager.getListadoGruposEmpresariales()) {
                    if(item.getGrpempId().equals(oEmpleadoModel.getEmpGrpEmpresarialId())){
                        spGrupoEmpresarialEmployee.setSelection(i);
                        return;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    public void setLocalesAdapter(){
        if(edicion){
            if(SessionManager.getListadoLocales().size() > 0){
                int i = 0;
                for (LocalModel item: SessionManager.getListadoLocales()) {
                    if(item.getLocalId()==oEmpleadoModel.getEmpLocalId()){
                        spLocalEmployee.setSelection(i);
                        return;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    public void setEstadoCivilAdapter(){
        if(edicion){
            int i = 0;
            for (Enumeraciones.EstadoCivil item: Enumeraciones.EstadoCivil.values()) {
                if(item.getIdEstadoCivil().equals(oEmpleadoModel.getEmpEstadoCivil())){
                    spEstadoCivilEmployee.setSelection(i);
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    public void setCategoriaLicenciaAdapter(){
        if(edicion){
            int i = 0;
            for (Enumeraciones.CategoriaLicencia item: Enumeraciones.CategoriaLicencia.values()) {
                if(item.getIdCategoriaLicencia().equals(oEmpleadoModel.getEmpLicenciaCategoriaId())){
                    spCategoriaLicenciaEmployee.setSelection(i);
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    public void setEstadoAdapter(){
        if(edicion){
            int i = 0;
            for (Enumeraciones.Estado item: Enumeraciones.Estado.values()) {
                if(item.getIdEstado().equals(oEmpleadoModel.getEmpEstado())){
                    spEstadoEmployee.setSelection(i);
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    private void GrabarEmpleado(){
        RequestRegistroEmpleado oRequestRegistroEmpleado=new RequestRegistroEmpleado();
        oRequestRegistroEmpleado.setEmpNombre(oEmpleadoModel.getEmpNombre());
        oRequestRegistroEmpleado.setEmpApellidos(oEmpleadoModel.getEmpApellidos());
        oRequestRegistroEmpleado.setEmpTipoDocumentoId(oEmpleadoModel.getEmpTipoDocumentoId());
        oRequestRegistroEmpleado.setEmpNumeroDocumento(oEmpleadoModel.getEmpNumeroDocumento());
        oRequestRegistroEmpleado.setEmpEstadoCivil(oEmpleadoModel.getEmpEstadoCivil());
        oRequestRegistroEmpleado.setEmpFechaNacimiento(oEmpleadoModel.getEmpFechaNacimiento());
        oRequestRegistroEmpleado.setEmpNacionalidad(oEmpleadoModel.getEmpNacionalidad());
        oRequestRegistroEmpleado.setEmpCargoId(oEmpleadoModel.getEmpCargoId());
        oRequestRegistroEmpleado.setEmpLicenciaCategoriaId(oEmpleadoModel.getEmpLicenciaCategoriaId());
        oRequestRegistroEmpleado.setEmpNumeroLicencia(oEmpleadoModel.getEmpNumeroLicencia());
        oRequestRegistroEmpleado.setEmpEstado(oEmpleadoModel.getEmpEstado());
        oRequestRegistroEmpleado.setEmpTipoServicioId(oEmpleadoModel.getEmpTipoServicioId());
        oRequestRegistroEmpleado.setEmpGrpEmpresarialId(oEmpleadoModel.getEmpGrpEmpresarialId());
        oRequestRegistroEmpleado.setEmpLocalId(oEmpleadoModel.getEmpLocalId());
        ApiClient.getInstance().RegistrarEmpleado(oRequestRegistroEmpleado, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {

                ResponseGeneric<Boolean> data = response.body();
                if (response.isSuccessful()) {
                    if (data.isSuccess()) {
                        Toast.makeText(getBaseContext(), R.string.msg_Save_Employee_OK, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                Log.v(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void ModificarEmpleado(){
        RequestModificaEmpleado oRequestModificaEmpleado=new RequestModificaEmpleado();
        oRequestModificaEmpleado.setEmpId(oEmpleadoModel.getEmpId());
        oRequestModificaEmpleado.setEmpNombre(oEmpleadoModel.getEmpNombre());
        oRequestModificaEmpleado.setEmpApellidos(oEmpleadoModel.getEmpApellidos());
        oRequestModificaEmpleado.setEmpTipoDocumentoId(oEmpleadoModel.getEmpTipoDocumentoId());
        oRequestModificaEmpleado.setEmpNumeroDocumento(oEmpleadoModel.getEmpNumeroDocumento());
        oRequestModificaEmpleado.setEmpEstadoCivil(oEmpleadoModel.getEmpEstadoCivil());
        oRequestModificaEmpleado.setEmpFechaNacimiento(oEmpleadoModel.getEmpFechaNacimiento());
        oRequestModificaEmpleado.setEmpNacionalidad(oEmpleadoModel.getEmpNacionalidad());
        oRequestModificaEmpleado.setEmpCargoId(oEmpleadoModel.getEmpCargoId());
        oRequestModificaEmpleado.setEmpLicenciaCategoriaId(oEmpleadoModel.getEmpLicenciaCategoriaId());
        oRequestModificaEmpleado.setEmpNumeroLicencia(oEmpleadoModel.getEmpNumeroLicencia());
        oRequestModificaEmpleado.setEmpEstado(oEmpleadoModel.getEmpEstado());
        oRequestModificaEmpleado.setEmpTipoServicioId(oEmpleadoModel.getEmpTipoServicioId());
        oRequestModificaEmpleado.setEmpGrpEmpresarialId(oEmpleadoModel.getEmpGrpEmpresarialId());
        oRequestModificaEmpleado.setEmpLocalId(oEmpleadoModel.getEmpLocalId());

        ApiClient.getInstance().ModificarEmpleado(oRequestModificaEmpleado, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {

                ResponseGeneric<Boolean> data = response.body();
                if (response.isSuccessful()) {
                    if (data.isSuccess()) {
                        Toast.makeText(getBaseContext(), R.string.msg_Modific_Employee_OK, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Error de modificación", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                Log.v(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private boolean ValidarFormEmpleado(){
        boolean estado=true;

        //Validar Nombre
        if(oEmpleadoModel.getEmpNombre().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese el nombre del empleado.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.nombresApellidos,oEmpleadoModel.getEmpNombre())){
                Toast.makeText(getBaseContext(), "Ingrese un nombre de empleado válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        //Validar Apellidos
        if(oEmpleadoModel.getEmpApellidos().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese los apellidos del empleado.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.nombresApellidos,oEmpleadoModel.getEmpApellidos())){
                Toast.makeText(getBaseContext(), "Ingrese un apellido de empleado válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        //Validar Numeros de Documento
        if(oEmpleadoModel.getEmpNumeroDocumento().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese el número de documento del empleado.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.numeroDocumento,oEmpleadoModel.getEmpNumeroDocumento())){
                Toast.makeText(getBaseContext(), "Ingrese un número de documento de empleado válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        //Validar Numero de Licencia
        if(oEmpleadoModel.getEmpNumeroLicencia().isEmpty()){
            Toast.makeText(getBaseContext(), "Ingrese la licencia del empleado.", Toast.LENGTH_LONG).show();
            estado=false;
        }else{
            if(!Functions.ValidaRegExp(ExpReg.numeroDocumento,oEmpleadoModel.getEmpNumeroLicencia())){
                Toast.makeText(getBaseContext(), "Ingrese una licencia de empleado válido.", Toast.LENGTH_LONG).show();
                estado=false;
            }
        }

        return estado;
    }

}