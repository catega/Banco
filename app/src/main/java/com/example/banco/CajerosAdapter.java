package com.example.banco;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.banco.dao.CajeroDAO;

public class CajerosAdapter extends CursorAdapter {
    private CajeroDAO cajeroDAO = null;

    public CajerosAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        cajeroDAO = new CajeroDAO(context);
        cajeroDAO.abrir();
    }

    // El método newView se utiliza para inflar una nueva vista y devolverla ,
    // No se enlaza los datos a la vista en este punto .
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Obtenemos el inflador
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflamos la vista que vamos a devolver
        View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        return view;
    }

    // El método bindView se utiliza para enlazar todos los datos de una vista determinada
    // como la creación del texto en una TextView . En este caso obtenemos el nombre y lo
    // asignamos al textview.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Como hemos inflado simple_dropdown_item_1line solo tenemos un TextView que lo obtenemos
        TextView tv = (TextView) view;
        // Obtenemos el indice de la columna
        int i = cursor.getColumnIndex(CajeroDAO.C_COLUMNA_DIRECCION);
        // Asignamos el valor
        tv.setText(cursor.getString(i));
    }
}
