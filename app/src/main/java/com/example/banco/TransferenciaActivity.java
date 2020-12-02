package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.pojo.Cliente;
import com.example.banco.pojo.Cuenta;
import com.example.banco.pojo.Movimiento;

import java.util.Date;

public class TransferenciaActivity extends AppCompatActivity {
    private GridView gridCuentas;
    private Spinner spinnerCuentas;
    private String numCuentaOrigen = "", numCuentaDestino = "";
    private float ingreso = 0;
    private boolean justificante = false;
    Cliente c;
    Cuenta origen, destino;
    MiBancoOperacional mbo;
    Movimiento movimientoOrigen, movimientoDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);

        mbo = MiBancoOperacional.getInstance(this.getApplicationContext());

        c = (Cliente)getIntent().getSerializableExtra("cliente");

        spinnerCuentas = (Spinner)findViewById(R.id.spnCuentas);


        CheckBox chkBox = (CheckBox)findViewById(R.id.chkJustificante);
        chkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    justificante = true;
                else
                    justificante = false;
            }
        });

        gridCuentas = (GridView)findViewById(R.id.gridCuentas);
        ArrayAdapter<Cuenta> adaptador = new MiArrayAdapter<Cuenta>(this, c.getListaCuentas(), R.layout.item_grid);
        gridCuentas.setAdapter(adaptador);
        adaptador = new MiArrayAdapter<Cuenta>(this, c.getListaCuentas(), R.layout.item_spinner);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuentas.setAdapter(adaptador);

        gridCuentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numCuentaOrigen = ((Cuenta)parent.getItemAtPosition(position)).getNumeroCuenta();
                origen = (Cuenta) parent.getItemAtPosition(position);
                TextView txtCuentaOrigen = (TextView)findViewById(R.id.txtCuentaOrigen);
                txtCuentaOrigen.setText("Cuenta origen: " + numCuentaOrigen);
            }
        });

        spinnerCuentas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numCuentaDestino = ((Cuenta)parent.getItemAtPosition(position)).getNumeroCuenta();
                destino = (Cuenta) parent.getItemAtPosition(position);

                TextView txtCuentaDestino = (TextView)findViewById(R.id.txtCuentaDestino);
                txtCuentaDestino.setText("Cuenta destino: " + numCuentaDestino);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText edtSaldo = (EditText)findViewById(R.id.edtTextSaldoIngresar);


        Button btnIngresar = (Button)findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingreso = Float.parseFloat(edtSaldo.getText().toString());

                if(numCuentaOrigen != numCuentaDestino){
                    if(origen.getSaldoActual() >= ingreso){
                        movimientoOrigen = new Movimiento(0, new Date(), "Transferencia", ingreso, origen, destino);
                        movimientoDestino = new Movimiento(1, new Date(), "Transferencia", ingreso, destino, origen);
                        mbo.transferencia(movimientoOrigen, movimientoDestino);

                        Toast.makeText(getApplicationContext(), "Datos transferencia: "
                                + "\nOrigen: " + numCuentaOrigen
                                + "\nDestino: " + numCuentaDestino
                                + "\nSaldo a ingresar: " + ingreso + "€"
                                + (justificante ? "\nCon Justificante" : "\nSin Justificante") , Toast.LENGTH_LONG).show();

                        Intent intent = new Intent();
                        intent.setClass(TransferenciaActivity.this, PrincipalActivity.class);
                        intent.putExtra("cliente", c);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "La cuenta origen no tiene suficiente saldo", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Elige dos cuentas distintas", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                numCuentaDestino = "";
                numCuentaOrigen = "";
                ingreso = 0;
                justificante = false;

                edtSaldo.setText("");

                Toast.makeText(getApplicationContext(), "Ingreso cancelado", Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.setClass(TransferenciaActivity.this, PrincipalActivity.class);
                intent.putExtra("cliente", c);
                startActivity(intent);
            }
        });
    }
}