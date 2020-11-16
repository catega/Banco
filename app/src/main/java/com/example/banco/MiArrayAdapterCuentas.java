package com.example.banco;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.banco.pojo.Cuenta;

import java.util.List;

public class MiArrayAdapterCuentas<T> extends ArrayAdapter<T> {
    private int layout;
    public MiArrayAdapterCuentas(Fragment context, List<T> objects, @LayoutRes int layout) {
        super(context.getActivity(), 0, objects);
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView = convertView;

        if (null == convertView) {
            listView = inflater.inflate(layout, parent, false);
        }

        TextView numCuenta = (TextView) listView.findViewById(R.id.txtNumCuenta);
        TextView saldo = (TextView) listView.findViewById(R.id.txtSaldoCuenta);

        Cuenta item = (Cuenta) getItem(position);

        numCuenta.setText(item.getNumeroCuenta());
        saldo.setText(String.valueOf(item.getSaldoActual()) + "€");

        return listView;
    }


}
