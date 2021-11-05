package kr.co.project.zeroid.englishdictionary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class MyWordFragment extends Fragment {


    public MyWordFragment() {
        // Required empty public constructor
    }

    public static MyWordFragment newInstance() {
        MyWordFragment fragment = new MyWordFragment(); //자동으로 기본생성자 호출
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_word, container, false);
        RecyclerView myWordRecyclerView=view.findViewById(R.id.myWordRecyclerView);



        return view;
    }
}