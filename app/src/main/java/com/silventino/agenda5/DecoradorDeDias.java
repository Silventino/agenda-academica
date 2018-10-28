package com.silventino.agenda5;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class DecoradorDeDias implements DayViewDecorator {
    private final int cor;
    private final HashSet<CalendarDay> dias;

    public DecoradorDeDias(int cor, Collection<CalendarDay> dias){
        this.cor = cor;
        this.dias = new HashSet<>(dias);
    }

    @Override
    public boolean shouldDecorate(final CalendarDay dia) {
        return this.dias.contains(dia);
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.addSpan(new DotSpan(5, this.cor));
    }
}
