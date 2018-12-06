package com.silventino.agenda5;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.EventLog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.silventino.agenda5.Evento;
import com.silventino.agenda5.Grupo;
import com.silventino.agenda5.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class BancoDeDados{
    private static ArrayList<Evento> eventos;
    private static ArrayList<Grupo> grupos;
    private static ArrayList<Usuario> usuarios;
    private static int proximoIdEvento = 0;
    private static int proximoIdGrupo = 0;

    private static HashMap<String, ArrayList<Evento>> eventosMap;

    private static BancoDeDados INSTANCIA = null;
    private RequestQueue fila;
    private String url = "http://192.168.43.134:5000";
    private String url2 = "http://192.168.43.82:5000";
    private int contador = 0;

    private int id_online = -1;

    private BancoDeDados(Context c) {
        this.eventos = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.usuarios = new ArrayList<>();

        this.eventosMap = new HashMap<>();

//        HurlStack hurlStack = new HurlStack() {
//            @Override
//            protected HttpURLConnection createConnection(URL url) throws IOException {
//                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
//                try {
//                    httpsURLConnection.setSSLSocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory());
//                    httpsURLConnection.setHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return httpsURLConnection;
//            }
//        };

        this.fila = Volley.newRequestQueue(c);

    }

    private String getUrl(){
        this.contador++;
        if(contador % 2 == 0){
            return url;
        }
        return url2;
    }

    public static BancoDeDados getInstancia(Context c) {
        if(INSTANCIA == null) {
            INSTANCIA = new BancoDeDados(c);
        }

        return INSTANCIA;
    }

    public boolean addUsuario(final Usuario u, final CadastroActivity activity){
        this.usuarios.add(u);

        Map<String, String>  params = new HashMap<>();

        try {
            String senha = u.getSenha();
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            byte messageDigestSenhaAdmin[] = hash.digest(senha.getBytes("UTF-8"));

            StringBuilder hexStringSenha = new StringBuilder();
            for (byte b : messageDigestSenhaAdmin) {
                hexStringSenha.append(String.format("%02X", 0xFF & b));
            }
            String senhaCodificada = hexStringSenha.toString();

            params.put("senha", senhaCodificada);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String caminho = "/adicionar/usuario";
        params.put("nome", u.getNome());
        params.put("login", u.getUsuario());
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, getUrl() + caminho, new JSONObject(params),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response addUsuario", response.toString());

                        try {
                            int id = response.getInt("usuario_id");
                            id_online = id;

                            activity.recebeRetornoAddUsuario(id);

                        } catch (JSONException e) {
                            int id = -1;
                            activity.recebeRetornoAddUsuario(id);
                        }
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




//        params.put("senha", senha);

        try {
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            byte messageDigestSenha[] = hash.digest(senha.getBytes("UTF-8"));

            StringBuilder hexStringSenha = new StringBuilder();
            for (byte b : messageDigestSenha) {
                hexStringSenha.append(String.format("%02X", 0xFF & b));
            }
            String senhaCodificada = hexStringSenha.toString();

            params.put("senha", senhaCodificada);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, getUrl() + caminho, new JSONObject(params),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response Login", response.toString());
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


//    public Grupo getGrupoPorId(int id){
////        for(Grupo grupo : grupos){
////            if(grupo.getId() == id){
////                return grupo;
////            }
////        }
////        return null;
//
////        http://192.168.1.3:5000/buscar/grupos/id_ou_user?grupo_id=18&usuario_id=
//
//    }

    public void getEventosGrupo(final Grupo g, final VisualizarGrupoActivity v){
        final String caminho = "/buscar/tarefas/dono";

        JsonArrayRequest JSONRequest = new JsonArrayRequest(Request.Method.GET, getUrl() + caminho + "?dono_id=" + g.getId(), null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Caminhoo",getUrl() + caminho + "?dono_id=" + g.getId());
                        Log.d("Respo getEventosGrupo", response.toString());

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
                                evento.setId(o.getInt("id"));
                                evento.setDono_id(o.getInt("dono_id"));

                                eventos.add(evento);

                                if(g.eventosMap.containsKey(evento.getData())){
                                    if(!g.eventosMap.get(evento.getData()).contains(evento)){
                                        eventosMap.get(evento.getData()).add(evento);
                                    }
                                }
                                else{
                                    ArrayList<Evento> array = new ArrayList();
                                    array.add(evento);
                                    g.eventosMap.put(evento.getData(), array);
                                }

                                Log.d("eventosMap", g.eventosMap.toString());
                                Log.d("eventosMap", "adicionei: " + evento.getData());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        v.refreshCalendar(g.eventosMap);
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
    }



    public void addEvento(final Evento e, final AddTarefaActivity activity, boolean adicionarAGrupo){
        String caminho = "/adicionar/tarefa";
        Map<String, String>  params = new HashMap<>();
        params.put("data", e.getData());
        params.put("horario", e.getHorario());
        params.put("titulo", e.getTitulo());
        params.put("descricao", e.getDescricao());

        if(adicionarAGrupo){
            Log.d("Box", "Dono id aqui dentro" + e.getDono_id());
            params.put("dono_id", e.getDono_id() + "");
        }
        else{
            Log.d("Box", "Entrei errado" + e.getDono_id());
            params.put("dono_id", id_online + "");

        }
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, getUrl() + caminho, new JSONObject(params),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response addEvento", response.toString());
                        try {
                            e.setId(response.getInt("id"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        if(eventosMap.containsKey(e.getData())){
                            eventosMap.get(e.getData()).add(e);
                        }
                        else{
                            ArrayList<Evento> array = new ArrayList();
                            array.add(e);
                            eventosMap.put(e.getData(), array);
                        }

                        activity.fechar();

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



    public ArrayList<Evento> getEventosDoDia(int dia, int mes, int ano){
        if(BancoDeDados.eventosMap.containsKey(ano+"-"+mes+"-"+dia)){

            return BancoDeDados.eventosMap.get(ano+"-"+mes+"-"+dia);
        }

        return new ArrayList<Evento>();
    }

//    public ArrayList<Grupo> getMeusGrupos(int idUsuario) {
//        ArrayList<Grupo> gruposParticipando = new ArrayList<>();
//        for(Grupo grupo : grupos) {
//            if(grupo.participaDoGrupo()) {
//                gruposParticipando.add(grupo);
//            }
//        }
//        return gruposParticipando;
//    }

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

    public void getEventos(final ConteudoCalendario c) {
        String caminho = "/buscar/tarefas/todos/dono";

        JsonArrayRequest JSONRequest = new JsonArrayRequest(Request.Method.GET, getUrl() + caminho + "?dono_id=" + id_online, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response getEventos", response.toString());

                        ArrayList<Evento> eventos = new ArrayList<>();
                        eventosMap = new HashMap<>();

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
                                evento.setId(o.getInt("id"));
                                evento.setDono_id(o.getInt("dono_id"));

                                eventos.add(evento);
                                if(eventosMap.containsKey(evento.getData())){
                                    if(!eventosMap.get(evento.getData()).contains(evento)){
                                        eventosMap.get(evento.getData()).add(evento);
                                    }
                                }
                                else{
                                    ArrayList<Evento> array = new ArrayList();
                                    array.add(evento);
                                    eventosMap.put(evento.getData(), array);
                                }

                                Log.d("eventosMap", eventosMap.toString());
                                Log.d("eventosMap", "adicionei: " + evento.getData());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        c.refreshCalendar(eventosMap);
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
    }

    public void entrarGrupo(Grupo g, final ListViewAdapterGrupo activity){

        String caminho = "/entrar_grupo";

        Map<String, String>  params = new HashMap<>();
        params.put("usuario_id", id_online+"");
        params.put("grupo_id", g.getId()+"");
        params.put("eh_administrador", 0+"");

        Log.d("TENTANDO ENTRAR: ", g.getNome() + " - " + g.getId());
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, getUrl() + caminho, new JSONObject(params),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response entrarGrupo", response.toString());

                        activity.receberRespostaEntrarGrupo();

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

    public void sairGrupo(Grupo g, final VisualizarGrupoActivity activity){
        String caminho = "/sair_grupo";

        Map<String, String>  params = new HashMap<>();
        params.put("usuario_id", id_online+"");
        params.put("grupo_id", g.getId()+"");

        Log.d("TENTANDO SAIR: ", g.getNome() + " - " + g.getId());
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, getUrl() + caminho, new JSONObject(params),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response addEvento", response.toString());

                        activity.receberRespostarSairGrupo();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response6", error.toString());
                        Toast.makeText(activity, "Erro ao sair. Tente novamente...", Toast.LENGTH_SHORT).show();
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


    public void getGrupos(final ConteudoGrupos activity) {
//        Log.i("tamanhooo2222", eventos.size()+ "");
//        /buscar/grupos/id_ou_user?grupo_id=&usuario_id=11
        String caminho = "/buscar/grupos/id_ou_user";

        JsonArrayRequest JSONRequest = new JsonArrayRequest(Request.Method.GET, getUrl() + caminho + "?grupo_id=&usuario_id=" + id_online, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response getGrupos", response.toString());

                        ArrayList<Grupo> grupos = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject o = (JSONObject) response.get(i);

                                String nome = (String) o.get("nome");

                                Grupo g = new Grupo(nome);
                                int idGrupo = o.getInt("dono_id");
                                g.setId(idGrupo);
                                if(o.get("participa").equals("false")){
                                    g.setParticipa(false);
                                }
                                else{
                                    g.setParticipa(true);
                                }

                                grupos.add(g);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        activity.getListaGrupos(grupos);
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


//        return INSTANCIA.grupos;
    }


    public void getMeusGrupos(final AddTarefaActivity activity) {
//        Log.i("tamanhooo2222", eventos.size()+ "");
//        /buscar/grupos/id_ou_user?grupo_id=&usuario_id=11
        String caminho = "/buscar/grupos/participante";

        JsonArrayRequest JSONRequest = new JsonArrayRequest(Request.Method.GET, getUrl() + caminho + "?grupo_id=&usuario_id=" + id_online, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response MeusGrupos", response.toString());

                        ArrayList<Grupo> grupos = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject o = (JSONObject) response.get(i);

                                String nome = (String) o.get("nome");

                                Grupo g = new Grupo(nome);
                                int idGrupo = o.getInt("grupo_id");
                                g.setId(idGrupo);

                                g.setParticipa(true);


                                grupos.add(g);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        activity.getListaGrupos(grupos);
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


//        return INSTANCIA.grupos;
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
