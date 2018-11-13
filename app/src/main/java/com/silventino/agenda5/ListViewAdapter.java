package com.silventino.agenda5;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Evento> {
    private ArrayList<Evento> listaEventos;
    private Context context;

    public ListViewAdapter(ArrayList<Evento> listaEventos, Context context) {
        super(context, R.layout.activity_add_event, listaEventos);
        this.listaEventos = listaEventos;
        this.context = context;
    }


    @Override
    public View getView(int posicao, View convertView, ViewGroup pai) {
        Evento evento = getItem(posicao);


        TextView txtTeste;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.activity_add_event, pai, false);
        txtTeste = (TextView) convertView.findViewById(R.id.)

    }


}
