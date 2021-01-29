package com.example.banco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    Button btnCrear;
    Button btnCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cajeros);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) return;

        edtDireccion = (EditText)findViewById(R.id.edtDireccion);
        edtLatitud = (EditText)findViewById(R.id.edtLatitud);
        edtLongitud = (EditText)findViewById(R.id.edtLongitud);
        edtZoom = (EditText)findViewById(R.id.edtZoom);

        btnCrear = (Button)findViewById(R.id.btnCrear);
        btnCancelar = (Button)findViewById(R.id.btnCancelar);

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

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }

    private void establecerModo(int m) {
        this.modo = m;

        if (modo == Constantes.C_VISUALIZAR) {
            this.setTitle(edtDireccion.getText().toString());
            this.setEdicion(false);
        } else if (modo == Constantes.C_CREAR) {
            this.setTitle("Crear");
            this.setEdicion(true);
        } else if (modo == Constantes.C_EDITAR) {
            this.btnCrear.setText("Guardar");
            this.setTitle("Editar");
            this.setEdicion(true);
        }
    }

    private void consultar(long id) {
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

    public void cancelar() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    public void guardar() {
        ContentValues reg = new ContentValues();

        if (modo == Constantes.C_EDITAR)
            reg.put(CajeroDAO.C_COLUMNA_ID, id);

        reg.put(CajeroDAO.C_COLUMNA_DIRECCION, edtDireccion.getText().toString());
        reg.put(CajeroDAO.C_COLUMNA_LAT, edtLatitud.getText().toString());
        reg.put(CajeroDAO.C_COLUMNA_LNG, edtLongitud.getText().toString());
        reg.put(CajeroDAO.C_COLUMNA_ZOOM, edtZoom.getText().toString());

        if (modo == Constantes.C_CREAR)
            cajeroDAO.add(reg);
        else if (modo == Constantes.C_EDITAR)
            cajeroDAO.update(reg);

        setResult(RESULT_OK);
        finish();
    }

    public void eliminar(final long id) {
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle("Eliminar el cajero?");
        dialogEliminar.setMessage("Seguro que quieres eliminar el cajero?");
        dialogEliminar.setCancelable(false);
        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                cajeroDAO.delete(id);
                Toast.makeText(ModificarCajerosActivity.this, "Cajero Eliminado", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
        dialogEliminar.setNegativeButton(android.R.string.no, null);
        dialogEliminar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Mirar si l'usuari és admin
        getMenuInflater().inflate(R.menu.menu_cajeros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editar:
                establecerModo(Constantes.C_EDITAR);
                return true;
            case R.id.menu_eliminar:
                eliminar(id);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}