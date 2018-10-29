package com.silventino.agenda5;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ConteudoCalendario conteudoCalendario;
    private ConteudoGrupos conteudoGrupos;

    public FragmentPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        conteudoCalendario = new ConteudoCalendario();
        conteudoGrupos = new ConteudoGrupos();
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return conteudoCalendario;
        } else{
            return conteudoGrupos;
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_calendario);
            case 1:
                return mContext.getString(R.string.tab_grupos);
            default:
                return null;
        }
    }

}