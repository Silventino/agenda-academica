package com.silventino.agenda5;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;


public class ConteudoCalendario extends Fragment {

    private MaterialCalendarView calendario;
    private Context c;

    public ConteudoCalendario() {
        // Required empty public constructor
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);


//        calendario = getView().findViewById(R.id.calendarioo);
////        CalendarDay dia = calendario.getCurrentDate();
////        CalendarDay dia2 = CalendarDay.from(2018,10,10);
//
//        final LocalDate instance = LocalDate.now();
//        calendario.setSelectedDate(instance);
//        Log.d("oioioioioioioioio", "oioioiojuuisqhuiwhduihwduih");
//
////        Calendar c = Calendar.getInstance();
//        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conteudo_calendario, container, false);
        this.c = inflater.getContext();
        Toast.makeText(c, "OK", Toast.LENGTH_SHORT).show();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendario = view.findViewById(R.id.calendarioo);
        Button b = view.findViewById(R.id.btnTeste);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "OI", Toast.LENGTH_SHORT).show();
            }
        });

        setEvents();

    }

    public void setEvents(){
        LocalDate temp = LocalDate.now().minusMonths(2);
        final ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Evento e = new Evento(temp.getDayOfMonth(),temp.getMonthValue(),temp.getYear(),0, 0, "Thuza", "Tutu", i);
            final CalendarDay day = e.getCalendarDay();
            dates.add(day);
            temp = temp.plusDays(5);
        }
        calendario.addDecorator(new DecoradorDeDias(Color.RED, dates));
        Log.d("DEBUG", "CHEGUEI");

    }


}
