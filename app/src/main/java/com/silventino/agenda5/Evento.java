package com.silventino.agenda5;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.Serializable;

public class Evento implements Serializable{
    private int dia;
    private int mes;
    private int ano;
    private int hora;
    private int minuto;
    private String titulo;
    private String descricao;
    // private Grupo grupo;
    private int id;

    public Evento(int dia, int mes, int ano, int hora, int minuto, String titulo, String descricao) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.hora = hora;
        this.minuto = minuto;
        this.titulo = titulo;
        this.descricao = descricao;
        this.id = BancoDeDados.getProximoIdEvento();
    }

    public boolean dataIgual(int dia, int mes, int ano){
        if(this.ano == ano && this.mes == mes && this.dia == dia){
            return true;
        }
        return false;
    }

    public CalendarDay getCalendarDay(){
        return CalendarDay.from(this.ano, this.mes, this.dia);
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getId() {
        return id;
    }
}
