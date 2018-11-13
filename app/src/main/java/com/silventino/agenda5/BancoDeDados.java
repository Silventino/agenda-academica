package com.silventino.agenda5;

import android.util.Log;

import java.util.ArrayList;

public class BancoDeDados{
    private static ArrayList<Evento> eventos;
    private static int proximoIdEvento = 0;

    private static BancoDeDados INSTANCIA = null;

    private BancoDeDados() {
        this.eventos = new ArrayList<>();
    }

    public void addEvento(Evento e){
        this.eventos.add(e);
        Log.i("tamanhooo", eventos.size()+ "");
    }

    public static int getProximoIdEvento(){
        int retorno = proximoIdEvento;
        proximoIdEvento++;
        return retorno;
    }

    public static BancoDeDados getInstancia() {
        if(INSTANCIA == null) {
            INSTANCIA = new BancoDeDados();
        }

        return INSTANCIA;
    }

    public ArrayList<Evento> getEventos() {
        Log.i("tamanhooo2222", eventos.size()+ "");
        return INSTANCIA.eventos;
    }



}
