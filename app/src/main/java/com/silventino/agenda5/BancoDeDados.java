package com.silventino.agenda5;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class BancoDeDados{
    private static ArrayList<Evento> eventos;
    private static ArrayList<Grupo> grupos;
    private static int proximoIdEvento = 0;
    private static int proximoIdGrupo = 0;

    private static BancoDeDados INSTANCIA = null;

    private BancoDeDados() {
        this.eventos = new ArrayList<>();
        this.grupos = new ArrayList<>();
    }

    public static BancoDeDados getInstancia() {
        if(INSTANCIA == null) {
            INSTANCIA = new BancoDeDados();
        }

        return INSTANCIA;
    }

    public Grupo getGrupoPorId(int id){
        for(Grupo grupo : grupos){
            if(grupo.getId() == id){
                return grupo;
            }
        }
        return null;
    }

    public void addEvento(Evento e){
        this.eventos.add(e);
    }

    public void addGrupo(Grupo g){
        this.grupos.add(g);
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

    public ArrayList<Grupo> getMeusGrupos(int idUsuario) {
        ArrayList<Grupo> gruposParticipando = new ArrayList<>();
        for(Grupo grupo : grupos) {
            if(grupo.participaDoGrupo()) {
                gruposParticipando.add(grupo);
            }
        }
        return gruposParticipando;
    }

    public static int getProximoIdEvento(){
        int retorno = proximoIdEvento;
        proximoIdEvento++;
        return retorno;
    }
    public static int getProximoIdGrupo(){
        int retorno = proximoIdGrupo;
        proximoIdGrupo++;
        return retorno;
    }

    public ArrayList<Evento> getEventos() {
        Log.i("tamanhooo2222", eventos.size()+ "");
        return INSTANCIA.eventos;
    }
    public ArrayList<Grupo> getGrupos() {
        Log.i("tamanhooo2222", eventos.size()+ "");
        return INSTANCIA.grupos;
    }



}
