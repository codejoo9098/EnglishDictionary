package kr.co.project.zeroid.englishdictionary.addVoca;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.co.project.zeroid.englishdictionary.R;

public class AddVocaActivity extends AppCompatActivity {

    //파이어베이스 데이터베이스 연동
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    //현재 연결은 데이터베이스에만 딱 연결해놓고
    //키값(테이블 또는 속성)의 위치 까지는 들어가지는 않은 모습이다.
    private DatabaseReference databaseReference = database.getReference();

    private String searchText;

    private String[] resultArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voca);

        ListView listView=findViewById(R.id.addVocaListView);
        TextView searchVocaEditText=findViewById(R.id.searchVocaEditText);
        Button searchVocaButton=findViewById(R.id.searchVocaButton);
        Button addVocaButton=findViewById(R.id.addVocaButton);
        ProgressBar addVocaProgressBar=findViewById(R.id.addVocaProgressBar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        searchVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onPreExecute
                addVocaProgressBar.setVisibility(View.VISIBLE);
                //doInBackground
                Observable.fromCallable(()->{
                    searchText= searchVocaEditText.getText().toString();
                    Pattern onlyEnglish = Pattern.compile("^[a-zA-Z]+$");
                    Pattern onlyKorean=Pattern.compile("^[가-힣]+$");
                    if(onlyEnglish.matcher(searchText).matches()) {
                        searchVocaEditText.setText(null);
                        String result=searchEnglish(searchText);
                        resultArray=searchEnglishTrimResult(result);
                    } else if(onlyKorean.matcher(searchText).matches()) {
                        searchVocaEditText.setText(null);
                        String result=searchKorean(searchText);
                        resultArray=searchKoreanTrimResult(result);
                    } else {
                        searchVocaEditText.setText(null);
                        searchText=null;
                        return false;
                    }
                    return true;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((result)->{
                            //onPostExecute
                            if(result==true) {
                                addVocaProgressBar.setVisibility(View.GONE);
                                showResult(listView, resultArray);
                            } else {
                                Toast.makeText(getApplicationContext(),"영어만 입력하거나 한글만 입력하세요.",Toast.LENGTH_SHORT).show();
                                ArrayList<AddVocaListData> listViewData = new ArrayList<>();
                                ListAdapter oAdapter = new AddVocaCustomView(listViewData);
                                listView.setAdapter(oAdapter);
                                addVocaProgressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

        addVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if(searchText==null) return;
                    int childCount = listView.getChildCount();
                    ArrayList<String> inputVocaList = new ArrayList<>();
                    for (int i = 0; i < childCount; i++) {
                        if (listView.isItemChecked(i)) {
                            inputVocaList.add((String) listView.getAdapter().getItem(i));
                        }
                    }
                    addInputVocaListToFirebaseRealtimeDatabase(inputVocaList);
                } else {
                    Toast.makeText(getApplicationContext(),"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addInputVocaListToFirebaseRealtimeDatabase(ArrayList<String> inputVocaList) {
        DatabaseReference myRef=databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).child(searchText);
        //기존에 추가한 단어를 중복해서 추가할경우 기존에 저장한 값은 없어짐
        myRef.setValue(searchText);
        for(String inputVoca:inputVocaList) {
            //push == auto_increment
            myRef.push().setValue(inputVoca);
        }
    }

    private String searchEnglish(String searchText) {
        Document doc = null;
        try {
            doc = Jsoup.connect(getString(R.string.baseURL)+searchText).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result=doc.head().select("meta[property=og:description]").first().attr("content");
        return result;
    }

    private String searchKorean(String searchText) {
        Document doc = null;
        try {
            doc = Jsoup.connect(getString(R.string.baseURL)+searchText).get();
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

    private String[] searchEnglishTrimResult(String result) {
        return result.replaceFirst("[0-9].","").replaceAll("[0-9].","\n").split("\n");
    }

    private String[] searchKoreanTrimResult(String result) {
        String[] resultArray = result.replaceFirst("[0-9].","").replaceAll("[0-9].","\n").split("\n");
        for(int i=0;i<resultArray.length;i++) {
            resultArray[i]=resultArray[i].trim();
        }
        return resultArray;
    }

    private void showResult(ListView listView, String[] resultArray) {
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
            searchText=null;
            Toast.makeText(getApplicationContext(),"검색 결과가 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }
}