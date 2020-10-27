package com.example.banco;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

public class MiArrayAdapter<T> extends ArrayAdapter<T> {
    private int layout;
    public MiArrayAdapter(Context context, List<T> objects, @LayoutRes int layout) {
        super(context, 0, objects);
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = convertView;

        if (null == convertView) {
            gridView = inflater.inflate(layout, parent, false);
        }

        TextView nom = (TextView) gridView.findViewById(R.id.txtNombreCuenta);
        TextView numCuenta = (TextView) gridView.findViewById(R.id.txtNumCuenta);
        TextView saldo = (TextView) gridView.findViewById(R.id.txtSaldo);

        Cuenta item = (Cuenta) getItem(position);

        nom.setText(item.getNombre());
        numCuenta.setText(item.getNumCuenta());
        saldo.setText(String.valueOf(item.getSaldo()) + "€");

        return gridView;
    }


}
