package com.pgl8.guajerez;

/**
 * Created by WINDOWS 7 on 11/02/2015.
 */
public class Vino {
    private String notasCata_;
    private String elaboracion_;
    private String parametros_;
    private String consumo_;
    private String URL;

    //consturctor sin parámetros
    public Vino() {
        super();
    }

    //constructor por defecto
    public Vino(String notasCata, String elaboracion, String parametros, String consumo) {
        super();
        this.notasCata_ = notasCata;
        this.elaboracion_ = elaboracion;
        this.parametros_ = parametros;
        this.consumo_ = consumo;

    }

    //métodos observadores
    final String getNotasCata() {
        return notasCata_;
    }

    final String getElaboracion() {
        return elaboracion_;
    }

    final String getParametros() {
        return parametros_;
    }

    final String getConsumo() {
        return consumo_;
    }
}
