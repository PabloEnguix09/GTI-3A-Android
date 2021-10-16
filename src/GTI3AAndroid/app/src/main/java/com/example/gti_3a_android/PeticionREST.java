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

    @Override
    protected Boolean doInBackground(Void... params) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(destino);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod(metodo);
            connection.setDoInput(true);

            if (this.metodo.equals("GET") && this.mensaje != null) {
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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

    protected void onPostExecute(Boolean comoFue) {
        this.respuesta.cb(codigoRespuesta, mensajeRespuesta);
    }

    public PeticionREST() {
    }

    public interface Respuesta {
        void cb(int codigo, String mensaje);
    }

    public void hacerPeticion(String metodo, String destino, String mensaje, Respuesta respuesta) {
        this.metodo = metodo;
        this.destino = destino;
        this.mensaje = mensaje;
        this.respuesta = respuesta;

        this.execute();
    }
}
