package kr.co.project.zeroid.englishdictionary.myVocar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.project.zeroid.englishdictionary.R;


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

    RecyclerView rc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_word, container, false);
        rc=view.findViewById(R.id.myWordRecyclerView);
        MyAdapter adapter=new MyAdapter();

        ArrayList<String> arr=new ArrayList<>();
        arr.add("바나나");
        WordAndMean wam1=new WordAndMean(1,"Banana",arr,arr.size(),0);
        adapter.addItem(wam1);

        ArrayList<String> arr2=new ArrayList<>();
        arr2.add("의미하다"); arr2.add("사악한");
        WordAndMean wam2=new WordAndMean(2,"Mean",arr2,arr2.size(),0);
        adapter.addItem(wam2);

        ArrayList<String> arr3=new ArrayList<>();
        arr3.add("의미하다"); arr3.add("사악한");
        WordAndMean wam3=new WordAndMean(3,"Mean",arr3,arr3.size(),0);
        adapter.addItem(wam3);
        Log.d("aa","aa");//
        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(adapter);
        return view;
    }
}