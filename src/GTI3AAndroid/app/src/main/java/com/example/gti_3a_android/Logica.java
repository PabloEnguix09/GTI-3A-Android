package com.example.gti_3a_android;

import android.util.Log;

public class Logica {

    public Logica() {
    }

    public void guardarMedicion(Medicion medicion) {
        PeticionREST peticionREST = new PeticionREST();
        String direccionREST = "http://192.168.10.103:8080/medicion";

        peticionREST.hacerPeticion("POST", direccionREST, medicion.medicionToJSON(),
                new PeticionREST.Respuesta () {
                    @Override
                    public void cb(int codigo, String mensaje) {

                        Log.d ("Logica","codigo: " + codigo + "\n Mensaje: " + mensaje);

                    }
                });
    }
}
