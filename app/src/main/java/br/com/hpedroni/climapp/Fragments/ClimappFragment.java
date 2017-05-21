package br.com.hpedroni.climapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.hpedroni.climapp.ClimappFragmentsPresenter;
import br.com.hpedroni.climapp.R;
import br.com.hpedroni.climapp.interfaces.IClimappFragment;
import br.com.hpedroni.climapp.model.InfoClima;

/**
 * Created by Hilde on 20/05/17.
 *
 * Optei por utilizar fragments para aproveitar tanto o layout como o código que é exatamente o mesmo
 * nas duas telas.
 *
 *
 */

public class ClimappFragment extends Fragment implements IClimappFragment.view{


    private IClimappFragment.presenter presenter;

    private TextView cidade;
    private TextView temp;
    private TextView umidade;
    private TextView txtLoading;
    private LinearLayout containerUmidade;


    private String local;

    public static ClimappFragment newInstance(String local) {
        ClimappFragment frag = new ClimappFragment();
        Bundle b = new Bundle();
        b.putString("local", local);
        frag.setArguments(b);
        return frag;
    }

    public ClimappFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local = getArguments().getString("local");
        presenter = new ClimappFragmentsPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.previsao_tempo_fragment_layout, container, false);

        cidade = (TextView) rootView.findViewById(R.id.local);
        temp = (TextView) rootView.findViewById(R.id.temp);
        umidade = (TextView) rootView.findViewById(R.id.umidade);
        containerUmidade = (LinearLayout) rootView.findViewById(R.id.container_umidade);
        txtLoading = (TextView) rootView.findViewById(R.id.txt_loading);

        //Busca os dados no server
        presenter.getWeather(this.local, this.getContext());

        return rootView;
    }

    @Override
    public void showLoading() {
        cidade.setVisibility(View.INVISIBLE);
        temp.setVisibility(View.INVISIBLE);
        containerUmidade.setVisibility(View.INVISIBLE);
        txtLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        cidade.setVisibility(View.VISIBLE);
        temp.setVisibility(View.VISIBLE);
        containerUmidade.setVisibility(View.VISIBLE);
        txtLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showData(InfoClima infoClima) {
        cidade.setText(infoClima.getLocal());
        temp.setText(infoClima.getTemp());
        umidade.setText(infoClima.getUmidade());
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG).show();
    }


}
