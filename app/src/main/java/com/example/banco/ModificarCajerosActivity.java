package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

import com.example.banco.bd.Constantes;
import com.example.banco.dao.CajeroDAO;

public class ModificarCajerosActivity extends AppCompatActivity {

    int modo;
    long id;

    Cursor cursor;
    CajeroDAO cajeroDAO;

    EditText edtDireccion;
    EditText edtLatitud;
    EditText edtLongitud;
    EditText edtZoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cajeros);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) return;

        edtDireccion = (EditText)findViewById(R.id.edtDireccion);
        edtLatitud = (EditText)findViewById(R.id.edtLatitud);
        edtLongitud = (EditText)findViewById(R.id.edtLongitud);
        edtZoom = (EditText)findViewById(R.id.edtZoom);

        cajeroDAO = new CajeroDAO(this);

        try {
            cajeroDAO.abrir();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (bundle.containsKey(CajeroDAO.C_COLUMNA_ID)){
            id = bundle.getLong(CajeroDAO.C_COLUMNA_ID);
            consultar(id);
        }

        establecerModo(bundle.getInt(Constantes.C_MODO));
    }

    private void establecerModo(int m) {
        this.modo = m;
        // Si estamos solamente visualizando establecemos el modo edicion desactivado a todo el formulario
        if (modo == Constantes.C_VISUALIZAR) {
            this.setTitle(edtDireccion.getText().toString());
            this.setEdicion(false);
        }
    }

    private void consultar(long id) {
        //
        // Consultamos el centro por el identificador
        //
        cursor = cajeroDAO.getRegistro(id);
        edtDireccion.setText(cursor.getString(cursor.getColumnIndex(CajeroDAO.C_COLUMNA_DIRECCION)));
        edtLatitud.setText(cursor.getString(cursor.getColumnIndex(CajeroDAO.C_COLUMNA_LAT)));
        edtLongitud.setText(cursor.getString(cursor.getColumnIndex(CajeroDAO.C_COLUMNA_LNG)));
        edtZoom.setText(cursor.getString(cursor.getColumnIndex(CajeroDAO.C_COLUMNA_ZOOM)));

    }

    private void setEdicion(boolean opcion) {
        edtDireccion.setEnabled(opcion);
        edtLatitud.setEnabled(opcion);
        edtLongitud.setEnabled(opcion);
        edtZoom.setEnabled(opcion);
    }
}