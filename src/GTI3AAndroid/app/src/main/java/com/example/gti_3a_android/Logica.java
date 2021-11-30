/**
 *
 * NOMBRE: Logica
 * AUTOR: Pablo Enguix Llopis
 * FECHA: 16/10/2021
 * DESCRIPCION: Este fichero se encarga de guardar la medicion en la base de datos e informar al usuario de si se ha guardado o no (logica fake)
 *
 */

package com.example.gti_3a_android;

import android.util.Log;

public class Logica {

    public Logica() {
    }

    /**
     *
     * medicion -> guardarMedicion()
     *
     * Guarda la medición en la base de datos
     *
     * @param medicion La medicion que guarda
     */
    public void guardarMedicion(Medicion medicion) {
        PeticionREST peticionREST = new PeticionREST();
        String direccionREST = "http://192.168.43.50:8080/postMedicion";

        peticionREST.hacerPeticion("POST", direccionREST, medicion.medicionToJSON(),
                new PeticionREST.Respuesta () {
                    @Override
                    public void cb(int codigo, String mensaje) {

                        Log.d ("Logica","codigo: " + codigo + "\n Mensaje: " + mensaje);

                    }
                });
    }
}
