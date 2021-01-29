package com.example.banco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.banco.bd.MiBD;


public class CajeroDAO {

    /**
     * Definimos constante con el nombre de la tabla
     */
    public static final String C_TABLA = "cajeros";
    /**
     * Definimos constantes con el nombre de las columnas de la tabla
     */
    public static final String C_COLUMNA_ID = "_id";
    public static final String C_COLUMNA_DIRECCION = "direccion";
    public static final String C_COLUMNA_LAT = "latitud";
    public static final String C_COLUMNA_LNG = "longitud";
    public static final String C_COLUMNA_ZOOM = "zoom";

    private Context contexto;
    private MiBD miBD;
    private SQLiteDatabase db;
    /**
     * Definimos lista de columnas de la tabla para utilizarla en las consultas a la base de datos
     */
    private String[] columnas = new String[]{C_COLUMNA_ID, C_COLUMNA_DIRECCION, C_COLUMNA_LAT,
            C_COLUMNA_LNG, C_COLUMNA_ZOOM};

    public CajeroDAO(Context context) {
        this.contexto = context;
    }

    public CajeroDAO abrir() {
        miBD = new MiBD(contexto);
        db = miBD.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        miBD.close();
    }

    /**
     * Devuelve un cursor con todas las filas y todas las columnas de la tabla
     */
    public Cursor getCursor() {
        Cursor c = db.query(true, C_TABLA, columnas, null, null, null, null, null, null);
        return c;
    }

    //Devuelve un cursor con el resultado del select
    public Cursor getRegistro(long id){
        String condicion = C_COLUMNA_ID + "=" + id;
        Cursor c = db.query( true, C_TABLA, columnas, condicion, null, null, null, null, null);
        //Nos movemos al primer registro de la consulta
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public long add(ContentValues reg){
        if (db == null)
            abrir();

        return db.insert(C_TABLA, null, reg);
    }

    public long update(ContentValues reg){
        long id = 0;

        if (db == null)
            abrir();

        if (reg.containsKey(C_COLUMNA_ID)){
            id = reg.getAsLong(C_COLUMNA_ID);
            reg.remove(C_COLUMNA_ID);
        }

        return db.update(C_TABLA, reg, "_id=" + id, null);
    }

    public long delete(long id){
        if (db == null)
            abrir();

        return db.delete(C_TABLA, "_id=" + id, null);
    }
}
