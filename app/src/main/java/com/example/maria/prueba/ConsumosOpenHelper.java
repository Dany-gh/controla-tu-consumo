package com.example.maria.prueba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ConsumosOpenHelper extends SQLiteOpenHelper{

    public static final String LOGTAG = "LOGTAG";

    public static final String NOMBRE_BD = "consumos.bd";
    public static final int VERSION_BD = 1;
    public static final String TABLA = "consumos";
    public static final String COLUMNA_ID = "id";
    public static final String COLUMNA_COSTO = "costo";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLA + " ( " +
            COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMNA_COSTO + " TEXT " + " ) " ;


    public ConsumosOpenHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i(LOGTAG, "La Tabla CONSUMOS ha sido Creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA);
        onCreate(db);
    }

    public String guardar (String costo) {
        String mensaje = "";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put(COLUMNA_COSTO, costo);

        try {
            database.insertOrThrow("consumos.bd", null, contenedor);
            mensaje = "Valor Ingresado Correctamente";
        } catch (SQLException E){
        mensaje = "Valor NO Ingresado";
        }

        return mensaje;
    }


    public ArrayList llenar_lv(){

        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        String q = "SELECT * FROM consumos.bd";
        Cursor registros = database.rawQuery(q, null);

        if (registros.moveToFirst()){

            do{
                lista.add(registros.getString(0));
            }while(registros.moveToNext());
        }
        return lista;
    }

}
