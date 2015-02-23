package com.pgl8.guajerez;

/**
 * Created by WINDOWS 7 on 11/02/2015.
 */
public class Vino {
    private String mNotasCata;
    private String mElaboracion;
    private String mParametros;
    private String mConsumo;

    //constructor por defecto
    public Vino(String notasCata, String elaboracion, String consumo, String parametros) {
        this.mNotasCata = notasCata;
        this.mParametros = parametros;
        this.mElaboracion = elaboracion;
        this.mConsumo = consumo;
    }

    //métodos observadores

    public String getNotasCata() {
        return mNotasCata;
    }

    public String getElaboracion() {
        return mElaboracion;
    }

    public String getParametros() {
        return mParametros;
    }

    public String getConsumo() {
        return mConsumo;
    }
}
