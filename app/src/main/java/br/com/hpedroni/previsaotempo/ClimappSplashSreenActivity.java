package br.com.hpedroni.previsaotempo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 * Classe criada para apresentar o app e lidar com as permissões das versões
 *  M em diante.
 */

public class ClimappSplashSreenActivity extends AppCompatActivity {


    private static final int REQUEST_READ_GPS_RESULT = 1;
    private static final long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);


        /*
            Permissões no android 6 e superior devem ser solicitadas em tempo de execução.
             Foi adicionado aqui, porque estava dando conflito com a autenticação do facebook na tela de login.
             Também foi adicionado aqui para melhor organização do código.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "Permissão necessária para utilizar o aplicativo", Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_READ_GPS_RESULT);
            }else{
                vaiParaLogin();
            }
        } else {
            vaiParaLogin();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_GPS_RESULT: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Caso o usuário aceite, ele é direcionado para a tela de login.
                    vaiParaLogin();
                } else {
                    Toast.makeText(ClimappSplashSreenActivity.this, "Impossível continuar sem essa permissão", Toast.LENGTH_LONG).show();
                    //Caso o usuário não aceite dar a pemissão, finalizamos a aplicação pois ela não funcionará
                    finish();
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    //Caso o usuário já tenha dado as permissões necessárias, ao abrir a aplicação, verá a tela
    // de splash por 3 segundos..
    private void vaiParaLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toLogin = new Intent(ClimappSplashSreenActivity.this, ClimappLoginActivity.class);
                startActivity(toLogin);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }


}


