package com.example.banco.fragments;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.banco.MiArrayAdapterCuentas;
import com.example.banco.R;
import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.pojo.Cliente;
import com.example.banco.pojo.Cuenta;

public class FragmentCuentas extends Fragment{
    ListView listaCuentas;
    Cliente c;
    MiBancoOperacional mbo;
    private CuentasListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_cuentas, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        c = (Cliente)getArguments().getSerializable("cliente");
        mbo = (MiBancoOperacional)getArguments().getSerializable("banco");

        listaCuentas = (ListView)getView().findViewById(R.id.listCuentas);
        listaCuentas.setAdapter(new MiArrayAdapterCuentas(this, mbo.getCuentas(c), R.layout.item_list_cuentas));

        listaCuentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null){
                    listener.onCuentaSeleccionada((Cuenta)listaCuentas.getAdapter().getItem(position));
                }
            }
        });
    }

    public void setCuentasListener(CuentasListener listener){
        this.listener = listener;
    }
}