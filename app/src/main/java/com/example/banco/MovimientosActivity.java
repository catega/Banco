package com.example.banco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.fragments.FragmentMovimientos;
import com.example.banco.pojo.Cliente;
import com.example.banco.pojo.Cuenta;
import com.example.banco.pojo.Movimiento;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MovimientosActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Cliente c;
    Cuenta cuenta;
    ListView lista;
    MiArrayAdapterCuentas<Cuenta> adaptadorCuentas;
    MiArrayAdapterMovimientos<Movimiento> adaptadorMovimientos;
    ArrayList<Movimiento> movimientos;
    FragmentMovimientos frgMovimientos;
    MiBancoOperacional mbo;
    private BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        mbo = MiBancoOperacional.getInstance(this.getApplicationContext());

        navBar = (BottomNavigationView)findViewById(R.id.menu_nav);
        navBar.setOnNavigationItemSelectedListener(this);

        cuenta = (Cuenta)getIntent().getSerializableExtra("cuenta");

        frgMovimientos = (FragmentMovimientos)getSupportFragmentManager().findFragmentById(R.id.frgMovimientos);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_todos:
                frgMovimientos.mostrarMovimientos(mbo.getMovimientos(cuenta));
                break;
            case R.id.menu_0:
                frgMovimientos.mostrarMovimientos(mbo.getMovimientosTipo(cuenta, 0));
                break;
            case R.id.menu_1:
                frgMovimientos.mostrarMovimientos(mbo.getMovimientosTipo(cuenta, 1));
                break;
            case R.id.menu_2:
                frgMovimientos.mostrarMovimientos(mbo.getMovimientosTipo(cuenta, 2));
                break;
        }
        return true;
    }
}