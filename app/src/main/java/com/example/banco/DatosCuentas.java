package com.example.banco;

import java.util.ArrayList;
import java.util.List;

public class DatosCuentas {
    static List CUENTAS = new ArrayList<CuentaTest>();

    static {
        CUENTAS.add(new CuentaTest("Cuenta 1", "12345", 562.34f));
        CUENTAS.add(new CuentaTest("Cuenta 2", "22222", 1562.2f));
        CUENTAS.add(new CuentaTest("Cuenta 3", "31313", 3500f));
        CUENTAS.add(new CuentaTest("Cuenta 4", "77177", 1.3f));
    }
}
