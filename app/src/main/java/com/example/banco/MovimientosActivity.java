package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.fragments.FragmentMovimientos;
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

        cuenta = (Cuenta)getIntent().getSerializableExtra("cuenta");

        FragmentMovimientos frgMovimientos = (FragmentMovimientos)getSupportFragmentManager().findFragmentById(R.id.frgMovimientos);
        frgMovimientos.mostrarMovimientos(mbo.getMovimientos(cuenta));

        Bundle bundle = new Bundle();
        bundle.putSerializable("cuenta", cuenta);
        bundle.putSerializable("banco", mbo);
        frgMovimientos.setArguments(bundle);

    }

    public void onClickPrincipal(View view){
        Intent intent = new Intent(MovimientosActivity.this, CuentasActivity.class);
        startActivity(intent);
    }
}