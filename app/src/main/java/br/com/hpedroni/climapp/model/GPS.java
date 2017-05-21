package br.com.hpedroni.climapp.model;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Hilde on 20/05/17.
 *
 * Classe responsável por buscar a localização via GPS.
 */
//Permissões são consedidas na SplashScreen, portanto não é necessário perguntar neste ponto.
@SuppressWarnings("MissingPermission")
public class GPS {

    private Context context;
    private Location currentLocation;


    public GPS(Context context) {
        this.context = context;
        getLocation();
    }


    private void getLocation() {
        //Tentando buscar em primeiro lugar a ultima localização conhecida
        LocationManager mLManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        setLocation(mLManager.getLastKnownLocation(locationProvider));

        //Caso a localização não exista, começamos a escutar o GPS para pegar a localização.
        //Para conservar a bateria, é feito um update a cada 30 min
        if(this.currentLocation == null){
            LocationListener mListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    setLocation(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}
            };
            //Updates a cada 30 min.
            mLManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 500, mListener);
        }
    }
    //Metodo que atualiza o objeto Location
    private void setLocation(Location location) {
        this.currentLocation = location;
    }

    //Retorna a latitude obtida.
    public double getLatitude(){
        return  currentLocation.getLatitude();
    }
    //Retorna a longitude obtida.
    public double getLongitude(){
        return currentLocation.getLongitude();
    }



}
