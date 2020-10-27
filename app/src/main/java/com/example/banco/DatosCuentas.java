package com.example.banco;

import java.util.ArrayList;
import java.util.List;

public class DatosCuentas {
    static List CUENTAS = new ArrayList<Cuenta>();

    static {
        CUENTAS.add(new Cuenta("Cuenta 1", "12345", 562.34f));
        CUENTAS.add(new Cuenta("Cuenta 2", "22222", 1562.2f));
        CUENTAS.add(new Cuenta("Cuenta 3", "31313", 3500f));
        CUENTAS.add(new Cuenta("Cuenta 4", "77177", 1.3f));
    }
}
