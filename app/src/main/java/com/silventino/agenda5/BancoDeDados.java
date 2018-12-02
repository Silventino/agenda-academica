package com.silventino.agenda5;

import android.content.Context;
import android.util.EventLog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.silventino.agenda5.Evento;
import com.silventino.agenda5.Grupo;
import com.silventino.agenda5.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
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

    private int id_online = -1;

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

    public boolean addUsuario(final Usuario u){
        this.usuarios.add(u);
        String caminho = "/adicionar/usuario";
        Map<String, String>  params = new HashMap<>();
        params.put("nome", u.getNome());
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
                        Log.d("Error.Response4", error.toString());
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

    public void login(String login, String senha, final LoginActivity telaDeLogin){
        String caminho = "/login";
        Map<String, String>  params = new HashMap<>();
        params.put("login", login);
        params.put("senha", senha);
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, url + caminho, new JSONObject(params),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            int id = response.getInt("usuario_id");
                            id_online = id;
                            telaDeLogin.receberLogin(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response5", error.toString());
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
        String caminho = "/adicionar/tarefa";
        Map<String, String>  params = new HashMap<>();
        params.put("data", e.getData());
        params.put("horario", e.getHorario());
        params.put("titulo", e.getTitulo());
        params.put("descricao", e.getDescricao());
        params.put("dono_id", id_online + "");
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
                        Log.d("Error.Response6", error.toString());
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


    }

    public void addGrupo(Grupo g){
        this.grupos.add(g);
    }

    public void getEventosDoDia(int dia, int mes, int ano, final ConteudoCalendario c){
//        ArrayList<Evento> eventosDoDia = new ArrayList<>();
//        for(Evento evento : eventos){
//            if(evento.dataIgual(dia, mes, ano)){
//                eventosDoDia.add(evento);
//            }
//        }
//        return eventosDoDia;
        String caminho = "/buscar/tarefas/data";
        String data = Evento.getDataFromInts(dia, mes, ano);
        JsonArrayRequest JSONRequest = new JsonArrayRequest(Request.Method.GET, url + caminho + "?dono_id=" + id_online + "&data=" + data, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());

                        ArrayList<Evento> eventos = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject o = (JSONObject) response.get(i);
                                int dia = Evento.getDiaFromData((String)o.get("data"));
                                int mes = Evento.getMesFromData((String)o.get("data"));
                                int ano = Evento.getAnoFromData((String)o.get("data"));
                                int hora = Evento.getHoraFromHorario((String)o.get("horario"));
                                int minuto = Evento.getMinutoFromHorario((String)o.get("horario"));
                                String titulo = (String)o.get("titulo");
                                String descricao = (String)o.get("descricao");
                                int idDono = (int)o.get("dono_id");

                                Evento evento = new Evento(dia,mes,ano,hora,minuto,titulo,descricao);
                                eventos.add(evento);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("eventos:", eventos.toString());
                        c.getRetornoListaTarefas(eventos);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response2", error.toString());
                    }
                }
        );

        fila.add(JSONRequest);

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

    public ArrayList<Evento> getEventos(final ConteudoCalendario c) {
        String caminho = "/buscar/tarefas/dono";

        JsonArrayRequest JSONRequest = new JsonArrayRequest(Request.Method.GET, url + caminho + "?dono_id=" + id_online, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());

                        ArrayList<Evento> eventos = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject o = (JSONObject) response.get(i);
                                int dia = Evento.getDiaFromData((String)o.get("data"));
                                int mes = Evento.getMesFromData((String)o.get("data"));
                                int ano = Evento.getAnoFromData((String)o.get("data"));
                                int hora = Evento.getHoraFromHorario((String)o.get("horario"));
                                int minuto = Evento.getMinutoFromHorario((String)o.get("horario"));
                                String titulo = (String)o.get("titulo");
                                String descricao = (String)o.get("descricao");
                                int idDono = (int)o.get("dono_id");

                                Evento evento = new Evento(dia,mes,ano,hora,minuto,titulo,descricao);
                                eventos.add(evento);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        c.refreshCalendar(eventos);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response3", error.toString());
                    }
                }
        );

        fila.add(JSONRequest);



//        Log.i("tamanhooo2222", eventos.size()+ "");
        return INSTANCIA.eventos;
    }
    public ArrayList<Grupo> getGrupos() {
//        Log.i("tamanhooo2222", eventos.size()+ "");
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
