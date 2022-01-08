package com.example.reservas_pasajes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.models.AsientoModel;
import com.example.reservas_pasajes.models.MapBusModel;
import com.example.reservas_pasajes.models.PasajeroModel;

import java.util.ArrayList;

public class MapBusActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MapBusModel> planoBus=new ArrayList<>();

    TextView tvDescRutaMapBus;
    TextView tvFechaHoraReservaMapBus;
    TextView tvServicioMapBus;
    TextView tvPrecioMapBus;
    Button btnPreviousMapBus;
    Button btnNextMapBus;
    LinearLayout llplanobus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_bus);
        DetalleReserva();
        btnPreviousMapBus=findViewById(R.id.btnAtrasMapBus);
        btnNextMapBus=findViewById(R.id.btnContinuarMapBus);
        btnPreviousMapBus.setOnClickListener(this);
        btnNextMapBus.setOnClickListener(this);

        Drawable drIconPrevious = getResources().getDrawable(R.drawable.ic_previous);
        Drawable drIconNext = getResources().getDrawable(R.drawable.ic_next);
        Bitmap bitmapPrevious = ((BitmapDrawable) drIconPrevious).getBitmap();
        Bitmap bitmapNext = ((BitmapDrawable) drIconNext).getBitmap();
        Drawable drwPrevious = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapPrevious, 30, 30, true));
        Drawable drwNext = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapNext, 30, 30, true));
        btnPreviousMapBus.setCompoundDrawablesWithIntrinsicBounds(drwPrevious, null, null, null);
        btnNextMapBus.setCompoundDrawablesWithIntrinsicBounds(null, null, drwNext, null);
        GenerarPlaniBus();
    }

    private void DetalleReserva(){
        tvDescRutaMapBus=findViewById(R.id.tvDescRutaMapBus);
        tvFechaHoraReservaMapBus=findViewById(R.id.tvFechaHoraReservaMapBus);
        tvServicioMapBus=findViewById(R.id.tvServicioMapBus);
        tvPrecioMapBus=findViewById(R.id.tvPrecioMapBus);

        tvFechaHoraReservaMapBus.setText(SessionManager.getTurnoViaje().getFechaReserva() + " - " + SessionManager.getTurnoViaje().getHoraReserva());
        tvServicioMapBus.setText(SessionManager.getTurnoViaje().getNomServicio());
        if(SessionManager.getTurnoViaje().getRutaViaje()!=null){
            tvDescRutaMapBus.setText(SessionManager.getTurnoViaje().getRutaViaje().getRutaDescripcion());
            tvPrecioMapBus.setText("S/ " + String.valueOf(SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio()));
        }
    }

    private void ListarPlanoBus(){
        int asientoExtrasPrimerPiso=SessionManager.getTurnoViaje().getAsientosBusPrimerPiso() % 4;
        int filasAsientosPrimerPiso=SessionManager.getTurnoViaje().getAsientosBusPrimerPiso() / 4;
        int a,b,c,d,e;
        int w,x,y,z;
        a=1;
        b=2;
        c=3;
        d=4;
        e=5;
        w=1;
        x=2;
        y=3;
        z=4;
        for (int i = 1; i < filasAsientosPrimerPiso; i++){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(y)));
            a+=5;
            b+=5;
            c+=5;
            d+=5;
            e+=5;

            w+=4;
            x+=4;
            y+=4;
            z+=4;
        }

        if(asientoExtrasPrimerPiso==0){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(y)));
        }
        else if(asientoExtrasPrimerPiso==1){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("AP",c,String.valueOf(x+1),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x+1)));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(x+2),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x+2)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(x+3),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x+3)));
        }
        else if(asientoExtrasPrimerPiso==2){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(y)));
            a+=5;
            b+=5;
            c+=5;
            d+=5;
            e+=5;

            w+=4;
            x+=4;
            y+=4;
            z+=4;
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("VA",d,"00",1,0,0,0));
            planoBus.add(new MapBusModel("VA",e,"00",1,0,0,0));
         }
        else if(asientoExtrasPrimerPiso==3){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(y)));
            a+=5;
            b+=5;
            c+=5;
            d+=5;
            e+=5;

            w+=4;
            x+=4;
            y+=4;
            z+=4;
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,0));
            planoBus.add(new MapBusModel("VA",d,"00",1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,0));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(x+1),1,SessionManager.getTurnoViaje().getRutaViaje().getRutaPrecio(),0,ValidaAsientoOcupado(x+1)));
        }




    }

    private void GenerarPlaniBus(){
        ListarPlanoBus();
        if((planoBus.size()%5)==0){
            llplanobus=(LinearLayout) findViewById(R.id.llplanobus);
            int numFilas=(planoBus.size()/5);

            LinearLayout.LayoutParams paramsItem = new LinearLayout.LayoutParams(
                    Functions.getCalcularAncho(Functions.getAnchoPantalla(this),12),
                    Functions.getCalcularAncho(Functions.getAnchoPantalla(this),12)
            );
            paramsItem.setMargins(20,20,20,20);
            Drawable paramsImage=null;

            GradientDrawable gradiEspacio=new GradientDrawable();
            GradientDrawable gradiLibre=new GradientDrawable();
            GradientDrawable gradiOcupado=new GradientDrawable();
            GradientDrawable gradiSeleccionado=new GradientDrawable();

            gradiLibre.setCornerRadius(20);
            gradiOcupado.setCornerRadius(20);
            gradiSeleccionado.setCornerRadius(20);

            gradiLibre.setStroke(2,Color.parseColor("#000000"));
            gradiOcupado.setStroke(2,Color.parseColor("#000000"));
            gradiSeleccionado.setStroke(2,Color.parseColor("#000000"));

            gradiEspacio.setColor(Color.parseColor("#FFFFFF"));
            gradiLibre.setColor(Color.parseColor("#FFFFFF"));
            gradiOcupado.setColor(Color.parseColor("#909090"));
            gradiSeleccionado.setColor(Color.parseColor("#FFEB3B"));

            int idItem=(numFilas + 10);
            int idLinearLayout=1;
            boolean newll=true;
            boolean boton=false;
            for (MapBusModel item:planoBus){
                LinearLayout llFila;
                Button btnAsiento=new Button(getApplicationContext());
                ImageView ivItem=new ImageView(getApplicationContext());

                if(item.getTipo()=="AP" || item.getTipo()=="VA" || item.getTipo()=="LI"){

                    btnAsiento.setId(idItem);
                    btnAsiento.setLayoutParams(paramsItem);
                    boton=true;
                    if(item.getTipo()=="VA" || item.getTipo()=="LI"){
                        btnAsiento.setBackground(gradiEspacio);
                        btnAsiento.setEnabled(false);
                    }else{

                        btnAsiento.setText(item.getNumAsiento());
                        if(item.getEstado()==1){
                            btnAsiento.setBackground(gradiLibre);
                            //btnAsiento.setOnClickListener();
                        }else if(item.getEstado()==2){
                            btnAsiento.setBackground(gradiSeleccionado);
                        }else{
                            btnAsiento.setTextColor(Color.parseColor("#FFFFFF"));
                            btnAsiento.setBackground(gradiOcupado);
                            btnAsiento.setEnabled(false);
                        }

                        if(item.getEstado()==2 || item.getEstado()==0){
                            Drawable dr = null;
                            if(item.getGenero()==1){
                                dr = getResources().getDrawable(R.drawable.man);
                            }else if(item.getGenero()==2){
                                dr = getResources().getDrawable(R.drawable.woman);

                            }else if(item.getGenero()==3){
                                dr = getResources().getDrawable(R.drawable.kid);
                            }else if(item.getGenero()==4){
                                dr = getResources().getDrawable(R.drawable.girl);
                            }

                            /*if(item.getGenero()!=0){
                                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                                Drawable drPeople = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 35, 35, true));
                                btnAsiento.setCompoundDrawablesWithIntrinsicBounds(drPeople, null, null, null);
                            }*/
                        }
                    }
                }


                idItem++;
                if(newll==true){
                    llFila=new LinearLayout(getApplicationContext());
                    llFila.setId(idLinearLayout);
                    llFila.setOrientation(LinearLayout.HORIZONTAL);
                    llFila.setGravity(Gravity.CENTER_HORIZONTAL);
                    if(boton){
                        llFila.addView(btnAsiento);
                    }else{
                        llFila.addView(ivItem);
                    }

                    llplanobus.addView(llFila);
                    newll=false;
                }else{
                    llFila=findViewById(idLinearLayout);
                    if(boton){
                        llFila.addView(btnAsiento);
                    }else{
                        llFila.addView(ivItem);
                    }
                }

                if((item.getIndice()%5)==0){
                    newll=true;
                    idLinearLayout++;
                }
                btnAsiento.setTag(item);
                btnAsiento.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v.getId()==btnAsiento.getId()){
                            final MapBusModel itemMapBusModel = (MapBusModel) btnAsiento.getTag();
                            if(itemMapBusModel.getEstado()==1){
                                if(BloquearAsiento(itemMapBusModel)){
                                    Toast.makeText(getBaseContext(), "Se bloqueo el asiento.", Toast.LENGTH_LONG).show();
                                    itemMapBusModel.setEstado(2);
                                    btnAsiento.setBackground(gradiSeleccionado);
                                    btnAsiento.setTag(itemMapBusModel);
                                }

                            }else if(itemMapBusModel.getEstado()==2){
                                if(LiberarAsiento(itemMapBusModel)){
                                    Toast.makeText(getBaseContext(), "Se libero el asiento.", Toast.LENGTH_LONG).show();
                                    itemMapBusModel.setEstado(1);
                                    btnAsiento.setBackground(gradiLibre);
                                    btnAsiento.setTag(itemMapBusModel);
                                }
                            }

                        }

                    }
                });
            }
        }



    }

    private void Continuar(){
        Intent i;
        i = new Intent(this, PassengerActivity.class);
        startActivity(i);
    }

    private void Atras(){
        Intent i;
        i = new Intent(this, SelectRouteActivity.class);
        startActivity(i);
    }

    private int ValidaAsientoOcupado(int NroAsiento){
        int ocupado=1;
        for (AsientoModel item:SessionManager.getTurnoViaje().getListaAsientosOcupados()){
            if(item.getNroAsiento()==NroAsiento){
                ocupado=0;
                break;
            }
        }
        return ocupado;
    }

    private boolean BloquearAsiento(MapBusModel itemMapBusModel){
        PasajeroModel oPasajeroModel=new PasajeroModel();
        oPasajeroModel.setNumAsiento(itemMapBusModel.getNumAsiento());
        oPasajeroModel.setPrecioAsiento(itemMapBusModel.getPrecio());
        ArrayList<PasajeroModel> listaPasajero= SessionManager.getTurnoViaje().getListaPasajeros();
        if(listaPasajero==null){
            listaPasajero=new ArrayList<>();
        }
        listaPasajero.add(oPasajeroModel);
        SessionManager.getTurnoViaje().setListaPasajeros(null);
        SessionManager.getTurnoViaje().setListaPasajeros(listaPasajero);
        if(SessionManager.getTurnoViaje().getListaPasajeros().size()>0){
            btnNextMapBus.setEnabled(true);
        }

        return true;
    }

    private boolean LiberarAsiento(MapBusModel itemMapBusModel){
        ArrayList<PasajeroModel> listaPasajero= SessionManager.getTurnoViaje().getListaPasajeros();
        for(int i=0;i<=listaPasajero.size()-1;i++){
            if(Integer.parseInt(listaPasajero.get(i).getNumAsiento())==Integer.parseInt(itemMapBusModel.getNumAsiento())){
                listaPasajero.remove(i);
                break;
            }
        }
        SessionManager.getTurnoViaje().setListaPasajeros(null);
        SessionManager.getTurnoViaje().setListaPasajeros(listaPasajero);
        if(SessionManager.getTurnoViaje().getListaPasajeros().size()<1){
            btnNextMapBus.setEnabled(false);
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==btnPreviousMapBus.getId()){
            Atras();
        }else if(v.getId()==btnNextMapBus.getId()){
            Continuar();
        }
    }
}