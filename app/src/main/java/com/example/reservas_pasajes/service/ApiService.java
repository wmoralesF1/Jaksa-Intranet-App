package com.example.reservas_pasajes.service;

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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("api/Charges/GetAllAsyn")
    Call<ResponseListGeneric<CargoModel>> ListarCargos();

    @GET("api/CompanyGroups/GetAllAsyn")
    Call<ResponseListGeneric<GrupoEmpresarialModel>> ListarGruposEmpresariales();

    @GET("api/Locals/GetAllAsyn")
    Call<ResponseListGeneric<LocalModel>> ListarLocales();

    @GET("api/TypeDocuments/GetAllAsyn")
    Call<ResponseListGeneric<TipoDocumentoModel>> ListarTipoDocumentoIdentidad();

    @GET("api/TypeServices/GetAllAsyn")
    Call<ResponseListGeneric<TipoServicioModel>> ListarTiposServicios();

    @GET("api/Ubigeos/GetAllAsyn")
    Call<ResponseListGeneric<PaisModel>> ListarPaises();

    //Empleado
    @GET("api/Employees/GetById/{emp_id}")
    Call<ResponseGeneric<EmpleadoModel>> obtenerEmpleado(@Path("emp_id") int idEmpleado );

    @POST("api/Employees/GetFilter")
    @Headers("Content-Type: application/json")
    Call<ResponseListGeneric<EmpleadoModel>> buscarEmpleados(@Body RequestFiltroEmpleado requestEmpleado);

    @POST("api/Employees/Save")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> registrarEmpleado(@Body RequestRegistroEmpleado requestEmpleado);

    @PUT("api/Employees/Modify")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> modificarEmpleado(@Body RequestModificaEmpleado requestEmpleado);



}

