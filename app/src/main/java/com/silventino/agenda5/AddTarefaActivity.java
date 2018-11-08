package com.silventino.agenda5;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddTarefaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText campoData;
    private EditText campoHora;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        campoData = findViewById(R.id.campoData);
        campoHora = findViewById(R.id.campoHora);
        campoData.setInputType(InputType.TYPE_NULL);


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

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String minutos = String.format("%02d", i1);
        String horas = String.format("%02d", i);
        campoHora.setText(horas + ":" + minutos);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        mes++;
        Toast.makeText(this, ""+ano+"/"+mes+"/"+dia, Toast.LENGTH_SHORT).show();
        campoData.setText(dia+"/"+mes+"/"+ano);

    }
}
