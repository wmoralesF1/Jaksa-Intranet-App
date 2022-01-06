package com.example.reservas_pasajes.helper;

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



}
