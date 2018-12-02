package com.silventino.agenda5;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DecoradorDeDias implements DayViewDecorator {
    private final int cor;
    private HashSet<CalendarDay> dias;

    public DecoradorDeDias(int cor, Collection<CalendarDay> dias){
        this.cor = cor;
        if(dias != null){
            this.dias = new HashSet<>(dias);
        }
        else{
            this.dias = new HashSet<>();
        }
    }

    @Override
    public boolean shouldDecorate(final CalendarDay dia) {
        return this.dias.contains(dia);
    }

    public void addDate(CalendarDay dia){
        this.dias.add(dia);
    }
    public void addDate(Collection<CalendarDay> dias){
        this.dias.addAll(dias);
    }

    public void rebuild(HashMap<String, ArrayList<Evento>> eventosMap){
        dias = new HashSet<>();
        for(String key : eventosMap.keySet()){
            for(Object evento : eventosMap.get(key)){
                Log.d("Eventos", ((Evento) evento).getData());
                this.addDate(((Evento) evento).getCalendarDay());
            }
        }

    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.addSpan(new DotSpan(5, this.cor));
    }
}
