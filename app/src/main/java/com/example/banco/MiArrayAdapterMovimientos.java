package com.example.banco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.banco.pojo.Movimiento;

import java.util.List;

public class MiArrayAdapterMovimientos<T> extends ArrayAdapter<T> {
    private int layout;
    public MiArrayAdapterMovimientos(Fragment context, List<T> objects, @LayoutRes int layout) {
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

        TextView numCuentas = (TextView) listView.findViewById(R.id.txtCuentas);
        TextView saldo = (TextView) listView.findViewById(R.id.txtSaldo);
        TextView fecha = (TextView) listView.findViewById(R.id.txtFecha);
        TextView descripcion = (TextView) listView.findViewById(R.id.txtDescripcion);

        Movimiento item = (Movimiento) getItem(position);

        if(item.getCuentaDestino().getNumeroCuenta() != null){
            numCuentas.setText(item.getCuentaOrigen().getNumeroCuenta() + " -> " + item.getCuentaDestino().getNumeroCuenta());
        }else{
            numCuentas.setText(item.getCuentaOrigen().getNumeroCuenta());
        }

        saldo.setText(String.valueOf(item.getImporte()) + "€");
        fecha.setText(item.fechaFormat());
        descripcion.setText(item.getDescripcion());

        return listView;
    }


}
