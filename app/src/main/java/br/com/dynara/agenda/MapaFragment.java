package br.com.dynara.agenda;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.dynara.agenda.dao.AlunoDAO;
import br.com.dynara.agenda.modelo.Aluno;


public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback{

    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap mapa) {
        this.mapa = mapa;
        LatLng posicaoDaEscola = pegaCoordenada("Rua Vergueiro 3185, Vila Mariana, Sao Paulo");
        if (posicaoDaEscola != null) {
            centralizaEm(posicaoDaEscola);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()) {
            LatLng posicaoDoAluno = pegaCoordenada(aluno.getEndereco());

            if (posicaoDoAluno != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(posicaoDoAluno);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                this.mapa.addMarker(marcador);
            }
        }
        alunoDAO.close();

        new Localizador(getContext(), this);

    }

    public void centralizaEm(LatLng coordenada) {
        if (this.mapa!=null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            this.mapa.moveCamera(cameraUpdate);
        }
    }

    private LatLng pegaCoordenada(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
