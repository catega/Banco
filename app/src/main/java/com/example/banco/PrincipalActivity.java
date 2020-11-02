package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.banco.pojo.Cliente;

public class PrincipalActivity extends AppCompatActivity {
    Cliente c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        c = (Cliente)getIntent().getSerializableExtra("cliente");

    }

    public void onClick(View view){
        Intent intent = new Intent();

        switch(view.getTag().toString()){
            case "Cuentas":
                intent.setClass(PrincipalActivity.this, CuentasActivity.class);
                break;
            case "Movimientos":
                intent.setClass(PrincipalActivity.this, MovimientosActivity.class);
                break;
            case "Transferencia":
                intent.setClass(PrincipalActivity.this, TransferenciaActivity.class);
                break;
            case "Password":
                intent.setClass(PrincipalActivity.this, PasswordActivity.class);
                break;
        }
        intent.putExtra("cliente", c);
        startActivity(intent);
    }
}