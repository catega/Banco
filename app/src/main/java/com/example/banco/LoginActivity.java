package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.pojo.Cliente;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usuario = (EditText)findViewById(R.id.editTextTextPersonName);
        final EditText pass = (EditText)findViewById(R.id.editTextTextPassword);

        final MiBancoOperacional mbo = MiBancoOperacional.getInstance(this.getApplicationContext());

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente c = new Cliente();
                c.setNif(usuario.getText().toString());
                c.setClaveSeguridad(pass.getText().toString());
                c = mbo.login(c);

                if(c == null){
                    Toast.makeText(LoginActivity.this, "Los datos no coinciden", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                    intent.putExtra("cliente", c);
                    startActivity(intent);
                }
            }
        });
    }
}