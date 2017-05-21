package br.com.hpedroni.previsaotempo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hilde on 20/05/17.
 *
 * Essa classe contem os dados que são obtidos da API de clima openweathermap
 * Apenas as informações mais relevantes para o propósito da aplicação são capturadas do Json
 *
 */

public class InfoClima {

    //Json de referência
    //{
    // "coord":{"lon":-47.05,"lat":-22.92},
    // "weather":[
    //              {"id":804,
    //               "main":"Clouds",
    //               "description":"overcast clouds",
    //               "icon":"04n"}
    //          ],
    // "base":"stations",
    // "main":{
    //          "temp":290.15,
    //          "pressure":1015,
    //          "humidity":100,
    //          "temp_min":290.15,
    //          "temp_max":290.15
    //         },
    // "visibility":10000,
    // "wind":{
    //          "speed":3.6,
    //          "deg":240},
    // "clouds":{"all":90},
    // "dt":1495234800,
    // "sys":{
    //          "type":1,"id":4521,
    //          "message":0.0045,
    //          "country":"BR",
    //          "sunrise":1495186581,
    //          "sunset":1495225978
    //          },
    // "id":3467865,
    // "name":"Campinas",
    // "cod":200}


    private double temp;
    private String pais;
    private String cidade;
    private int umidade;


    //Recebe um String em formato Json e formata para as suas necessiades.
    public InfoClima(String data) throws JSONException {
        JSONObject jObject = new JSONObject(data);
        this.cidade = jObject.getString("name");
        this.pais = jObject.getJSONObject("sys").getString("country");
        this.temp = jObject.getJSONObject("main").getDouble("temp");
        this.umidade = jObject.getJSONObject("main").getInt("humidity");
    }

    //Retorna o local, fomatado : Cidade - país
    public String getLocal() {
        return this.cidade + " - " + this.pais;
    }

    //Retorna a temperatura local, convertida em celsius, com arredondamento e com simbolo de grau
    public String getTemp() {
        //converte kelvin pra celsius e arredonda de .5 em .5
        double c =  Math.round((this.temp - 273.15) * 2) / 2.0;
        return String.valueOf((c) + " \u2103");
    }

    //Retorna a umidade local formatada em %
    public String getUmidade(){
        return this.umidade + "%";
    }

    //To String, utilizado para testes
    @Override
    public String toString() {
        return "Cidade : " + this.cidade + " pais : " + this.pais +
                " Temperatura : " + this.temp + " umidade : " + this.umidade;
    }



}
