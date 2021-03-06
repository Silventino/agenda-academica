package com.silventino.agenda5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
    public void onResume() {
        super.onResume();
        //refresh nos dias listados
        refreshListaGrupos();

    }

    public void refreshListaGrupos(){
        BancoDeDados.getInstancia(getActivity().getApplicationContext()).getGrupos(this);

    }

    public void getListaGrupos(final ArrayList<Grupo> grupos){
        Log.i("asdasdasd", grupos.toString());
        ListViewAdapterGrupo lvag = new ListViewAdapterGrupo(grupos, getView().getContext());
        listaGrupos.setAdapter(lvag);
        lvag.notifyDataSetChanged();
        listaGrupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Grupo g = grupos.get(pos);
                Intent i = new Intent(getActivity(), VisualizarGrupoActivity.class);
                i.putExtra("idGrupo", g.getId());
                i.putExtra("nomeGrupo", g.getNome());
                startActivity(i);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaGrupos = view.findViewById(R.id.listaGrupos);
        BancoDeDados.getInstancia(getActivity().getApplicationContext()).getGrupos(this);

//        ListViewAdapterGrupo lvag = new ListViewAdapterGrupo(gs, view.getContext());
//        listaGrupos.setAdapter(lvag);
//        listaGrupos.setFocusable(false);



    }
}
