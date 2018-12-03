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
    private Grupo grupo;
    private int id;
    private int dono_id;

    private String dono;


    public Evento(int dia, int mes, int ano, int hora, int minuto, String titulo, String descricao) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.hora = hora;
        this.minuto = minuto;
        this.titulo = titulo;
        this.descricao = descricao;
        this.id = -1;
        this.dono_id = -1;
        this.dono = "";
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setDono_id(int id){
        this.dono_id = id;
    }
    public int getDono_id(){
        return this.dono_id;
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

    public void setGrupo(Grupo g){
        this.grupo = g;
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

    public String getData(){
        return (ano + "-" + mes + "-" + dia);
    }

    public String getHorario(){
        return (hora + ":" + minuto + ":00");
    }

    public static String getDataFromInts(int dia, int mes, int ano){
        return (ano + "-" + mes + "-" + dia);
    }

    public static int getDiaFromData(String data){
        String[] partes = data.split("-");

        return Integer.valueOf(partes[2]);
    }
    public static int getMesFromData(String data){
        String[] partes = data.split("-");

        return Integer.valueOf(partes[1]);
    }
    public static int getAnoFromData(String data){
        String[] partes = data.split("-");

        return Integer.valueOf(partes[0]);
    }
    public static int getHoraFromHorario(String horario){
        String[] partes = horario.split(":");

        return Integer.valueOf(partes[0]);

    }
    public static int getMinutoFromHorario(String horario){
        String[] partes = horario.split(":");

        return Integer.valueOf(partes[1]);
    }

    @Override
    public boolean equals(Object obj) {
        Evento e = (Evento) obj;
        if(e.id == this.id){
            return true;
        }
        return false;
    }
}
