package com.silventino.agenda5;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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

    private BancoDeDados bancoDeDados;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

//        Intent intent = getIntent();
        bancoDeDados = BancoDeDados.getInstancia();

        campoData = findViewById(R.id.campoData);
        campoHora = findViewById(R.id.campoHora);
        campoDescricao = findViewById(R.id.campoDescricao);
        campoTitulo = findViewById(R.id.campoTitulo);
        campoData.setInputType(InputType.TYPE_NULL);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bancoDeDados.addEvento(criarEvento());
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
