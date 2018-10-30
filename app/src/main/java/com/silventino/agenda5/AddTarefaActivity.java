package com.silventino.agenda5;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddTarefaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText campoData;

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        mes++;
        Toast.makeText(this, ""+ano+"/"+mes+"/"+dia, Toast.LENGTH_SHORT).show();
        campoData.setText(dia+"/"+mes+"/"+ano);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        campoData = findViewById(R.id.campoData);
        campoData.setInputType(InputType.TYPE_NULL);


        final AddTarefaActivity ponteiroThis = this;
        campoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog newFragment = new DatePickerDialog(ponteiroThis, ponteiroThis, 2018, 10, 12);
                newFragment.show();
            }
        });

    }
}
