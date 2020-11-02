package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.pojo.Cliente;
import com.example.banco.pojo.Cuenta;

public class CuentasActivity extends AppCompatActivity {
    Cliente c;
    ListView lista;
    MiArrayAdapterCuentas<Cuenta> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        MiBancoOperacional mbo = MiBancoOperacional.getInstance(this.getApplicationContext());

        c = (Cliente)getIntent().getSerializableExtra("cliente");

        lista = (ListView)findViewById(R.id.listCuentas);
        adaptador = new MiArrayAdapterCuentas<Cuenta>(this, mbo.getCuentas(c), R.layout.item_list_cuentas);
        lista.setAdapter(adaptador);
    }

    public void onClickPrincipal(View view){
        Intent intent = new Intent(CuentasActivity.this, PrincipalActivity.class);
        intent.putExtra("cliente", c);
        startActivity(intent);
    }
}