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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class TransferenciaActivity extends AppCompatActivity {
    private GridView gridCuentas;
    private Spinner spinnerCuentas;
    private String numCuentaOrigen = "", numCuentaDestino = "";
    private float ingreso = 0;
    private boolean justificante = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);

        final RadioButton rdbPropia = (RadioButton)findViewById(R.id.rdbtnPropia);

        spinnerCuentas = (Spinner)findViewById(R.id.spnCuentas);
        final EditText editTextAjena = (EditText)findViewById(R.id.edtTextCuentaAjena);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rdGrpCuentas);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)findViewById(checkedId);

                if(radioButton.getText().toString().contentEquals("Propia")){
                    spinnerCuentas.setVisibility(View.VISIBLE);
                    editTextAjena.setVisibility(View.INVISIBLE);
                }else{
                    spinnerCuentas.setVisibility(View.INVISIBLE);
                    editTextAjena.setVisibility(View.VISIBLE);
                }
            }
        });

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
        ArrayAdapter<Cuenta> adaptador = new MiArrayAdapter<Cuenta>(this, DatosCuentas.CUENTAS, R.layout.item_grid);
        gridCuentas.setAdapter(adaptador);
        adaptador = new MiArrayAdapter<Cuenta>(this, DatosCuentas.CUENTAS, R.layout.item_spinner);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuentas.setAdapter(adaptador);

        gridCuentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numCuentaOrigen = ((Cuenta)parent.getItemAtPosition(position)).getNumCuenta();
            }
        });

        spinnerCuentas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numCuentaDestino = ((Cuenta)parent.getItemAtPosition(position)).getNumCuenta();
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
                if(!rdbPropia.isChecked()){
                    numCuentaDestino = editTextAjena.getText().toString();
                }

                ingreso = Float.parseFloat(edtSaldo.getText().toString());

                Toast.makeText(getApplicationContext(), "Datos transferencia: "
                       + "\nOrigen: " + numCuentaOrigen
                       + "\nDestino: " + numCuentaDestino
                       + "\nSaldo a ingresar: " + ingreso + "€"
                       + (justificante ? "\nCon Justificante" : "\nSin Justificante") , Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.setClass(TransferenciaActivity.this, PrincipalActivity.class);
                startActivity(intent);
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

                editTextAjena.setText("");
                edtSaldo.setText("");

                Toast.makeText(getApplicationContext(), "Ingreso cancelado", Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.setClass(TransferenciaActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }
}