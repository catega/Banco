package com.example.banco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.banco.pojo.Cliente;

import java.util.Locale;

public class PrincipalActivity extends AppCompatActivity {
    Cliente c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PrincipalActivity.this);
        String nick = sp.getString("nick", "");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        c = (Cliente)getIntent().getSerializableExtra("cliente");

        TextView txtSubtitle = (TextView)findViewById(R.id.txtSubtitle);
        if (nick.isEmpty())
            txtSubtitle.setText(txtSubtitle.getText() + " " + c.getNombre() + " " + c.getApellidos());
        else
            txtSubtitle.setText(txtSubtitle.getText() + " " + nick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();

        switch(item.getItemId()){
            case R.id.action_cuentas:
                intent.setClass(PrincipalActivity.this, CuentasActivity.class);
                break;
            case R.id.action_movimientos:
                intent.setClass(PrincipalActivity.this, MovimientosActivity.class);
                break;
            case R.id.action_transferencia:
                intent.setClass(PrincipalActivity.this, TransferenciaActivity.class);
                break;
            case R.id.action_cajeros:
                intent.setClass(PrincipalActivity.this, CajerosActivity.class);
                intent.putExtra("cliente", c);
                startActivity(intent);
                break;
            case R.id.action_password:
                intent.setClass(PrincipalActivity.this, PasswordActivity.class);
                break;
            case R.id.action_settings:
                intent.setClass(PrincipalActivity.this, OpcionesActivity.class);
                break;
        }
        intent.putExtra("cliente", c);
        startActivity(intent);
        return true;
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
            case "Cajeros":
                intent.setClass(PrincipalActivity.this, CajerosActivity.class);
                break;
        }
        intent.putExtra("cliente", c);
        startActivity(intent);
    }
}