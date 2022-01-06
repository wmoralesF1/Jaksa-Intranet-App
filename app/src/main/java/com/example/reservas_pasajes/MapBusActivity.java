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

import java.util.ArrayList;

public class MapBusActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MapBusModel> planoBus=new ArrayList<>();
    ArrayList<MapBusModel> planoBusPrimerPiso=new ArrayList<>();
    ArrayList<MapBusModel> planoBusSegundoPiso=new ArrayList<>();

    TextView tvDescRuta;
    TextView tvFechaHoraReserva;
    TextView tvServicio;
    TextView tvPrecio;
    Button btnPrevious;
    Button btnNext;
    LinearLayout llplanobus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_bus);
        tvDescRuta=findViewById(R.id.tvDescRuta);
        tvFechaHoraReserva=findViewById(R.id.tvFechaHoraReserva);
        tvServicio=findViewById(R.id.tvServicio);
        tvPrecio=findViewById(R.id.tvPrecio);

        tvDescRuta.setText(SessionManager.getViaje().getItinerarioViaje().getRutaViaje().getRutaDescripcion());
        tvFechaHoraReserva.setText(SessionManager.getViaje().getFechaReservaFormat() + " - " + SessionManager.getViaje().getItinerarioViaje().getHoraReserva());
        tvServicio.setText(SessionManager.getViaje().getItinerarioViaje().getNomServicio());
        tvPrecio.setText("S/ " + String.valueOf(SessionManager.getViaje().getPrecioAsiento()));
        btnPrevious=findViewById(R.id.btnAtras);
        btnNext=findViewById(R.id.btnContinuar);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        Drawable drIconPrevious = getResources().getDrawable(R.drawable.ic_previous);
        Drawable drIconNext = getResources().getDrawable(R.drawable.ic_next);
        Bitmap bitmapPrevious = ((BitmapDrawable) drIconPrevious).getBitmap();
        Bitmap bitmapNext = ((BitmapDrawable) drIconNext).getBitmap();
        Drawable drwPrevious = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapPrevious, 30, 30, true));
        Drawable drwNext = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapNext, 30, 30, true));
        btnPrevious.setCompoundDrawablesWithIntrinsicBounds(drwPrevious, null, null, null);
        btnNext.setCompoundDrawablesWithIntrinsicBounds(null, null, drwNext, null);

        //BuscarPlanoBus(1);
        //CargarPlanoBus();
        GenerarPlaniBus();
    }

    private void ListarPlanoBus(){
        int asientoExtrasPrimerPiso=SessionManager.getViaje().getItinerarioViaje().getAsientosBusPrimerPiso() % 4;
        int filasAsientosPrimerPiso=SessionManager.getViaje().getItinerarioViaje().getAsientosBusPrimerPiso() / 4;
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
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(y)));
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
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(y)));
        }
        else if(asientoExtrasPrimerPiso==1){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("AP",c,String.valueOf(x+1),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x+1)));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(x+2),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x+2)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(x+3),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x+3)));
        }
        else if(asientoExtrasPrimerPiso==2){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(y)));
            a+=5;
            b+=5;
            c+=5;
            d+=5;
            e+=5;

            w+=4;
            x+=4;
            y+=4;
            z+=4;
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("VA",d,"00",1,0,0,0));
            planoBus.add(new MapBusModel("VA",e,"00",1,0,0,0));
         }
        else if(asientoExtrasPrimerPiso==3){
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,0,0,0));
            planoBus.add(new MapBusModel("AP",d,String.valueOf(z),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(z)));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(y),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(y)));
            a+=5;
            b+=5;
            c+=5;
            d+=5;
            e+=5;

            w+=4;
            x+=4;
            y+=4;
            z+=4;
            planoBus.add(new MapBusModel("AP",a,String.valueOf(w),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(w)));
            planoBus.add(new MapBusModel("AP",b,String.valueOf(x),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x)));
            planoBus.add(new MapBusModel("VA",c,"00",1,SessionManager.getViaje().getPrecioAsiento(),0,0));
            planoBus.add(new MapBusModel("VA",d,"00",1,SessionManager.getViaje().getPrecioAsiento(),0,0));
            planoBus.add(new MapBusModel("AP",e,String.valueOf(x+1),1,SessionManager.getViaje().getPrecioAsiento(),0,ValidaAsientoOcupado(x+1)));
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
                                itemMapBusModel.setEstado(2);
                                btnAsiento.setBackground(gradiSeleccionado);
                                btnAsiento.setTag(itemMapBusModel);
                                Toast.makeText(getBaseContext(), "Se bloqueo el asiento.", Toast.LENGTH_LONG).show();
                            }else if(itemMapBusModel.getEstado()==2){
                                itemMapBusModel.setEstado(1);
                                btnAsiento.setBackground(gradiLibre);
                                btnAsiento.setTag(itemMapBusModel);
                            }

                        }

                    }
                });
            }
        }



    }

    private void BuscarPlanoBus(int piso){
        if(piso==1){
            planoBus.add(new MapBusModel("AP",1,"01",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",2,"02",piso,20,2,0));
            planoBus.add(new MapBusModel("VA",3,"03",piso,20,1,1));
            planoBus.add(new MapBusModel("PU",4,"04",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",5,"05",piso,20,3,2));
            planoBus.add(new MapBusModel("AP",6,"06",piso,20,0,1));
            planoBus.add(new MapBusModel("AP",7,"07",piso,20,4,0));
            planoBus.add(new MapBusModel("VA",8,"08",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",9,"09",piso,20,0,1));
            planoBus.add(new MapBusModel("AP",10,"10",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",11,"11",piso,20,1,1));
            planoBus.add(new MapBusModel("TV",12,"12",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",13,"13",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",14,"14",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",15,"15",piso,20,1,2));
            planoBus.add(new MapBusModel("ES",16,"16",piso,20,1,1));
            planoBus.add(new MapBusModel("ES",17,"17",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",18,"18",piso,20,1,1));
            planoBus.add(new MapBusModel("BA",19,"19",piso,20,1,1));
            planoBus.add(new MapBusModel("BA",20,"20",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",21,"21",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",22,"22",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",23,"23",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",24,"24",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",25,"25",piso,20,1,2));
            planoBus.add(new MapBusModel("AP",26,"26",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",27,"27",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",28,"28",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",29,"29",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",30,"30",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",31,"31",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",32,"32",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",33,"33",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",34,"34",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",35,"35",piso,20,1,2));
            planoBus.add(new MapBusModel("AP",36,"36",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",37,"37",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",38,"38",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",39,"39",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",40,"40",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",41,"41",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",42,"42",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",43,"43",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",44,"44",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",45,"45",piso,20,1,2));
            planoBus.add(new MapBusModel("AP",46,"46",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",47,"47",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",48,"48",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",49,"49",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",50,"50",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",51,"51",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",52,"52",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",53,"53",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",54,"54",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",55,"55",piso,20,1,2));
            planoBus.add(new MapBusModel("AP",56,"56",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",57,"57",piso,20,1,0));
            planoBus.add(new MapBusModel("VA",58,"58",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",59,"59",piso,20,1,1));
            planoBus.add(new MapBusModel("AP",60,"60",piso,20,1,1));
        }else{
            planoBus.add(new MapBusModel("AP",61,"61",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",62,"62",piso,30,1,0));
            planoBus.add(new MapBusModel("AP",63,"63",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",64,"64",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",65,"65",piso,30,1,2));
            planoBus.add(new MapBusModel("AP",66,"66",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",67,"67",piso,30,1,0));
            planoBus.add(new MapBusModel("AP",68,"68",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",69,"69",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",70,"70",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",71,"71",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",72,"72",piso,30,1,0));
            planoBus.add(new MapBusModel("AP",73,"73",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",74,"74",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",75,"75",piso,30,1,2));
            planoBus.add(new MapBusModel("AP",76,"76",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",77,"77",piso,30,1,0));
            planoBus.add(new MapBusModel("AP",78,"78",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",79,"79",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",80,"80",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",81,"81",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",82,"82",piso,30,1,0));
            planoBus.add(new MapBusModel("AP",83,"83",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",84,"84",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",85,"85",piso,30,1,2));
            planoBus.add(new MapBusModel("AP",86,"86",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",87,"87",piso,30,1,0));
            planoBus.add(new MapBusModel("AP",88,"88",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",89,"89",piso,30,1,1));
            planoBus.add(new MapBusModel("AP",90,"90",piso,30,1,1));
        }

    }

    private void CargarPlanoBus(){

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

                if(item.getTipo()=="AP" || item.getTipo()=="VA"){

                    btnAsiento.setId(idItem);
                    btnAsiento.setLayoutParams(paramsItem);
                    boton=true;
                    if(item.getTipo()=="VA"){
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
                }else{
                    if(item.getTipo()=="PU"){
                        paramsImage=getResources().getDrawable(R.drawable.door);
                    }else if(item.getTipo()=="BA"){
                        paramsImage=getResources().getDrawable(R.drawable.toilet);
                    }
                    else if(item.getTipo()=="ES"){
                        paramsImage=getResources().getDrawable(R.drawable.stairs);
                    }
                    else if(item.getTipo()=="TV"){
                        paramsImage=getResources().getDrawable(R.drawable.tv);
                    }
                    ivItem.setId(idItem);
                    ivItem.setLayoutParams(paramsItem);
                    ivItem.setImageDrawable(paramsImage);
                    boton=false;
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
        i = new Intent(this, SearchRoutesActivity.class);
        startActivity(i);
    }

    private int ValidaAsientoOcupado(int NroAsiento){
        int ocupado=1;
        for (AsientoModel item:SessionManager.getViaje().getItinerarioViaje().getListaAsientosOcupados()){
            if(item.getNroAsiento()==NroAsiento){
                ocupado=0;
                break;
            }
        }
        return ocupado;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnPrevious.getId()){
            Atras();
        }else if(v.getId()==btnNext.getId()){
            Continuar();
        }
    }
}