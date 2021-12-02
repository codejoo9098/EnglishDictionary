package kr.co.project.zeroid.englishdictionary.myVocar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.project.zeroid.englishdictionary.R;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    SearchView searchView;
    RecyclerView rc;
    LinearLayout categoryBar;
    RadioGroup radioGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<WordAndMean> db = MyWordFragment.stay_remember;




        searchView = view.findViewById(R.id.search_view);
        categoryBar=view.findViewById(R.id.categoryBar);
        rc=view.findViewById(R.id.searchRc);
        radioGroup=view.findViewById(R.id.radio_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MyAdapter adapter = new MyAdapter();
                int searchAmount=db.size();
                String query_s=query.trim();
                query_s=query_s.substring(0,1).toUpperCase()+query_s.substring(1,query_s.length()).toLowerCase();
                if (R.id.english_word==radioGroup.getCheckedRadioButtonId()) {//영단어검색하는 경우
                    int count=0;
                    for(int i=0;i<searchAmount;i++){
                        if(query_s.equals(db.get(i).englishWord)) {
                            adapter.addItem(db.get(i));
                            count++;
                        }
                    }
                    if(count==0){
                        categoryBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "검색과 일치하는 단어가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        categoryBar.setVisibility(View.VISIBLE);

                        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rc.setAdapter(adapter);
                    }
                } else if (R.id.mean==radioGroup.getCheckedRadioButtonId()) { //뜻으로 검색하는 경우
                    int count=0;
                    for(int i=0;i<searchAmount;i++){
                        if(db.get(i).mean.contains(query_s)) {
                            adapter.addItem(db.get(i));
                            count++;
                        }
                    }
                    if(count==0){
                        categoryBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "검색과 일치하는 단어가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        categoryBar.setVisibility(View.VISIBLE);
                        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rc.setAdapter(adapter);
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }
}