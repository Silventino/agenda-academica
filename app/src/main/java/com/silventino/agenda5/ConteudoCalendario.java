package com.silventino.agenda5;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;


public class ConteudoCalendario extends Fragment implements DatePickerDialog.OnDateSetListener{

    private MaterialCalendarView calendario;
    private Context c;
    private DecoradorDeDias decorador;
    private FragmentActivity contexto;

    public ConteudoCalendario() {
        // Required empty public constructor
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conteudo_calendario, container, false);
        this.c = inflater.getContext();
        Toast.makeText(c, "OK", Toast.LENGTH_SHORT).show();
        return rootView;
    }

//    @Override
//    public void onAttach(Context context) {
//        this.contexto = (FragmentActivity) context;
//        super.onAttach(context);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendario = view.findViewById(R.id.calendarioo);
        decorador = new DecoradorDeDias(Color.RED, null);
        calendario.addDecorator(decorador);


        Button b = view.findViewById(R.id.btnTeste);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDay diaSelecionado = calendario.getSelectedDate();
                if(diaSelecionado != null){
                    decorador.addDate(diaSelecionado);
                    calendario.invalidateDecorators();
                }
            }
        });

        Button teste = view.findViewById(R.id.btn2);
        final ConteudoCalendario ponteiroThis = this;
        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog newFragment = new DatePickerDialog(getActivity(), ponteiroThis, 2018, 10, 12);
                newFragment.show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Toast.makeText(c, ""+i+"/"+i1+"/"+i2, Toast.LENGTH_SHORT).show();
    }

//    public void showDatePicker(){
//
//    }

//    public void setEvents(){
//        LocalDate temp = LocalDate.now().minusMonths(2);
//        final ArrayList<CalendarDay> dates = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            Evento e = new Evento(temp.getDayOfMonth(),temp.getMonthValue(),temp.getYear(),0, 0, "Thuza", "Tutu", i);
//            final CalendarDay day = e.getCalendarDay();
//            dates.add(day);
//            temp = temp.plusDays(5);
//        }
//        decorador.addDate(dates);
//        calendario.addDecorator(decorador);
//        Log.d("DEBUG", "CHEGUEI");
//
//    }


}
