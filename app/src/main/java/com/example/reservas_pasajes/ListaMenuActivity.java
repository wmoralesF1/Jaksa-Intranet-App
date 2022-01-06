package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reservas_pasajes.helper.Functions;
import com.example.reservas_pasajes.helper.SessionManager;
import com.example.reservas_pasajes.helper.adaptadorItinerarios;
import com.example.reservas_pasajes.helper.adaptadorListaMenu;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.MapBusModel;
import com.example.reservas_pasajes.models.MenuModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class ListaMenuActivity extends AppCompatActivity {
    ArrayList<MenuModel> listaOpciones = new ArrayList<>();
    LinearLayout llMenuOpciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_menu);
        listarMenuOpciones();
        GenerarMeunuBotones(2);
    }

    private void listarMenuOpciones(){
        listaOpciones.add(new MenuModel(getResources().getDrawable(R.drawable.ic_manager),"ADMINISTRATIVO","","ADMINISTRATIVO",1,0));
        listaOpciones.add(new MenuModel(getResources().getDrawable(R.drawable.ic_planning),"OPERACIONES","","OPERACIONES",2,0));
        listaOpciones.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sales),"VENTAS","","VENTAS",3,0));
        listaOpciones.add(new MenuModel(getResources().getDrawable(R.drawable.ic_settings),"SISTEMAS","","SISTEMAS",4,0));

    }

    private void GenerarMeunuBotones(int numBotones){
        try {
            //int numFilas=SessionManager.getUsuario().getListaMenu().size()/numBotones;
            int numFilas=listaOpciones.size()/numBotones;
            llMenuOpciones=(LinearLayout) findViewById(R.id.llMenuOpciones);
            GradientDrawable gradiMenu=new GradientDrawable();
            gradiMenu.setCornerRadius(20);
            gradiMenu.setStroke(2, Color.parseColor("#000000"));
            gradiMenu.setColor(Color.parseColor("#FFFFFF"));
            LinearLayout.LayoutParams paramsItem = new LinearLayout.LayoutParams(
                    Functions.getCalcularAncho(Functions.getAnchoPantalla(this),35),
                    Functions.getCalcularAncho(Functions.getAnchoPantalla(this),35)
            );
            paramsItem.setMargins(30,30,30,30);
            boolean newll=true;
            int idItem=(numFilas + 10);
            int idLinearLayout=1;
            int indice=1;
            for (MenuModel item:listaOpciones) {
                LinearLayout llFila;
                Button btnOpcion=new Button(getApplicationContext());

                btnOpcion.setId(idItem);
                btnOpcion.setLayoutParams(paramsItem);
                btnOpcion.setTextColor(Color.parseColor("#000000"));
                btnOpcion.setBackground(gradiMenu);
                btnOpcion.setText(item.getTextoPrimario());
                btnOpcion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navegacion(item);
                    }
                });
                Drawable dr=item.getImagen();
                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                bitmap=Functions.getResizedBitmap(bitmap,
                        Functions.getCalcularAncho(Functions.getAnchoPantalla(this),5),
                        Functions.getCalcularAncho(Functions.getAnchoPantalla(this),5));
                Drawable drOpcion = new BitmapDrawable(getResources(), bitmap);
                btnOpcion.setCompoundDrawablesWithIntrinsicBounds(null, drOpcion, null, null);

                idItem++;
                if(newll==true){
                    llFila=new LinearLayout(getApplicationContext());
                    llFila.setId(idLinearLayout);
                    llFila.setOrientation(LinearLayout.HORIZONTAL);
                    llFila.setGravity(Gravity.CENTER_HORIZONTAL);
                    llFila.addView(btnOpcion);

                    llMenuOpciones.addView(llFila);
                    newll=false;
                }else{
                    llFila=findViewById(idLinearLayout);
                    llFila.addView(btnOpcion);
                }

                if((indice%numBotones)==0){
                    newll=true;
                    idLinearLayout++;
                }
                indice++;
            }

        }
        catch (Exception e)
        {
            Log.i("Hola", "Error " + e.getMessage());
        }



    }

    public void Navegacion(MenuModel item){

        /*WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        Log.i("TAG", "Method 1: height::" + height + "  width::" + width);*/

        if(SessionManager.getUsuario().getListaMenu().get(0).getClave().equals(item.getClave())){
            Intent i;
            i = new Intent(this, SubMenuActivity.class);
            i.putExtra("idMenuPadre", String.valueOf(item.getId()));
            i.putExtra("nomMenuPadre", String.valueOf(item.getTextoPrimario()));
            startActivity(i);
        }else{
            Toast.makeText(getBaseContext(), R.string.msg_Error_Block_Acceso, Toast.LENGTH_LONG).show();
        }


    }

}