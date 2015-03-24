package com.pgl8.guajerez;


public class Lugar {
    private float mLatitud;
    private float mLongitud;

    // constructor por defecto
    public Lugar(float mLatitud, float mLongitud) {
        this.mLatitud = mLatitud;
        this.mLongitud = mLongitud;
    }

    // métodos observadores
    public float getLatitud() {
        return mLatitud;
    }
    public float getLongitud() {
        return mLongitud;
    }
}
