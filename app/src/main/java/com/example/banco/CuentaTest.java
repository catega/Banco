package com.example.banco;

public class CuentaTest {
    String nombre, numCuenta;
    float saldo;

    public CuentaTest(String nombre, String numCuenta, float saldo) {
        this.nombre = nombre;
        this.numCuenta = numCuenta;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString(){
        return this.getNombre() + " " + this.getNumCuenta() + ": " + this.getSaldo() + "€";
    }
}
