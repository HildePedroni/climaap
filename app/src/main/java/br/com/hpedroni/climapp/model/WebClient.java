package br.com.hpedroni.climapp.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Hilde on 20/05/17.
 *
 * Classe responsável pelo acesso ao webservice.
 * foi implementada como um singleton para facilitar o acesso.
 */

public class WebClient {

    private static WebClient instance;

    public static WebClient getInstance() {
        if (instance == null) {
            instance = new WebClient();
        }
        return instance;
    }

    //Evita que a classe seja acessada fora do getInstance
    private WebClient() {
    }

    public String getForecastData(String mUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(mUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            StringBuilder response = new StringBuilder();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }
    }




}
