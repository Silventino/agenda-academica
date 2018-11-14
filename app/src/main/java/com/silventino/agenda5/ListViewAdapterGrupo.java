package com.silventino.agenda5;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapterGrupo extends ArrayAdapter<Grupo> {
    private static class ViewHolder {
        TextView txtNome;
        ImageView info;
        ImageView imgGrupo;
    }

    private ArrayList<Grupo> listaGrupos;
    private Context context;

    public ListViewAdapterGrupo(ArrayList<Grupo> listaEventos, Context context) {
        super(context, R.layout.row_grupos, listaEventos);
        this.listaGrupos = listaEventos;
        this.context = context;
    }


    @Override
    public View getView(int posicao, View convertView, ViewGroup pai) {
        // Get the data item for this position
        Grupo grupo = getItem(posicao);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ListViewAdapterGrupo.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_grupos, pai, false);
            viewHolder.txtNome = convertView.findViewById(R.id.nomeGrupo);
            viewHolder.info = convertView.findViewById(R.id.item_info_grupo);
            viewHolder.imgGrupo = convertView.findViewById(R.id.imgGrupo);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListViewAdapterGrupo.ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtNome.setText(grupo.getNome());
        viewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "UAU! QUE LOUCO MAN", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.info.setTag(posicao);
        viewHolder.imgGrupo.setTag(posicao);
        // Return the completed view to render on screen
        return convertView;
    }



}
