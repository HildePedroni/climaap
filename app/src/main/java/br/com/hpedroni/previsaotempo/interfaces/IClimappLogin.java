package br.com.hpedroni.previsaotempo.interfaces;

import android.content.Context;

import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;

/**
 * Created by Hilde on 20/05/17.
 */

public interface IClimappLogin {

    interface view {
        void showToast(String msg);

        void direcionaParaClima();

        void finaliza();

        void falhaAutenticacao();
    }

    interface presenter {
        void start();

        void stop();

        void configure();

        void handleFacebookAccessToken(LoginResult loginResult, Context context);

        CallbackManager getCallBackManager();

        void logout();

    }

}
