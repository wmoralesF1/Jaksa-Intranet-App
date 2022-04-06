package com.example.reservas_pasajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reservas_pasajes.helper.adaptadorListaMenu;
import com.example.reservas_pasajes.models.MenuModel;

import java.util.ArrayList;

public class SubMenuActivity extends AppCompatActivity {
    private ListView lvSubMenu;
    private adaptadorListaMenu adaptador;
    ArrayList<MenuModel> listaSubMenu = new ArrayList<>();
    ArrayList<MenuModel> listaOpciones = new ArrayList<>();
    MenuModel vSeleccion;
    private int idMenuPadre;
    private String nomMenuPadre;
    TextView tvTituloMenuPadre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);
        Bundle bundle = getIntent().getExtras();
        idMenuPadre = Integer.parseInt(bundle.getString("idMenuPadre"));
        nomMenuPadre = bundle.getString("nomMenuPadre").toString();
        CargarSubMenu();
        CargarOpciones();
        tvTituloMenuPadre=(TextView) findViewById(R.id.tvTituloMenuPadre);
        tvTituloMenuPadre.setText(nomMenuPadre);
        lvSubMenu=(ListView)findViewById(R.id.lvSubMenu);

        adaptador = new adaptadorListaMenu(this, listaOpciones);
        lvSubMenu.setAdapter(adaptador);
    }

    public void CargarSubMenu() {

        listaSubMenu = new ArrayList<MenuModel>();

        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "AUTORIZACION de OC's","","AUTORIZACION_OC",5,
                1));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "CUENTAS por PAGAR","","CUENTAS_PAGAR",6,
                1));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "CONTROL de PRESUPUESTO","","CONTROL_PRESUPUESTO",7,
                1));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "FLUJO de CAJA","","FLUJO_CAJA",8,
                1));
        /*listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "PROGRAMACION de VIAJES","","PROGRAMACION_VIAJES",9,
                2));*/
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "PROGRAMACION de VIAJES","","PROGRAMACION_VIAJE",10,
                2));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "RESERVA de PASAJES","","RESERVA_PASAJES",11,
                3));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "ANULACION de RESERVAS","","ANULACION_RESERVAS",12,
                3));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "CUENTAS por COBRAR","","CUENTAS_COBRAR",13,
                3));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "ADMINISTRACION de USUARIOS","","ADMINISTRACION_USUARIOS",14,
                4));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "ANULAR VENTAS","","ANULAR_VENTAS",15,
                4));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "CAMBIOS en RQ","","CAMBIOS_RQ",16,
                4));



    }

    public void CargarOpciones(){
        listaOpciones=new ArrayList<MenuModel>();
        for (MenuModel item:listaSubMenu) {
            if(item.getIdMenuPadre()==idMenuPadre){
                listaOpciones.add(item);
            }
        }
    }
}