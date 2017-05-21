package br.com.hpedroni.climapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import br.com.hpedroni.climapp.interfaces.IClimappLogin;

public class ClimappLoginActivity extends AppCompatActivity implements IClimappLogin.view {

    private LoginButton loginButton;
    private TextView txtDireciona;
    IClimappLogin.presenter presenter;

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new ClimappLoginPresenter(this);
        txtDireciona = (TextView) findViewById(R.id.txt_redireciona);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        Intent intent = getIntent();
        if (intent.hasExtra("logout")) {
            //Finaliza o aplicativo pois o usuario deu logout.
            presenter.logout();
        }

        //Facebook
        presenter.configure();

        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(presenter.getCallBackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                txtDireciona.setVisibility(View.VISIBLE);
                presenter.handleFacebookAccessToken(loginResult, ClimappLoginActivity.this);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.getCallBackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void direcionaParaClima() {
        Intent toClima = new Intent(this, ClimappContainerActivity.class);
        startActivity(toClima);
        finish();
    }

    @Override
    public void finaliza() {
        finish();
    }

    @Override
    public void falhaAutenticacao() {
        txtDireciona.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        showToast("Falha de autenticação, tente novamente.");

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}


