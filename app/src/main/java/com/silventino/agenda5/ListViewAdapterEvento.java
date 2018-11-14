package com.silventino.agenda5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapterEvento extends ArrayAdapter<Evento> {
    private static class ViewHolder {
        TextView txtTitulo;
        TextView txtHora;
        ImageView info;
    }

    private ArrayList<Evento> listaEventos;
    private Context context;

    public ListViewAdapterEvento(ArrayList<Evento> listaEventos, Context context) {
        super(context, R.layout.row_tarefas, listaEventos);
        this.listaEventos = listaEventos;
        this.context = context;
    }


    @Override
    public View getView(int posicao, View convertView, ViewGroup pai) {
        // Get the data item for this position
        Evento evento = getItem(posicao);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_tarefas, pai, false);
            viewHolder.txtTitulo = convertView.findViewById(R.id.titulo);
            viewHolder.txtHora = convertView.findViewById(R.id.hora);
            viewHolder.info = convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtTitulo.setText(evento.getTitulo());
        viewHolder.txtHora.setText(evento.getHora() + ":" + evento.getMinuto());
        viewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "UAU! QUE LOUCO MAN", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.info.setTag(posicao);
        // Return the completed view to render on screen
        return convertView;
    }



}
