package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.pojo.Cliente;
import com.example.banco.pojo.Cuenta;
import com.example.banco.pojo.Movimiento;

import java.util.ArrayList;

public class MovimientosActivity extends AppCompatActivity {
    Cliente c;
    Cuenta cuenta;
    ListView lista;
    MiArrayAdapterCuentas<Cuenta> adaptadorCuentas;
    MiArrayAdapterMovimientos<Movimiento> adaptadorMovimientos;
    ArrayList<Movimiento> movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        MiBancoOperacional mbo = MiBancoOperacional.getInstance(this.getApplicationContext());

        c = (Cliente)getIntent().getSerializableExtra("cliente");
        cuenta = mbo.getCuentas(c).get(0);

        Spinner spinnerCuentas = (Spinner)findViewById(R.id.spinnerCuentas);
        adaptadorCuentas = new MiArrayAdapterCuentas<Cuenta>(this, mbo.getCuentas(c), R.layout.item_list_cuentas);
        adaptadorCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lista = (ListView)findViewById(R.id.listMovimientos);
        adaptadorMovimientos = new MiArrayAdapterMovimientos<>(this, mbo.getMovimientos(cuenta), R.layout.item_list_movimientos);
        lista.setAdapter(adaptadorMovimientos);

        spinnerCuentas.setAdapter(adaptadorCuentas);

        spinnerCuentas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cuenta = (Cuenta)parent.getItemAtPosition(position);
                adaptadorMovimientos.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClickPrincipal(View view){
        Intent intent = new Intent(MovimientosActivity.this, PrincipalActivity.class);
        intent.putExtra("cliente", c);
        startActivity(intent);
    }
}