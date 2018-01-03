package com.emmita.english.Fragments;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.WorkSource;
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
import java.util.FormatterClosedException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthsFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){

                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }

        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    public MonthsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false );

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(R.string.month_january, R.string.english_month_january, R.raw.january));
        words.add(new Word(R.string.month_february, R.string.english_month_february, R.raw.february));
        words.add(new Word(R.string.month_march, R.string.english_month_march, R.raw.march));
        words.add(new Word(R.string.month_april, R.string.english_month_april, R.raw.april));
        words.add(new Word(R.string.month_may, R.string.english_month_may, R.raw.may));
        words.add(new Word(R.string.month_june, R.string.english_month_june, R.raw.june));
        words.add(new Word(R.string.month_july, R.string.english_month_july, R.raw.july));
        words.add(new Word(R.string.month_august, R.string.english_month_august, R.raw.august));
        words.add(new Word(R.string.month_september, R.string.english_month_september, R.raw.september));
        words.add(new Word(R.string.month_october, R.string.english_month_october, R.raw.october));
        words.add(new Word(R.string.month_november, R.string.english_month_november, R.raw.november));
        words.add(new Word(R.string.month_december, R.string.english_month_december, R.raw.december));

        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_months);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();

                Word word = words.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResource());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if (mMediaPlayer != null){
            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
