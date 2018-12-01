package com.silventino.agenda5;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BancoDeDados{
    private static ArrayList<Evento> eventos;
    private static ArrayList<Grupo> grupos;
    private static ArrayList<Usuario> usuarios;
    private static int proximoIdEvento = 0;
    private static int proximoIdGrupo = 0;

    private static BancoDeDados INSTANCIA = null;


    private RequestQueue fila;
    private String url = "http://192.168.1.3:5000";

    private BancoDeDados(Context c) {
        this.eventos = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.fila = Volley.newRequestQueue(c);
    }

    public static BancoDeDados getInstancia(Context c) {
        if(INSTANCIA == null) {
            INSTANCIA = new BancoDeDados(c);
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

    public boolean addUsuario(final Usuario u){
        this.usuarios.add(u);
        String caminho = "/adicionar/usuario";

        Map<String, String>  params = new HashMap<>();
        params.put("name", u.getNome());
        params.put("login", u.getUsuario());
        params.put("senha", u.getSenha());


        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, url + caminho, new JSONObject(params),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        fila.add(JSONRequest);

        return true;
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


    public void removeEvento(Evento e) {
        for(int i = 0; i < this.eventos.size(); i++) {
            if(this.eventos.get(i).getId() == e.getId()) {
                this.eventos.remove(i);
                i--;
            }
        }
    }
}
