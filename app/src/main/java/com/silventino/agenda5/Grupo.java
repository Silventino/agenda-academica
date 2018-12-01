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


//        this.participa = true;


        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos989898", "top"));
        eventos.add(new Evento(14,11,2018, 12,12,"estudos2", "top"));
        eventos.add(new Evento(12,11,2018, 12,12,"estudos3", "top"));
    }

    public ArrayList<Evento> getEventosDoDia(int dia, int mes, int ano){
        ArrayList<Evento> eventosDoDia = new ArrayList<>();
        for(Evento evento : eventos){
            if(evento.dataIgual(dia, mes, ano)){
                eventosDoDia.add(evento);
            }
        }
        return eventosDoDia;
    }

    public void addEvento(Evento e){
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

    public boolean participaDoGrupo() {
        return participa;
    }

    public void setParticipa(boolean participa, BancoDeDados b) {
        if(participa == true){
            for(Evento e : this.eventos){
                // TODO fazer um add eventoS
                b.addEvento(e);
            }
        } else {
            for(Evento e : this.eventos) {
                b.removeEvento(e);
            }
        }
        this.participa = participa;
    }
}
