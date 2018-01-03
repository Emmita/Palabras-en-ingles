package com.emmita.english.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

import com.emmita.english.Fragments.ColorsFragment;
import com.emmita.english.Fragments.DaysFragment;
import com.emmita.english.Fragments.MonthsFragment;
import com.emmita.english.Fragments.NumbersFragment;
import com.emmita.english.R;

/**
 * Created by JESUS on 02/01/2018.
 */

public class CategoryAdapter extends FragmentPagerAdapter{

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        mContext = context;
    }

    //Regresa el fragment que debe ser lanzado por el numero de pagina marcado
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new NumbersFragment();
        }else if (position == 1){
            return new ColorsFragment();
        }else if (position == 2){
            return new DaysFragment();
        }else {
            return new MonthsFragment();
        }
    }

    //Número total de paginas
    @Override
    public int getCount() {
        return 4;
    }

    //Asigna el título a cada pagina
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.category_numbers);
        }else if (position == 1){
            return mContext.getString(R.string.category_colors);
        }else if (position == 2){
            return mContext.getString(R.string.category_days);
        }else {
            return mContext.getString(R.string.category_months);
        }
    }
}
