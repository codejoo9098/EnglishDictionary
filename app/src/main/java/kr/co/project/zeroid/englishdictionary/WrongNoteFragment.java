package kr.co.project.zeroid.englishdictionary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WrongNoteFragment extends Fragment {

    public WrongNoteFragment() {
        // Required empty public constructor
    }

    public static WrongNoteFragment newInstance() {
        WrongNoteFragment fragment = new WrongNoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wrong_note, container, false);
    }
}