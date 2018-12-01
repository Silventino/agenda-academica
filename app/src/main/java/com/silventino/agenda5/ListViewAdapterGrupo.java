package com.silventino.agenda5;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapterGrupo extends ArrayAdapter<Grupo> {
    private static class ViewHolder {
        TextView txtNome;
        Button btnEntrar;
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
        final Grupo grupo = getItem(posicao);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ListViewAdapterGrupo.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_grupos, pai, false);
            viewHolder.txtNome = convertView.findViewById(R.id.nomeGrupo);
            viewHolder.btnEntrar = convertView.findViewById(R.id.btnEntrar);
            viewHolder.imgGrupo = convertView.findViewById(R.id.imgGrupo);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListViewAdapterGrupo.ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtNome.setText(grupo.getNome());

        if(grupo.participaDoGrupo()) {
            viewHolder.btnEntrar.setVisibility(View.INVISIBLE);
            viewHolder.btnEntrar.setClickable(false);
        } else {
            viewHolder.btnEntrar.setVisibility(View.VISIBLE);
            viewHolder.btnEntrar.setClickable(true);
        }

        viewHolder.btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grupo.setParticipa(true, BancoDeDados.getInstancia(getContext().getApplicationContext()));
                viewHolder.btnEntrar.setVisibility(View.INVISIBLE);
                viewHolder.btnEntrar.setClickable(false);
                Intent i = new Intent(view.getContext(), VisualizarGrupoActivity.class);
                i.putExtra("idGrupo", grupo.getId());
                view.getContext().startActivity(i);
                // TODO abrir grupo ao entrar
                Toast.makeText(context, "Você entrou no grupo! Clique nele para acessá-lo", Toast.LENGTH_SHORT).show();

            }
        });
        viewHolder.btnEntrar.setTag(posicao);
        viewHolder.imgGrupo.setTag(posicao);
        // Return the completed view to render on screen
        return convertView;
    }


}
