package com.pgl8.guajerez;


public class Lugar {
    private double mLatitud;
    private double mLongitud;

    // constructor por defecto
    public Lugar(double mLatitud, double mLongitud) {
        this.mLatitud = mLatitud;
        this.mLongitud = mLongitud;
    }

    // métodos observadores
    public double getLatitud() {
        return mLatitud;
    }
    public double getLongitud() {
        return mLongitud;
    }
}
