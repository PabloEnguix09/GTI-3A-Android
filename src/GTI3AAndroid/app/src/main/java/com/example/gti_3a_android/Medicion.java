/**
 *
 * NOMBRE: Medicion
 * AUTOR: Pablo Enguix Llopis
 * FECHA: 16/10/2021
 * DESCRIPCION: Este fichero es el que contiene todos los datos de una medicion
 *
 */

package com.example.gti_3a_android;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Medicion {

    private int idUsuario;
    private int idSensor;

    private static final String FORMATO_FECHA = "yyyy-MM-dd hh:mm:ss";
    private Timestamp fecha;

    private double posicionLat;
    private double posicionLng;
    private double dato;

    public Medicion(int idUsuario, int idSensor, double posicionLat, double posicionLng, double dato) {
        this.idUsuario = idUsuario;
        this.idSensor = idSensor;
        this.fecha = new Timestamp(System.currentTimeMillis());
        this.posicionLat = posicionLat;
        this.posicionLng = posicionLng;
        this.dato = dato;
    }

    /**
     *
     * Convierte un objeto Medicion en un JSON
     *
     * @return el objeto en JSON
     */
    public String medicionToJSON() {
        String fechaFormateada = new SimpleDateFormat(FORMATO_FECHA).format(this.fecha);

        String res = "{"+
                "\"idUsuario\":\""+this.idUsuario+"\", " +
                "\"idSensor\":\""+this.idSensor+"\", " +
                "\"fecha\":\""+this.fecha+"\"," +
                "\"posicionLat\":\""+this.posicionLat+"\", " +
                "\"posicionLng\":\""+this.posicionLng+"\", " +
                "\"dato\":\""+this.dato+"\", " +
                "}";
        return res;
    }
}
