package com.example.reservas_pasajes.helper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.reservas_pasajes.CancelReservaActivity;
import com.example.reservas_pasajes.EmployeeActivity;
import com.example.reservas_pasajes.FilterEmployeeActivity;
import com.example.reservas_pasajes.FilterUserActivity;
import com.example.reservas_pasajes.OpenTravelActivity;
import com.example.reservas_pasajes.ProgrammingTravelActivity;
import com.example.reservas_pasajes.R;
import com.example.reservas_pasajes.SearchEditTicket;
import com.example.reservas_pasajes.SearchInspectionBusActivity;
import com.example.reservas_pasajes.SearchProductionBusActivity;
import com.example.reservas_pasajes.SearchRoutesActivity;
import com.example.reservas_pasajes.models.MenuModel;

import java.util.ArrayList;

public class adaptadorListaMenu extends BaseAdapter {
    private ArrayList<MenuModel> listMenu;
    private Context context;
    private LayoutInflater inflater;
    Resources res;
    public adaptadorListaMenu(Context context, ArrayList<MenuModel> listModel) {
        this.context = context;
        this.listMenu = listModel;
        res=context.getResources();
    }

    @Override
    public int getCount() {
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final MenuModel entidad = (MenuModel) getItem(position);

        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_menu, null);
        View v=convertView;

        Button btnItemMenu = (Button) convertView.findViewById(R.id.btnItemMenu);
        GradientDrawable gradiMenu=new GradientDrawable();
        gradiMenu.setCornerRadius(20);

        Drawable dr=res.getDrawable(R.drawable.ic_next);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable drBtnNext = new BitmapDrawable(res, Bitmap.createScaledBitmap(bitmap, 45, 45, true));
        btnItemMenu.setCompoundDrawablesWithIntrinsicBounds(null, null, drBtnNext, null);
        btnItemMenu.setTextColor(Color.parseColor("#EECD00"));
        btnItemMenu.setBackground(gradiMenu);
        btnItemMenu.setText(entidad.getTextoPrimario());
        btnItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navegacion(entidad);
            }
        });
        return convertView;
    }

    public void Navegacion(MenuModel item){

        if(item.getClave()=="RESERVA_PASAJES"){
            Intent i;
            i = new Intent(context, SearchRoutesActivity.class);
            context.startActivity(i);
        }else if(item.getClave()=="ANULACION_RESERVAS"){
            Intent i;
            i = new Intent(context, CancelReservaActivity.class);
            context.startActivity(i);
        }else if(item.getClave()=="PROGRAMACION_VIAJE"){
            Intent i;
            i = new Intent(context, OpenTravelActivity.class);
            context.startActivity(i);
        }else if(item.getClave()=="EDICION_BOLETO"){
            Intent i;
            i = new Intent(context, SearchEditTicket.class);
            context.startActivity(i);
        }else if(item.getClave()=="PRODUCCION_BUSES"){
            Intent i;
            i = new Intent(context, SearchProductionBusActivity.class);
            context.startActivity(i);
        }else if(item.getClave()=="EMPLEADOS"){
            Intent i;
            i = new Intent(context, FilterEmployeeActivity.class);
            context.startActivity(i);
        }else if(item.getClave()=="USUARIOS"){
            Intent i;
            i = new Intent(context, FilterUserActivity.class);
            context.startActivity(i);
        }else if(item.getClave()=="INSPECCION_BUSES"){
            Intent i;
            i = new Intent(context, SearchInspectionBusActivity.class);
            context.startActivity(i);
        }


    }
}
