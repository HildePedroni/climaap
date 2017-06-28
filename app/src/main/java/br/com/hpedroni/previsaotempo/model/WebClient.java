package br.com.hpedroni.previsaotempo.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hilde on 20/05/17.
 * <p>
 * Classe responsável pelo acesso ao webservice.
 * foi implementada como um singleton para facilitar o acesso.
 */

public class WebClient {

    private static final String TAG = WebClient.class.getSimpleName();
    private static WebClient instance;
    private OkHttpClient client;


    public static WebClient getInstance() {
        if (instance == null) {
            instance = new WebClient();
        }
        return instance;
    }

    //Evita que a classe seja acessada fora do getInstance
    private WebClient() {
        client = new OkHttpClient();
    }

    public String getForecastData(String mUrl) {

        Request request = new Request.Builder()
                .url(mUrl)
                .build();

        Response response = null;
        try {
            response = this.client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
