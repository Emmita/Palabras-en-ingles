package com.emmita.english.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;

import com.emmita.english.Adapter.CategoryAdapter;
import com.emmita.english.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Encuentra el ViewPager en el xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Se crea el adaptador que va a encargarse de mostrar el fragment para cada categoría
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

        //Se le asigna el adaptador al viewPager
        viewPager.setAdapter(categoryAdapter);

        //Encuentra el TabLayout en el xml
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //Se asigna el viewPager al TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }
}
