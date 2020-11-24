package com.example.banco.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.banco.MiArrayAdapterMovimientos;
import com.example.banco.R;
import com.example.banco.bd.MiBancoOperacional;
import com.example.banco.pojo.Cuenta;
import com.example.banco.pojo.Movimiento;

import java.util.ArrayList;

public class FragmentMovimientos extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_movimientos, container, false);
    }

    public void mostrarMovimientos(ArrayList<Movimiento> movimientos){
        ListView listaMovimientos = (ListView)getView().findViewById(R.id.listMovimientos);
        listaMovimientos.setAdapter(new MiArrayAdapterMovimientos(this, movimientos, R.layout.item_list_movimientos));
    }
}