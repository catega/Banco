package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banco.pojo.Cliente;

public class PasswordActivity extends AppCompatActivity {
    Cliente c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        c = (Cliente)getIntent().getSerializableExtra("cliente");

        final EditText passActual = (EditText)findViewById(R.id.edtTextPassword);
        final EditText passNueva = (EditText)findViewById(R.id.edtTextNuevaPass);
        final EditText passNuevaRepetir = (EditText)findViewById(R.id.edtTextNuevaPassRepetir);

        Button btnCambiar = (Button)findViewById(R.id.btnPassword);
        btnCambiar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c.getClaveSeguridad().contentEquals(passActual.getText())){
                    if(passNueva.getText().toString().contentEquals(passNuevaRepetir.getText())){
                        c.setClaveSeguridad(passNueva.getText().toString());
                        Intent intent = new Intent(PasswordActivity.this, PrincipalActivity.class);
                        intent.putExtra("cliente", c);
                        startActivity(intent);
                    }else{
                        Toast.makeText(PasswordActivity.this, "Esribe bien la nueva contrasña dos veces", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(PasswordActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClickPrincipal(View view){
        Intent intent = new Intent(PasswordActivity.this, PrincipalActivity.class);
        intent.putExtra("cliente", c);
        startActivity(intent);
    }
}