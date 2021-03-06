package kr.co.project.zeroid.englishdictionary.addVoca;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import kr.co.project.zeroid.englishdictionary.util.MakeToast;
import kr.co.project.zeroid.englishdictionary.util.NetworkStatus;

public class AddVocaActivity extends AppCompatActivity {

    //파이어베이스 데이터베이스 연동
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    //현재 연결은 데이터베이스에만 딱 연결해놓고
    //키값(테이블 또는 속성)의 위치 까지는 들어가지는 않은 모습이다.
    private DatabaseReference databaseReference = database.getReference();

    private String searchText;

    private String[] resultArray;

    private boolean unexpectedErrorFlag=false;
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

        Pattern onlyEnglish = Pattern.compile("^[a-zA-Z ]+$");
        Pattern onlyKorean=Pattern.compile("^[가-힣 ]+$");

        searchVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkStatus.getConnectivityStatus(getApplicationContext())!=3) {
                    searchVocaEditText.setText(searchVocaEditText.getText().toString().trim());
                    if(searchVocaEditText.getText().length()==0) {
                        MakeToast.makeToast(getApplicationContext(),"검색할 단어를 입력하세요.").show();
                    }
                    else {
                        //onPreExecute
                        addVocaProgressBar.setVisibility(View.VISIBLE);
                        Log.d("제로이드", "RxJava 실행");
                        //doInBackground
                        Observable.fromCallable(() -> {
                            searchText = searchVocaEditText.getText().toString();
                            searchText = searchText.substring(0, 1).toUpperCase() + searchText.substring(1, searchText.length()).toLowerCase();
                            if (onlyEnglish.matcher(searchText).matches()) {
                                String result = searchEnglish(searchText);
                                resultArray = searchEnglishTrimResult(result);
                                listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                            } else if (onlyKorean.matcher(searchText).matches()) {
                                String result = searchKorean(searchText);
                                resultArray = searchKoreanTrimResult(result);
                                listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                            } else {
                                searchText = null;
                                return false;
                            }
                            return true;
                        })
                                .onErrorReturn(throwable -> {
                                    unexpectedErrorFlag=true;
                                    searchText=null;
                                    return false;
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe((result) -> {
                                    //onPostExecute
                                    searchVocaEditText.setText(null);
                                    if (result == true) {
                                        addVocaProgressBar.setVisibility(View.GONE);
                                        showResult(listView, resultArray);
                                    } else {
                                        if(unexpectedErrorFlag==true) {
                                            unexpectedErrorFlag=false;
                                            MakeToast.makeToast(getApplicationContext(),"예상하지 못한 오류가 발생했습니다. 다시 시도해주세요.").show();
                                        } else {
                                            MakeToast.makeToast(getApplicationContext(), "영어+공백조합이거나 한글+공백조합만 입력하세요.").show();
                                        }
                                        ArrayList<AddVocaListData> listViewData = new ArrayList<>();
                                        ListAdapter oAdapter = new AddVocaCustomView(listViewData);
                                        listView.setAdapter(oAdapter);
                                        addVocaProgressBar.setVisibility(View.GONE);
                                    }
                                });
                    }
                } else {
                    MakeToast.makeToast(getApplicationContext(),"인터넷 연결을 확인해 주세요.").show();
                }
            }
        });

        addVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if(NetworkStatus.getConnectivityStatus(getApplicationContext())!=3) {
                        int childCount = listView.getChildCount();
                        if (searchText == null || childCount == 0) {
                            MakeToast.makeToast(getApplicationContext(), "추가할 단어가 없습니다.").show();
                            return;
                        }
                        ArrayList<String> inputVocaList = new ArrayList<>();
                        for (int i = 0; i < childCount; i++) {
                            if (listView.isItemChecked(i)) {
                                inputVocaList.add((String) listView.getAdapter().getItem(i));
                            }
                        }
                        if (inputVocaList.isEmpty()) {
                            MakeToast.makeToast(getApplicationContext(), "추가할 단어가 선택되지 않았습니다.").show();
                            return;
                        }
                        if (onlyEnglish.matcher(searchText).matches()) {
                            addInputVocaListToFirebaseRealtimeDatabaseOnlyEnglish(inputVocaList);
                        } else if (onlyKorean.matcher(searchText).matches()) {
                            addInputVocaListToFirebaseRealtimeDatabaseOnlyKorean(inputVocaList);
                        } else {
                            MakeToast.makeToast(getApplicationContext(), "예상치 못한 오류가 발생했습니다. 다시 시도해 주세요.").show();
                        }
                        MakeToast.makeToast(getApplicationContext(), "단어가 추가됬습니다.").show();
                    } else {
                        MakeToast.makeToast(getApplicationContext(), "인터넷 연결을 확인해 주세요.").show();
                    }
                } else {
                    MakeToast.makeToast(getApplicationContext(),"로그인이 필요한 서비스입니다.").show();
                }
            }
        });
    }

    private void addInputVocaListToFirebaseRealtimeDatabaseOnlyEnglish(ArrayList<String> inputVocaList) {
        DatabaseReference myRef=databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).child(searchText);
        //기존에 추가한 단어를 중복해서 추가할경우 기존에 저장한 값은 없어짐
        Log.d("제로이드3", searchText);
        myRef.setValue(searchText);
        int index=1;
        for(String inputVoca:inputVocaList) {
            //키 값에 -를 붙여야 제대로 가져옴
            myRef.child("-"+index).setValue(inputVoca.trim());
            index++;
        }
        myRef.child("-틀린횟수").setValue("0");
    }

    private void addInputVocaListToFirebaseRealtimeDatabaseOnlyKorean(ArrayList<String> inputVocaList) {
        String text=inputVocaList.get(0).trim();
        text=text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
        DatabaseReference myRef=databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).child(text);
        //기존에 추가한 단어를 중복해서 추가할경우 기존에 저장한 값은 없어짐
        myRef.setValue(text);
        myRef.child("-1").setValue(searchText);
        myRef.child("-틀린횟수").setValue("0");
    }

    private String searchEnglish(String searchText) {
        Document doc = null;
        Log.d("제로이드_다음사전", "Jsoup 실행");
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
        Log.d("제로이드", "Jsoup 실행");
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
            MakeToast.makeToast(getApplicationContext(),"검색 결과가 없습니다.").show();
        }
    }
}