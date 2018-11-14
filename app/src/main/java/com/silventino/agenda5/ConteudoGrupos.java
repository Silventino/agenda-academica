package com.silventino.agenda5;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ConteudoGrupos extends Fragment {

    private ListView listaGrupos;

    public ConteudoGrupos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conteudo_grupos, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaGrupos = view.findViewById(R.id.listaGrupos);

        ArrayList<Grupo> gs = BancoDeDados.getInstancia().getGrupos();

        ListViewAdapterGrupo lva = new ListViewAdapterGrupo(gs, view.getContext());
        listaGrupos.setAdapter(lva);
        listaGrupos.setFocusable(false);
    }
}
