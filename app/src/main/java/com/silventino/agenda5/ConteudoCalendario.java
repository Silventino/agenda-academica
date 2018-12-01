package com.silventino.agenda5;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;


public class ConteudoCalendario extends Fragment{

    private MaterialCalendarView calendario;
    private Context contexto;
    private DecoradorDeDias decorador;
    private ListView listaTarefas;
//    private FragmentActivity contexto;

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
        this.contexto = inflater.getContext();
        Toast.makeText(contexto, "OK", Toast.LENGTH_SHORT).show();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //refresh nos dias listados
        if(calendario.getSelectedDate() != null){
            refreshListaTarefas(calendario.getSelectedDate());
        }

        // refresh nos dias decorados

        ArrayList<Evento> eventos = BancoDeDados.getInstancia(getActivity().getApplicationContext()).getEventos();
        for(Evento evento : eventos){
            decorador.addDate(evento.getCalendarDay());
        }
        calendario.invalidateDecorators();


    }

    public void refreshListaTarefas(CalendarDay data){
        ArrayList<Evento> eventosDoDia = BancoDeDados.getInstancia(getActivity().getApplicationContext()).getEventosDoDia(data.getDay(), data.getMonth(), data.getYear());
        Log.i("asdasdasd", eventosDoDia.toString());
        ListViewAdapterEvento lva = new ListViewAdapterEvento(eventosDoDia, calendario.getContext());
        listaTarefas.setAdapter(lva);
        lva.notifyDataSetChanged();
        setListViewHeightBasedOnChildren();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendario = view.findViewById(R.id.calendarioo);
        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay diaSelecionado, boolean b) {
                refreshListaTarefas(diaSelecionado);
            }
        });

        decorador = new DecoradorDeDias(Color.RED, null);
        calendario.addDecorator(decorador);


        listaTarefas = view.findViewById(R.id.listaTarefas);

        ArrayList<Evento> es = new ArrayList<>();

//        es.add(new Evento(12,12,1998, 12, 12, "uau", "Doze"));
//        es.add(new Evento(14,12,1998, 12, 12, "uaau!", "Doze"));
//        es.add(new Evento(15,12,1998, 12, 12, "uaaau!", "Doze"));
//        es.add(new Evento(16,12,1998, 12, 12, "uauaaaaa!", "Doze"));
//        es.add(new Evento(17,12,1998, 12, 12, "uauaaaaaaa!", "Doze"));
//        es.add(new Evento(12,12,1998, 12, 12, "uau", "Doze"));
//        es.add(new Evento(12,12,1998, 12, 12, "uau!", "Doze"));
//        es.add(new Evento(16,12,1998, 12, 12, "NUUUUUUUUUUU uauaaaaa!", "Doze"));
//        es.add(new Evento(17,12,1998, 12, 12, "UUUU uauaaaaaaa!", "Doze"));
//        es.add(new Evento(12,12,1998, 12, 12, "uau!", "Doze"));
//        es.add(new Evento(12,12,1998, 12, 12, "IUIUNINI uau!", "Doze"));

        ListViewAdapterEvento lva = new ListViewAdapterEvento(es, view.getContext());
        listaTarefas.setAdapter(lva);
        listaTarefas.setFocusable(false);

//        setListViewHeightBasedOnChildren();
        calendario.setSelectedDate(LocalDate.now());

    }

    public void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = listaTarefas.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listaTarefas.getPaddingTop() + listaTarefas.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listaTarefas.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listaTarefas);


            if(listItem != null){
//                 This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

//                Log.i("medida", listItem.getHeight()+"");
                //TODO arrumar o calculo do tamanho do bagulho

            }
        }

        ViewGroup.LayoutParams params = listaTarefas.getLayoutParams();
//        params.height = totalHeight + (listaTarefas.getDividerHeight());
        params.height = totalHeight + (listaTarefas.getDividerHeight() * (listAdapter.getCount() - 1));
        listaTarefas.setLayoutParams(params);
        listaTarefas.requestLayout();
    }




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
