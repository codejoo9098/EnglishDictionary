package kr.co.project.zeroid.englishdictionary.myVocar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.singleton.SingletonVocaMap;
import kr.co.project.zeroid.englishdictionary.util.MakeToast;
import kr.co.project.zeroid.englishdictionary.util.NetworkStatus;


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

    static FragmentActivity fragmentActivity;
    static RecyclerView rc;
    static public MyAdapter adapter;

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference databaseReference = database.getReference();
    static DatabaseReference myRef=databaseReference.child("users").child(FirebaseAuth.getInstance().getUid());

    static ArrayList<WordAndMean> stay_remember;
    static int isFirst=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Thread pullDataThread;
    private Handler mHandler=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_word, container, false);
        rc = view.findViewById(R.id.myWordRecyclerView);
        fragmentActivity = getActivity();

        if (isFirst==0) {
            pullDataThread=new Thread("Pull Data"){
                @Override
                public void run() {
                    Log.d("firekmj","작업스레드 안입니다.");
                    isFirst++;
                    HashMap<String, HashMap<String, String>> singletonVocaMap=SingletonVocaMap.getInstance(); //최근에 통신으로 받아온걸 받음.
                    adapter = new MyAdapter();
                    for (String english_key : singletonVocaMap.keySet()) {
                        ArrayList<String> arr = new ArrayList<>();
                        for (int num = 1; num <= (singletonVocaMap.get(english_key).size()-1); num++) {
                            arr.add(singletonVocaMap.get(english_key).get("-" + num));
                        }
                        int wc=Integer.parseInt(singletonVocaMap.get(english_key).get("-틀린횟수"));
                        //String eg=english_key.substring(0,1).toUpperCase()+english_key.substring(1,english_key.length());
                        WordAndMean wam = new WordAndMean(english_key, arr, wc);
                        //Log.d("firekmj",""+wam);
                        adapter.addItem(wam);
                    }
                    Collections.sort(adapter.getItemList(),new EnglishComparator());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("firekmj","작업스레드에서 메인스레드에게 화면 변화 요청");
                            rc.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rc.setAdapter(adapter);
                        }
                    });
                }
            };
            pullDataThread.start();
        }
        else{
            adapter = new MyAdapter();
            adapter.setItemList(stay_remember);
            rc.setLayoutManager(new LinearLayoutManager(getActivity()));
            rc.setAdapter(adapter);
        }

        inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder = new AlertDialog.Builder(getContext());

        return view;
    }

    @Override
    public void onPause() {
        Log.d("firekmj","단어장 프래그먼트 정지됨.");
        stay_remember=adapter.getItemList();
        pullDataThread.interrupt();
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("firekmj","단어장 프래그먼트 스탑됨.");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("firekmj","단어장 프래그먼트 파괴됨.");
        super.onDestroy();
    }

    static AlertDialog customDialog; //다이얼로그 안에서 이벤트 처리를 위한 다이얼로그 객체
    static LayoutInflater inflater1; //이게 프래그먼트 인자인 inflater랑 같아서 오류난거였음ㅠㅠ 바꾸니까 되네
    static AlertDialog.Builder builder;
    static String editMean; //다이얼로그에서 입력한 스트링 받음.
    static String tvEnglish;//해당 영문자를 받아옴
    static String wantEditMean; //바꾸려는 기존 뜻 스피너에서 선택.
    static String addMean; //추가하고 싶은 뜻을 입력받음.
    static Button deleteBt; //삭제버튼

    static public void Didalog(View view, TextView no, TextView eglish, TextView koreanM) {
        if (NetworkStatus.getConnectivityStatus(fragmentActivity.getApplicationContext()) != 3) {
            View customDialogView = inflater1.inflate(R.layout.my_word_dialog_layout, null);
            EditText edMean = customDialogView.findViewById(R.id.edit_mean);
            TextView eg = customDialogView.findViewById(R.id.english_in_dialog);
            TextView add = customDialogView.findViewById(R.id.add_mean);
            Spinner spinner = customDialogView.findViewById(R.id.mean_spinner);
            int num = Integer.parseInt(no.getText().toString()); //다이얼로그를 호출한 항목이 몇번째인지
            ArrayList<String> meanSpinner = adapter.getItemList().get(num - 1).getMean();

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(customDialogView.getContext(), android.R.layout.simple_spinner_item, meanSpinner);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    wantEditMean = adapter.getItemList().get(num - 1).mean.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    wantEditMean = adapter.getItemList().get(num - 1).mean.get(0);
                }
            });

            DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialog == customDialog && which == DialogInterface.BUTTON_POSITIVE) {
                        if (NetworkStatus.getConnectivityStatus(fragmentActivity.getApplicationContext()) != 3) {
                            editMean = edMean.getText().toString().trim();//수정하려고 입력한 단어
                            tvEnglish = eg.getText().toString(); //선택한 영단어(다이얼로그의 주인)
                            addMean = add.getText().toString(); //추가하려고 입력한 뜻

                            //int num=Integer.parseInt(no.getText().toString());

                            //수정하거나 삭제하는 단어가 있는지.
                            if ("삭제!".equals(editMean)) { //삭제 입력하면 해당 뜻 삭제
                                editKoreanMean(1, num); //해당 뜻 삭제

                            } else if (!("".equals(editMean))) { //삭제가 아니고 빈것도 아니라면 해당 뜻 수정.
                                //Toast.makeText(koreanM.getContext(), "확인"+editMean, Toast.LENGTH_SHORT).show();

                                editKoreanMean(0, num); //삭제하는게 아니라 수정임.
                            }
                            //
                            if (!("".equals(addMean))) {
                                adapter.getItemList().get(num - 1).mean.add(addMean);
                                adapter.notifyDataSetChanged();
                                int new_add_num = adapter.getItemList().get(num - 1).mean.size();
                                myRef.child(adapter.getItemList().get(num - 1).englishWord).child("-" + new_add_num).setValue(addMean);
                                //db수정.
                            }
                        }
                        else{
                            MakeToast.makeToast(fragmentActivity.getApplicationContext(),"네트워크 연결을 확인해주세요.").show();
                        }
                    }

                }
            };

            deleteBt = customDialogView.findViewById(R.id.delete_in_my_word); //삭제 버튼 클릭시
            deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteDialog(num);
                    customDialog.dismiss();
                }
            });

            eg.setText(eglish.getText().toString());//다이얼로그에 무슨 단어의 다이얼로그인지 표시

            builder.setView(customDialogView);
            builder.setPositiveButton("확인", dialogListener);
            builder.setNegativeButton("취소", null);
            customDialog = builder.create();
            customDialog.show();
        }
        else{
            MakeToast.makeToast(fragmentActivity.getApplicationContext(),"네트워크 연결을 확인해주세요.").show();
        }
    }


    static public void showDeleteDialog(int num) {
        fragmentActivity.startActivity(new Intent(fragmentActivity, DeleteDialogActivity.class).putExtra("num", num));
    }



    static void editKoreanMean(int isDeleteMean, int num) {
        if (isDeleteMean == 0) {  //수정하는 경우
            int j = adapter.getItemList().get(num - 1).mean.indexOf(wantEditMean); //바꾸려는 뜻의 뜻 배열인덱스 찾기
            adapter.getItemList().get(num - 1).mean.set(j, editMean);

            //데이터베이스 통신으로 데이터 교체
            myRef.child(adapter.getItemList().get(num - 1).englishWord).child("-"+(j+1)).setValue(editMean); //교체

        } else {

            if(adapter.getItemList().get(num-1).getMean().size()==1){
                Toast.makeText(fragmentActivity.getApplicationContext(),"뜻이 한개 밖에 없습니다.",Toast.LENGTH_SHORT).show();
            }
            else {
                int j = adapter.getItemList().get(num - 1).mean.indexOf(wantEditMean); //삭제하려는 뜻의 뜻 배열인덱스 찾기

                adapter.getItemList().get(num - 1).mean.remove(j);

                //데이터베이스에서 삭제
                myRef.child(adapter.getItemList().get(num - 1).englishWord).child("-" + (j + 1)).removeValue();

                //데이터베이스내의 해당 단어의 의미 키값 다시 정렬. 이유: 중간 뜻의 숫자키가 사라지면 키값이 안이어짐.
                for (int i = 0; i < adapter.getItemList().get(num - 1).mean.size(); i++) {
                    myRef.child(adapter.getItemList().get(num - 1).englishWord).child("-" + (i + 1)).setValue(adapter.getItemList().get(num - 1).mean.get(i));
                }
                myRef.child(adapter.getItemList().get(num - 1).englishWord).child("-" + (adapter.getItemList().get(num - 1).mean.size() + 1)).removeValue();
            }
        }
        rc.setAdapter(adapter);
        adapter.notifyDataSetChanged(); //어댑터 다시 시작(뷰 다시 채워넣음)
        Log.d("firekmj","뜻 삭제 후 갱신"+adapter.getItemList().get(num-1).mean);

    }

    static public void word_Add(){
        if(NetworkStatus.getConnectivityStatus(fragmentActivity.getApplicationContext())!=3) {
            fragmentActivity.startActivity(new Intent(fragmentActivity, AddWordDialogActivity.class));
        }
        else{
            MakeToast.makeToast(fragmentActivity.getApplicationContext(),"네트워크 연결을 확인해주세요.").show();
        }
    }

}