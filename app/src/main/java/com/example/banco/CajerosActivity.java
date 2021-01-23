package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.banco.bd.Constantes;
import com.example.banco.dao.CajeroDAO;

import java.util.List;

public class CajerosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajeros);

        ListView listaCajeros = (ListView)findViewById(R.id.lstCajeros);

        CajeroDAO cajeroDAO = new CajeroDAO(getBaseContext());

        try {
            cajeroDAO.abrir();
            Cursor cursor = cajeroDAO.getCursor();

            startManagingCursor(cursor);
            CajerosAdapter cajerosAdapter = new CajerosAdapter(this, cursor);

            listaCajeros.setAdapter(cajerosAdapter);
        }catch (Exception e){
            Toast.makeText(getBaseContext(), "Error al abrir la base de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        listaCajeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CajerosActivity.this, ModificarCajerosActivity.class);
                intent.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);
                intent.putExtra(CajeroDAO.C_COLUMNA_ID, id);
                startActivityForResult(intent, Constantes.C_VISUALIZAR);
            }
        });
    }
}