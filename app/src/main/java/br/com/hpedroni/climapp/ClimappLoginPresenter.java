package br.com.hpedroni.climapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.hpedroni.climapp.interfaces.IClimappLogin;

/**
 * Created by Hilde on 20/05/17.
 * <p>
 * O objetivo é isolar o codigo de autenticação do facebook.
 */

class ClimappLoginPresenter implements IClimappLogin.presenter {


    private IClimappLogin.view view;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private FirebaseAuth.AuthStateListener mAuthListener;


    ClimappLoginPresenter(IClimappLogin.view view) {
        this.view = view;
    }

    @Override
    public void start() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void stop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void configure() {
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    view.direcionaParaClima();
                }
            }
        };
    }


    public void handleFacebookAccessToken(LoginResult loginResult, Context context) {
        AccessToken token = loginResult.getAccessToken();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            view.showToast("Autenticação falhou!");
                            view.falhaAutenticacao();
                        } else {
                            view.direcionaParaClima();
                        }
                    }
                });
    }

    @Override
    public CallbackManager getCallBackManager() {
        return this.callbackManager;
    }

    @Override
    public void logout() {
        LoginManager.getInstance().logOut();
        view.finaliza();
    }


}
