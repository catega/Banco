package com.example.banco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.banco.bd.Constantes;
import com.example.banco.dao.CajeroDAO;
import com.example.banco.pojo.Cliente;

import java.util.List;

public class CajerosActivity extends AppCompatActivity{

    ListView listaCajeros;
    CajeroDAO cajeroDAO;
    Cliente c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajeros);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        c = (Cliente)getIntent().getSerializableExtra("cliente");

        listaCajeros = (ListView)findViewById(R.id.lstCajeros);

        cajeroDAO = new CajeroDAO(getBaseContext());

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case Constantes.C_CREAR:
            case Constantes.C_EDITAR:
                if (resultCode == RESULT_OK)
                    recargarLista();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void recargarLista() {
        CajeroDAO cajeroDAO = new CajeroDAO(getBaseContext());
        cajeroDAO.abrir();
        CajerosAdapter cajerosAdapter = new CajerosAdapter(this, cajeroDAO.getCursor());
        listaCajeros.setAdapter(cajerosAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Mirar si l'usuari és admin
        getMenuInflater().inflate(R.menu.menu_crear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id){
            case R.id.menu_nuevo:
                intent = new Intent(this, ModificarCajerosActivity.class);
                intent.putExtra(Constantes.C_MODO, Constantes.C_CREAR);
                startActivityForResult(intent, Constantes.C_CREAR);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}