package com.silventino.agenda5;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;


public class conteudoCalendario extends Fragment {

    MaterialCalendarView calendario;

    public conteudoCalendario() {
        // Required empty public constructor
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        calendario = getView().findViewById(R.id.calendarioo);
//        CalendarDay dia = calendario.getCurrentDate();
//        CalendarDay dia2 = CalendarDay.from(2018,10,10);


//        Calendar c = Calendar.getInstance();
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conteudo_calendario, container, false);
        return rootView;
    }

    public void setEvents(){
        LocalDate temp = LocalDate.now().minusMonths(2);
        final ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            final CalendarDay day = CalendarDay.from(temp);
            dates.add(day);
            temp = temp.plusDays(5);
        }
        calendario.addDecorator(new DecoradorDeDias(Color.RED, dates));
        Log.d("DEBUG", "CHEGUEI");

    }



    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalDate temp = LocalDate.now().minusMonths(2);
            final ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                final CalendarDay day = CalendarDay.from(temp);
                dates.add(day);
                temp = temp.plusDays(5);
            }
            calendario.addDecorator(new DecoradorDeDias(Color.RED, dates));
            Log.d("DEBUG", "CHEGUEI2");
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

//            if (isFinishing()) {
//                return;
//            }

            calendario.addDecorator(new DecoradorDeDias(Color.RED, calendarDays));
            Log.d("DEBUG", "CHEGUEI2");

        }
    }

}
