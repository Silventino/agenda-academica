package com.silventino.agenda5;

import java.util.ArrayList;

public class Grupo {
//    private listaDeParticipantes
    private int id;
    private String nome;
    ArrayList<Evento> eventos;
    private boolean participa; //TODO arruma isso direito fi

    public Grupo(String nome) {
        this.id = BancoDeDados.getProximoIdGrupo();
        this.nome = nome;
        this.eventos = new ArrayList<>();
        this.participa = false;
    }

    public void adicionarEvento(Evento e){
        eventos.add(e);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public boolean isParticipa() {
        return participa;
    }

    public void setParticipa(boolean participa) {
        this.participa = participa;
    }
}
