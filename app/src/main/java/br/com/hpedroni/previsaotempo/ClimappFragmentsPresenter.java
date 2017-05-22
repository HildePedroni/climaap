package br.com.hpedroni.previsaotempo;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONException;

import br.com.hpedroni.previsaotempo.model.WebClient;
import br.com.hpedroni.previsaotempo.interfaces.IClimappFragment;
import br.com.hpedroni.previsaotempo.model.GPS;
import br.com.hpedroni.previsaotempo.model.InfoClima;

/**
 * Created by Hilde on 20/05/17.
 */

public class ClimappFragmentsPresenter implements IClimappFragment.presenter {


    private static String URL_OPEN_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    private static String MY_API_KEY = "f4806144307a4ec421d39c761ffd59a0";
    private static String LONDON_URL = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=" + MY_API_KEY;

    private static String DEFAULT_URL = "http://api.openweathermap.org/data/2.5/weather?q=brasilia,uk&appid=" + MY_API_KEY;


    private IClimappFragment.view view;


    public ClimappFragmentsPresenter(IClimappFragment.view view){
        this.view = view;
    }


    @Override
    public void getWeather(String local, Context context) {
        if(local.equals("Local")) {
            GPS gps = new GPS(context);
            Location location = gps.getLocation();
            String url = DEFAULT_URL;
            if(location != null){
                url = URL_OPEN_WEATHER + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + MY_API_KEY;
            }else{
                view.showToast("Falha ao encontrar localização, cidade padrão carregada!");
            }
            buscaDadosWebService(url);
        }else{
            String url = LONDON_URL;
            buscaDadosWebService(url);
        }
    }


    //Chamada Assincrona para buscar os dados no servidor
    private void buscaDadosWebService(String url) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                view.showLoading();
            }

            @Override
            protected String doInBackground(String... params) {
                String url = params[0];
                return WebClient.getInstance().getForecastData(url);
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    view.hideLoading();
                    InfoClima iTempo = new InfoClima(s);
                    view.showData(iTempo);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showToast("Falha ao recuperar dados.");
                }
            }
        }.execute(url);
    }



}
