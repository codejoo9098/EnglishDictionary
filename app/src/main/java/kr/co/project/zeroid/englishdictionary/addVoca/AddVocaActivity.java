package kr.co.project.zeroid.englishdictionary.addVoca;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import kr.co.project.zeroid.englishdictionary.R;

public class AddVocaActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voca);

        ListView listView=findViewById(R.id.addVocaListView);
        TextView searchVocaEditText=findViewById(R.id.searchVocaEditText);
        Button searchVocaButton=findViewById(R.id.searchVocaButton);
        Button addVocaButton=findViewById(R.id.addVocaButton);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        searchVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= searchVocaEditText.getText().toString();
                Pattern onlyEnglish = Pattern.compile("^[a-zA-Z]+$");
                Pattern onlyKorean=Pattern.compile("^[가-힣]+$");
                if(onlyEnglish.matcher(text).matches()) {
                    searchVocaEditText.setText(null);
                    String result=searchEnglish(text);
                    showResult(listView, result);
                } else if(onlyKorean.matcher(text).matches()) {
                    searchVocaEditText.setText(null);
                    String result=searchKorean(text);
                    showResult(listView, result);
                } else {
                    searchVocaEditText.setText(null);
                    Toast.makeText(getApplicationContext(),"영어만 입력하거나 한글만 입력하세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        addVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 실시간 데이터베이스에 선택된 단어 넣기
            }
        });
    }

    private String searchEnglish(String text) {
        Document doc = null;
        try {
            doc = Jsoup.connect(getString(R.string.baseURL)+text).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result=doc.head().select("meta[property=og:description]").first().attr("content");
        return result;
    }

    private String searchKorean(String text) {
        Document doc = null;
        try {
            doc = Jsoup.connect(getString(R.string.baseURL)+text).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element elem=doc.getElementsByAttributeValue("data-tiara-layer","word eng").select("ul[class=list_search]").first();
        try {
            return elem.text();
        } catch(NullPointerException e) {
            return "";
        }
    }

    private String[] trimResult(String result) {
        return result.replaceFirst("[0-9].","").replaceAll("[0-9].","\n").split("\n");
    }

    private void showResult(ListView listView, String result) {
        String[] resultArray=trimResult(result);
        if(resultArray[0]!="") {
            ArrayList<AddVocaListData> listViewData = new ArrayList<>();
            for (int i=0; i<resultArray.length; i++)
            {
                AddVocaListData listData = new AddVocaListData();
                listData.voca = resultArray[i];
                listViewData.add(listData);
            }
            ListAdapter oAdapter = new AddVocaCustomView(listViewData);
            listView.setAdapter(oAdapter);
        } else {
            ArrayList<AddVocaListData> listViewData = new ArrayList<>();
            ListAdapter oAdapter = new AddVocaCustomView(listViewData);
            listView.setAdapter(oAdapter);
            Toast.makeText(getApplicationContext(),"검색 결과가 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }
}