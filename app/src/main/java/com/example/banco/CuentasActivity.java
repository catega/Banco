package com.example.banco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.fragments.CuentasListener;
import com.example.banco.fragments.FragmentCuentas;
import com.example.banco.fragments.FragmentMovimientos;
import com.example.banco.pojo.Cliente;
import com.example.banco.pojo.Cuenta;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CuentasActivity extends AppCompatActivity implements CuentasListener {
    Cliente c;
    ListView lista;
    MiBancoOperacional mbo;
    Bundle bundle;
    MiArrayAdapterCuentas<Cuenta> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        mbo = MiBancoOperacional.getInstance(this.getApplicationContext());

        c = (Cliente) getIntent().getSerializableExtra("cliente");
        bundle = new Bundle();
        bundle.putSerializable("cliente", c);
        bundle.putSerializable("banco", mbo);
        FragmentCuentas frgCuentas = (FragmentCuentas)getSupportFragmentManager().findFragmentById(R.id.frgCuentas);
        frgCuentas.setCuentasListener(this);
        frgCuentas.setArguments(bundle);
    }

    public void onClickPrincipal(View view){
        Intent intent = new Intent(CuentasActivity.this, PrincipalActivity.class);
        intent.putExtra("cliente", c);
        startActivity(intent);
    }



    @Override
    public void onCuentaSeleccionada(Cuenta c) {
        boolean hayMovimientos = (getSupportFragmentManager().findFragmentById(R.id.frgMovimientos) != null);

        if(hayMovimientos){
            ((FragmentMovimientos)getSupportFragmentManager().findFragmentById(R.id.frgMovimientos)).mostrarMovimientos(mbo.getMovimientos(c));
        }else{
            Intent intent = new Intent(this, MovimientosActivity.class);
            intent.putExtra("cuenta", c);
            startActivity(intent);
        }
    }
}