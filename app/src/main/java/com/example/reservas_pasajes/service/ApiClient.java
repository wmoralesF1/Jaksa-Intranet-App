package com.example.reservas_pasajes.service;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.models.CargoModel;
import com.example.reservas_pasajes.models.EmpleadoModel;
import com.example.reservas_pasajes.models.GrupoEmpresarialModel;
import com.example.reservas_pasajes.models.LocalModel;
import com.example.reservas_pasajes.models.PaisModel;
import com.example.reservas_pasajes.models.TipoDocumentoModel;
import com.example.reservas_pasajes.models.TipoServicioModel;
import com.example.reservas_pasajes.service.request.RequestFiltroEmpleado;
import com.example.reservas_pasajes.service.request.RequestModificaEmpleado;
import com.example.reservas_pasajes.service.request.RequestRegistroEmpleado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient  extends AppCompatActivity {
    public static ApiClient instance = null;

    private static ApiService service;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.grupovaldez.pe/ServicesAppIntranet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service =  retrofit.create(ApiService.class);
    }
    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public void ListarCargos(Callback<ResponseListGeneric<CargoModel>> callback ){
        Call<ResponseListGeneric<CargoModel>> response = service.ListarCargos();
        response.enqueue(callback);
    }

    public void ListarGruposEmpresariales(Callback<ResponseListGeneric<GrupoEmpresarialModel>> callback ){
        Call<ResponseListGeneric<GrupoEmpresarialModel>> response = service.ListarGruposEmpresariales();
        response.enqueue(callback);
    }

    public void ListarLocales(Callback<ResponseListGeneric<LocalModel>> callback ){
        Call<ResponseListGeneric<LocalModel>> response = service.ListarLocales();
        response.enqueue(callback);
    }

    public void ListarTipoDocumentoIdentidad(Callback<ResponseListGeneric<TipoDocumentoModel>> callback ){
        Call<ResponseListGeneric<TipoDocumentoModel>> response = service.ListarTipoDocumentoIdentidad();
        response.enqueue(callback);
    }

    public void ListarTiposServicios(Callback<ResponseListGeneric<TipoServicioModel>> callback ){
        Call<ResponseListGeneric<TipoServicioModel>> response = service.ListarTiposServicios();
        response.enqueue(callback);
    }

    public void ListarPaises(Callback<ResponseListGeneric<PaisModel>> callback ){
        Call<ResponseListGeneric<PaisModel>> response = service.ListarPaises();
        response.enqueue(callback);
    }

    //Empleado

    public void ObtenerEmpleado(int id, Callback<ResponseGeneric<EmpleadoModel>> callback){
        Call<ResponseGeneric<EmpleadoModel>> response = service.obtenerEmpleado(id);
        response.enqueue(callback);
    }

    public void BuscarEmpleados(RequestFiltroEmpleado requestEmpleado, Callback<ResponseListGeneric<EmpleadoModel>> callback ){
        Call<ResponseListGeneric<EmpleadoModel>> response = service.buscarEmpleados(requestEmpleado);
        response.enqueue(callback);
    }

    public void RegistrarEmpleado(RequestRegistroEmpleado request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.registrarEmpleado(request);
        response.enqueue(callback);
    }

    public void ModificarEmpleado(RequestModificaEmpleado request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.modificarEmpleado(request);
        response.enqueue(callback);
    }

}
