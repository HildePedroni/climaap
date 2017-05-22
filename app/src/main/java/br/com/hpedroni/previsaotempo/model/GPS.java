package br.com.hpedroni.previsaotempo.model;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

/**
 * Created by Hilde on 20/05/17.
 * <p>
 * Classe responsável por buscar a localização via GPS.
 */
//Permissões são consedidas na SplashScreen, portanto não é necessário perguntar neste ponto.
@SuppressWarnings("MissingPermission")
public class GPS {



    private Context context;

    private Location mLocation;

    public GPS(Context context) {
        this.context = context;
        //Busca ultima localização conhecida.
        mLocation = getLastKnownLocation();
    }


    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Retorna null, vai carregar clima default
            return null;
        } else {
            List<String> providers = mLocationManager.getProviders(true);
            Location location = null;
            for (String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (location == null || l.getAccuracy() < location.getAccuracy()) {
                    location = l;
                }
            }
            return location;
        }
    }

    public Location getLocation(){
        return this.mLocation;
    }




}
