package com.silventino.agenda5;

import java.util.ArrayList;
import java.util.HashMap;

public class Grupo {
//    private listaDeParticipantes
    private int id;
    private String nome;
    public HashMap<String,ArrayList<Evento>> eventosMap;
    private boolean participa; //TODO arruma isso direito fi

    public Grupo(String nome) {
        this.id = -1;
        this.nome = nome;
        this.eventosMap = new HashMap<>();
        this.participa = false;


//        this.participa = true;


//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos989898", "top"));
//        eventos.add(new Evento(14,11,2018, 12,12,"estudos2", "top"));
//        eventos.add(new Evento(12,11,2018, 12,12,"estudos3", "top"));
    }

    public ArrayList<Evento> getEventosDoDia(int dia, int mes, int ano){
        ArrayList<Evento> eventosDoDia = eventosMap.get(ano+"-"+mes+"-"+dia);
        if(eventosDoDia == null){
            return new ArrayList<Evento>();
        }
        return eventosDoDia;
    }

    public void addEvento(Evento e){
//        eventos.put(e.getData(), e);
        //TODO
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
//        return eventos;
    //TODO
        return new ArrayList<Evento>();
    }


    public boolean participaDoGrupo() {
        return participa;
    }

    public void setParticipa(boolean participa) {

        this.participa = participa;
    }
}
