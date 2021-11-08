package kr.co.project.zeroid.englishdictionary.myVocar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.project.zeroid.englishdictionary.R;

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

    RecyclerView rc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wrong_note, container, false);
        rc=view.findViewById(R.id.myWrongRecyclerView);
        WrongAdapter adapter=new WrongAdapter();

        ArrayList<String> arr=new ArrayList<>();
        arr.add("바나나");
        WordAndMean wam1=new WordAndMean(1,"Banana",arr,arr.size(),7);
        adapter.addItem(wam1);

        ArrayList<String> arr2=new ArrayList<>();
        arr2.add("의미하다"); arr2.add("사악한");
        WordAndMean wam2=new WordAndMean(2,"Mean",arr2,arr2.size(),5);
        adapter.addItem(wam2);

        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(adapter);
        return view;
    }
}