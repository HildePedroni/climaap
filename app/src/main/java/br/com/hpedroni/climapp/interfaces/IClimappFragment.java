package br.com.hpedroni.climapp.interfaces;

import android.content.Context;

import br.com.hpedroni.climapp.model.InfoClima;

/**
 * Created by Hilde on 20/05/17.
 */

public interface IClimappFragment {

    interface view {
        void showLoading();

        void showData(InfoClima infoClima);

        void showToast(String msg);

        void hideLoading();
    }

    interface presenter {
        void getWeather(String local, Context context);
    }


}
