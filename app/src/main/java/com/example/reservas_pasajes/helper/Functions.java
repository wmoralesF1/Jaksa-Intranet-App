package com.example.reservas_pasajes.helper;

import static java.lang.String.format;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.reservas_pasajes.ListaMenuActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Functions {
    public static int getAnchoPantalla(Activity appView){
        int ancho=350;
        try {
            WindowManager manager = appView.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        }catch (Exception e)
        {
            Log.i("Hola", "Error " + e.getMessage());
        }

        return ancho;
    }

    public static int getAltoPantalla(Activity appView){
        int alto=350;
        try {
            WindowManager manager = appView.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            alto= outMetrics.heightPixels;
        }catch (Exception e)
        {
            Log.i("Hola", "Error " + e.getMessage());
        }

        return alto;
    }

    public static int getCalcularAncho(int anchoPantalla,int porcentaje){

        Double ancho=Double.valueOf(anchoPantalla);
        Double porcentajeDecimal=Double.valueOf(Double.valueOf(porcentaje)/100);
        Integer width= new Double (ancho*porcentajeDecimal).intValue();
        return width;
    }

    public static int getCalcularAnchoBitMap(int anchoPantalla,int porcentaje){

        Double ancho=Double.valueOf(anchoPantalla);
        Double porcentajeDecimal=Double.valueOf(Double.valueOf(porcentaje)/100);
        Integer width= new Double (ancho*porcentajeDecimal).intValue();
        return width/2;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
        int width = 1792;
        int height = 1792;
        //int width = bm.getWidth();
        //int height = bm.getHeight();
        Log.i("Hola", "Var ancho Imagen: " + width);
        Log.i("Hola", "Var alto Imagen: " + height);
        float aspect = (float)width / height;
        float scaleWidth = newWidth;
        float scaleHeight = scaleWidth / aspect;// yeah!

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth / width, scaleHeight / height);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();
        return resizedBitmap;
    }



    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        //Redimensionamos
        int width = bm.getWidth();
        int height = bm.getHeight();
        Log.i("Hola", "Var ancho Imagen: " + width);
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        /*
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Log.i("Hola", "Var ancho Imagen: " + width);
        Log.i("Hola", "Var alto Imagen: " + height);
        Log.i("Hola", "Var ancho Escala: " + scaleWidth);
        Log.i("Hola", "Var alto Escala: " + scaleHeight);
        Log.i("Hola", "Var ancho Escala2: " + newWidth);

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();

        Log.i("Hola", "Var New ancho Imagen: " + resizedBitmap.getWidth());
        Log.i("Hola", "Var New alto Imagen: " + resizedBitmap.getHeight());
        return resizedBitmap;*/
    }

    public  static String FechaHoy(){
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        Date dateObj = calendar.getTime();
        String formattedDate = dtf.format(dateObj);
        return formattedDate;
    }

    public  static String Fecha(int day,int month,int year){
        String año=String.valueOf(year);
        String mes=new DecimalFormat("00").format(month);
        String dia=new DecimalFormat("00").format(day);
        String date=dia+"/"+ mes + "/"+ año;
        return date;
    }



    public static int NumeroAñoFechaHoy(){
        Calendar calendar= Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int NumeroMesFechaHoy(){
        Calendar calendar= Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static int NumeroDiaFechaHoy(){
        Calendar calendar= Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatDate(Date date){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String strDate = simpleDateFormat.format(date);
        return strDate;
    }
    public static Date formatStringtoDate(String strDate){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String StringFormatWsDate(String fecha){
        String formatDate="";
        String[] FechaArray=fecha.split("/");
        formatDate=FechaArray[2]+FechaArray[1]+FechaArray[0];
        return formatDate;
    }

    public static String leftzeros(int numero,int zeros){

        return String.format("%0"+String.valueOf(zeros)+"d", numero);
    }

}
