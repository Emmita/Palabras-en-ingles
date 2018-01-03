package com.emmita.english.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emmita.english.POJO.Word;
import com.emmita.english.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by JESUS on 02/01/2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    //Color que va a ser asignado al contenedor de la categoria en donde se encuentra la aplicación
    private int mColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId){
        super(context, 0, words);
        mColorResourceId = colorResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Infla la vista que va aser mostrada
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //Es el objeto que esta asignado en esa posición en la lista
        Word currentWord = getItem(position);

        //Encuentra el TextView de la palabra en inglés situado en el xml antes inflado
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_text_view);
        //Se le asigna el texto correspondiente de acuerdo al objeto qe esta en esa posición
        englishTextView.setText(currentWord.getEnglish());

        //Encuentra el TextView de la palabra en español situado en el xml antes inflado
        TextView spanishTextView = (TextView) listItemView.findViewById(R.id.spanish_text_view);
        //Se le asigna el texto correspondiente de acuerdo al objeto qe esta en esa posición
        spanishTextView.setText(currentWord.getSpanish());


        //Es el contenedor de cada uno de los elementos de la lista
        View textContainer = listItemView.findViewById(R.id.text_container);

        //Se le asigna el color al contenedor de acuerdo en qué categoría se encuentra
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
