package com.silventino.agenda5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;

public class VisualizarGrupoActivity extends AppCompatActivity {
    private MaterialCalendarView calendario;
    private ListView listaTarefasGrupo;
    private Grupo grupo;
    private DecoradorDeDias decorador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);




        int idGrupo = getIntent().getIntExtra("idGrupo", -1);
        String nomeGrupo = getIntent().getStringExtra("nomeGrupo");

        this.grupo = new Grupo(nomeGrupo);
        this.grupo.setId(idGrupo);

//        Toast.makeText(this, idGrupo+"", Toast.LENGTH_SHORT).show();
//        grupo = BancoDeDados.getInstancia(getApplicationContext()).getGrupoPorId(idGrupo);
        if(nomeGrupo == null || idGrupo == -1){
            Toast.makeText(this, "Erro ao procurar grupo", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // coloca a setinha no toolbar e o nome do grupo
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(grupo.getNome());

        FloatingActionButton btnAddTarefa = findViewById(R.id.fabAddTarefaGrupo);
        btnAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisualizarGrupoActivity.this, AddTarefaActivity.class);
                i.putExtra("idGrupo", grupo.getId());
                startActivity(i);
            }
        });
        calendario = findViewById(R.id.calendarioGrupo);
        decorador = new DecoradorDeDias(Color.RED, null);
        calendario.addDecorator(decorador);
        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay diaSelecionado, boolean b) {
                refreshListaTarefas(diaSelecionado);
            }
        });

        listaTarefasGrupo = findViewById(R.id.listaTarefasGrupo);
        calendario.setSelectedDate(LocalDate.now());

        BancoDeDados.getInstancia(getApplicationContext()).getEventosGrupo(this.grupo, this);
    }

    // seta a setinha pra voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
//        if (item.getItemId() == android.R.id.home) {
//            finish(); // close this activity and return to preview activity (if there is any)
//        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.sair:
                confirmaSaida();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void refreshCalendar(HashMap<String, ArrayList<Evento>> eventos){
        Log.d("Cá estou!", "oia aqui");

        for(String key : eventos.keySet()){
            for(Object evento : eventos.get(key)){
                Log.d("Eventos", ((Evento) evento).getData());
                decorador.addDate(((Evento) evento).getCalendarDay());
            }
        }
        calendario.invalidateDecorators();
    }

    public void confirmaSaida() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sair do grupo");
        dialog.setMessage("Caso saia deste grupo todas as atividades pertencentes a ele serão removidas das sua agenda individual. " +
                          "Tem certeza que deseja continuar?");


        final Context context = this;
        final VisualizarGrupoActivity ponteiroThis = this;
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BancoDeDados.getInstancia(getApplicationContext()).sairGrupo(grupo, ponteiroThis);

            }
        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Saida cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }

    public void receberRespostarSairGrupo(){
        Toast.makeText(this, "Saiu do grupo", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(calendario.getSelectedDate() != null){
            refreshListaTarefas(calendario.getSelectedDate());
        }

        ArrayList<Evento> eventos = grupo.getEventos();
        for(Evento evento : eventos){
            decorador.addDate(evento.getCalendarDay());
        }
        calendario.invalidateDecorators();

    }

    public void refreshListaTarefas(CalendarDay data){
        // TODO mudar isso auqi pro banco de dados
        ArrayList<Evento> eventosDoDia = grupo.getEventosDoDia(data.getDay(), data.getMonth(), data.getYear());
//        Log.i("asdasdasd", eventosDoDia.toString());
        ListViewAdapterEvento lva = new ListViewAdapterEvento(eventosDoDia, calendario.getContext());
        listaTarefasGrupo.setAdapter(lva);
        lva.notifyDataSetChanged();
        setListViewHeightBasedOnChildren();

    }

    public void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = listaTarefasGrupo.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listaTarefasGrupo.getPaddingTop() + listaTarefasGrupo.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listaTarefasGrupo.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listaTarefasGrupo);


            if(listItem != null){
//                 This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

//                Log.i("medida", listItem.getHeight()+"");
                //TODO arrumar o calculo do tamanho do bagulho

            }
        }

        ViewGroup.LayoutParams params = listaTarefasGrupo.getLayoutParams();
//        params.height = totalHeight + (listaTarefasGrupo.getDividerHeight());
        params.height = totalHeight + (listaTarefasGrupo.getDividerHeight() * (listAdapter.getCount() - 1));
        listaTarefasGrupo.setLayoutParams(params);
        listaTarefasGrupo.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_grupo, menu);
        return true;
    }

}
