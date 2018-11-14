package com.silventino.agenda5;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTarefaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText campoData;
    private EditText campoHora;
    private EditText campoTitulo;
    private EditText campoDescricao;
    private Button btnAdicionar;

    private int diaSelecionado;
    private int mesSelecionado;
    private int anoSelecionado;

    private int horaSelecionada;
    private int minutoSelecionado;

    private CheckBox checkBox;
    private Spinner seletorGrupos;

    private BancoDeDados bancoDeDados;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        int idGrupo = getIntent().getIntExtra("idGrupo", -1);

        bancoDeDados = BancoDeDados.getInstancia();

        campoData = findViewById(R.id.campoData);
        campoHora = findViewById(R.id.campoHora);
        campoDescricao = findViewById(R.id.campoDescricao);
        campoTitulo = findViewById(R.id.campoTitulo);
        campoData.setInputType(InputType.TYPE_NULL);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        // TODO fazer essa funcao receber um usuario cadastrado
        final ArrayList<Grupo> grupos = BancoDeDados.getInstancia().getMeusGrupos(-1);

        ArrayList<String> nomeGrupos = new ArrayList<>();
        for(int i = 0; i < grupos.size(); i++) {
            nomeGrupos.add(grupos.get(i).getNome());
        }


        // TODO fazer adapter para o spinner
        seletorGrupos = findViewById(R.id.seletorGrupos);
        ArrayAdapter<String> adapterSeletorGrupos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomeGrupos);
        adapterSeletorGrupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorGrupos.setAdapter(adapterSeletorGrupos);

        checkBox = findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    seletorGrupos.setVisibility(View.VISIBLE);
                    return;
                }
                seletorGrupos.setVisibility(View.INVISIBLE);
            }
        });


        if(idGrupo > -1) {
            int indiceGrupoSelecionado = -1;
            checkBox.setChecked(true);
            for(int i = 0; i < grupos.size() && indiceGrupoSelecionado < 0; i++) {
                if(idGrupo == grupos.get(i).getId()) {
                    indiceGrupoSelecionado = i;
                }
                Log.i("valorRRRRRR de I", i + "");
                Log.i("nomeEEEEEEEEE", grupos.get(i).getNome());
            }
            seletorGrupos.setSelection(indiceGrupoSelecionado);
        }
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoTitulo.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "O título não pode ser vazio", Toast.LENGTH_SHORT);
                    return;
                }
                if(campoData.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "A data não pode ser vazio", Toast.LENGTH_SHORT);
                    return;
                }
                if(campoHora.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "A hora não pode ser vazio", Toast.LENGTH_SHORT);
                    return;
                }
                if(checkBox.isChecked()){
                    Grupo grupoSelecionado = grupos.get(seletorGrupos.getSelectedItemPosition());
                    Evento e = criarEvento();
                    e.setGrupo(grupoSelecionado);
                    grupoSelecionado.addEvento(e);
                    BancoDeDados.getInstancia().addEvento(e);
                }
                else{
                    bancoDeDados.addEvento(criarEvento());
                }
                Toast.makeText(getApplicationContext(), "Tarefa agendada", Toast.LENGTH_SHORT).show();
                finish();

            }
        });



        final AddTarefaActivity ponteiroThis = this;
        campoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(ponteiroThis, ponteiroThis, 2018, 10, 12);
                datePicker.show();
            }
        });

        campoHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(ponteiroThis, ponteiroThis, 20, 20, true);
                timePicker.show();
            }
        });

    }

    public Evento criarEvento(){
        String titulo = campoTitulo.getText().toString();
        String descricao = campoDescricao.getText().toString();
//        Log.i("UAUUUUUUUU", titulo + "\n" + descricao + "\n" + diaSelecionado + "/" + mesSelecionado + "/" + anoSelecionado + "\n" + horaSelecionada + ":" + minutoSelecionado);
        Evento novoEvento = new Evento(diaSelecionado, mesSelecionado, anoSelecionado, horaSelecionada, minutoSelecionado, titulo, descricao);
        return novoEvento;

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int horas, int minutos) {
        String minutosString = String.format("%02d", minutos);
        String horasString = String.format("%02d", horas);
        campoHora.setText(horasString + ":" + minutosString);
        horaSelecionada = horas;
        minutoSelecionado = minutos;

    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        mes++;
        Toast.makeText(this, ""+ano+"/"+mes+"/"+dia, Toast.LENGTH_SHORT).show();
        campoData.setText(dia+"/"+mes+"/"+ano);
        this.diaSelecionado = dia;
        this.mesSelecionado = mes;
        this.anoSelecionado = ano;

    }
}
