package com.emmita.english.Fragments;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.emmita.english.Adapter.WordAdapter;
import com.emmita.english.POJO.Word;
import com.emmita.english.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    //La clase MediaPlayer ayuda en la reproducción de los sonidos que están dentro del fragment
    private MediaPlayer mMediaPlayer;

    //AudioManager ayuda en la forma en que se va a estar escuchano el audio
    private AudioManager mAudioManager;

    //Este listener aplica para cada situación en que el audio se vea afectado
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //AUDIOFOCUS_LOSS_TRANSIENT se refiere a que el enfoque de audio se pierde temporalmente
                //AUDIOFOUCS_LOSS_TRANSIENT_CAN_DUCK  se refiere a que el audio puede seguir reproduciéndose, pero con volúmen más bajo

                //Se pausa la reproducción del audio y se reinicia. Así podemos reproducir de nuevo el audio de la palabra y se escuchará desde
                //el comienzo
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //AUDIOFOCUS_GAIN se refiere a que se recupera el enfoque del audio y se reaunudará la reproducción
                mMediaPlayer.start();
            }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //AUDIOFOCUS_LOSS se refiere a que el enfoque del audio se ha perdido y entonces se deben liberar los recursos utilizados
                releaseMediaPlayer();
            }
        }
    };

    //Este listener se refiere a cuando el audio se ha terminado y se deben liberar los recursos utilizados
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla la vista de este fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false );

        //Se crea la instancia de la clase AudioManager
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Se crea un ArrayList que contendrá todas las palabras
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(R.string.color_black, R.string.english_color_black, R.raw.black));
        words.add(new Word(R.string.color_white, R.string.english_color_white, R.raw.white));
        words.add(new Word(R.string.color_gray, R.string.english_color_gray, R.raw.gray));
        words.add(new Word(R.string.color_red, R.string.english_color_red, R.raw.red));
        words.add(new Word(R.string.color_green, R.string.english_color_green, R.raw.green));
        words.add(new Word(R.string.color_blue, R.string.english_color_blue, R.raw.blue));
        words.add(new Word(R.string.color_yellow, R.string.english_color_yellow, R.raw.yellow));
        words.add(new Word(R.string.color_orange, R.string.english_color_orange, R.raw.orange));
        words.add(new Word(R.string.color_brown, R.string.english_color_brown, R.raw.brown));
        words.add(new Word(R.string.color_purple, R.string.english_color_purple, R.raw.purple));

        //Se crea el adaptador
        final WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        //Se encuentra el ListView en el xml
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        //Se le asigna el adaptador al ListView
        listView.setAdapter(wordAdapter);

        //OnItemClickListener se encargará de reproducir el audio de cada palabra en cuanto se le de click al elemento seleccionado
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Si el audio de alguna otra palabra está en curso, se detiene
                //ya que al momento de dar click se reproducirá uno diferente
                releaseMediaPlayer();

                Word word = words.get(position);

                //Hace una solicitud para obtener el enfoque de audio
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //Si la solicitud es satisfactoria se crea la instancia de la clase MediaPlayer y se inicia
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResource());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    //Cuando la aplicación es pausada es neceario pausar y liberar los recursos
    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    //Este método se encarga de liberar los recursos utilizados y pausar el audio
    private void releaseMediaPlayer(){
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
