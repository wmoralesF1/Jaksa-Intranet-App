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
        lvSubMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vSeleccion= (MenuModel) adapterView.getItemAtPosition(i);
                //String Opcion = vSeleccion.getClave();
                Navegacion(vSeleccion);
            }
        });
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
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "APERTURA de SERVICIOS/VIAJES","","APERTURA_SERVICIOS_VIAJES",9,
                2));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "POSICION de UNIDADES","","POSICION_UNIDADES",10,
                2));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "ESTADOS RQ","","ESTADOS_RQ",11,
                2));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "RESERVA de PASAJES","","RESERVA_PASAJES",12,
                3));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "LISTADO de RESERVAS","","LISTADO_RESERVAS",13,
                3));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "CUENTAS por COBRAR","","CUENTAS_COBRAR",14,
                3));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "ADMINISTRACION de USUARIOS","","ADMINISTRACION_USUARIOS",15,
                4));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "ANULAR VENTAS","","ANULAR_VENTAS",16,
                4));
        listaSubMenu.add(new MenuModel(getResources().getDrawable(R.drawable.ic_sale),
                "CAMBIOS en RQ","","CAMBIOS_RQ",17,
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

    public void Navegacion(MenuModel item){
        Intent i;

        switch (item.getClave())
        {
            case "RESERVA_PASAJES":
                i = new Intent(this, SearchRoutesActivity.class);
                startActivity(i);
                break;
            case "LISTADO_RESERVAS":
                i = new Intent(this, ReservationListActivity.class);
                startActivity(i);
                break;
        }

    }
}