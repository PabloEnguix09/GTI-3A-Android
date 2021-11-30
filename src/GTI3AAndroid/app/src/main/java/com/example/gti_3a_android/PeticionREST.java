/**
 *
 * NOMBRE: PeticionREST
 * AUTOR: Pablo Enguix Llopis
 * FECHA: 16/10/2021
 * DESCRIPCION: Este fichero es el que se encarga de recoger los datos a subir a la base de datos, subirlos e informar si se ha conseguido (logica verdadera)
 *
 */

package com.example.gti_3a_android;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PeticionREST extends AsyncTask<Void, Void, Boolean> {

    private String metodo;
    private String destino;
    private String mensaje = null;
    private Respuesta respuesta;

    private int codigoRespuesta;
    private String mensajeRespuesta;

    /**
     *
     * diseño logico: parametros -> doInBackgroound() -> VoF
     *
     * Hace una petición al servidor rest
     *
     * @param params los parámetros de la petición
     * @return Si se ha conseguido realizar la petición correctamente
     */

    @Override
    protected Boolean doInBackground(Void... params) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(destino);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod(metodo);
            connection.setDoInput(true);

            if (!this.metodo.equals("GET") && this.mensaje != null) {
                connection.setDoOutput(true);
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(mensaje);
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            int codigoRes = connection.getResponseCode();
            String resMensaje = connection.getResponseMessage();

            String res = "" + codigoRes + ":" + resMensaje;
            codigoRespuesta = codigoRes;


            try {
                InputStream inputStream = connection.getInputStream();
                Log.d("REST", inputStream.toString());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                Log.d("REST","bufferedReader:" + bufferedReader.toString());

                String linea;
                StringBuilder stringBuilder = new StringBuilder();
                while ((linea = bufferedReader.readLine()) != null) {
                    stringBuilder.append(linea);
                }

                mensajeRespuesta = stringBuilder.toString();

                connection.disconnect();
            } catch (IOException exception) {
                Log.d("REST", "no hay cuerpo");
            }
            return true;
        } catch (Exception exception) {
            Log.d("REST", exception.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    /**
     *
     * Después de que se ejecute la petición REST, te dice cómo ha ido
     *
     * @param comoFue Si ha ido bien o mal
     */

    protected void onPostExecute(Boolean comoFue) {
        this.respuesta.cb(codigoRespuesta, mensajeRespuesta);
    }

    public PeticionREST() {
    }

    public interface Respuesta {
        void cb(int codigo, String mensaje);
    }

    /**
     *
     * diseño logico: metodo, destino, mensaje, respuesta -> hacerPeticion()
     *
     * Crea los parámetros de la petición
     *
     * @param metodo El tipo de método (GET, POST...)
     * @param destino La url de destino
     * @param mensaje El cuerpo de la petición
     * @param respuesta La respuesta que devuelve el servidor
     */
    public void hacerPeticion(String metodo, String destino, String mensaje, Respuesta respuesta) {
        this.metodo = metodo;
        this.destino = destino;
        this.mensaje = mensaje;
        this.respuesta = respuesta;

        this.execute();
    }
}
