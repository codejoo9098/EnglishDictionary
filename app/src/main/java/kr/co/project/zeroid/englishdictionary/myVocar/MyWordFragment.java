package kr.co.project.zeroid.englishdictionary.myVocar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

    static FragmentActivity fragmentActivity;
    RecyclerView rc;
    static public MyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_word, container, false);
        rc=view.findViewById(R.id.myWordRecyclerView);
        adapter=new MyAdapter();
        fragmentActivity=getActivity();

        ArrayList<String> arr=new ArrayList<>();
        arr.add("바나나");
        WordAndMean wam1=new WordAndMean("Banana",arr,0);
        adapter.addItem(wam1);

        ArrayList<String> arr2=new ArrayList<>();
        arr2.add("의미하다"); arr2.add("사악한");
        WordAndMean wam2=new WordAndMean("Mean",arr2,0);
        adapter.addItem(wam2);
        //test2

        ArrayList<String> arr3=new ArrayList<>();
        arr3.add("자본금"); arr3.add("수도");
        WordAndMean wam3=new WordAndMean("Capital",arr3,0);
        adapter.addItem(wam3);

        for(int i=0;i<20;i++){
            ArrayList<String> arr4=new ArrayList<>();
            arr4.add("자본금"); arr4.add("수도");
            WordAndMean wam4=new WordAndMean("Capital",arr4,0);
            adapter.addItem(wam4);
        } //아 그냥 해당 영단어에 번호를 부여해야한다.

        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(adapter);

        inflater1=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder=new AlertDialog.Builder(getContext());

        return view;
    }


    static AlertDialog customDialog; //다이얼로그 안에서 이벤트 처리를 위한 다이얼로그 객체
    static LayoutInflater inflater1; //이게 프래그먼트 인자인 inflater랑 같아서 오류난거였음ㅠㅠ 바꾸니까 되네
    static AlertDialog.Builder builder;
    static String editMean; //다이얼로그에서 입력한 스트링 받음.
    static String tvEnglish;//해당 영문자를 받아옴
    static String wantEditMean; //바꾸려는 기존 뜻 스피너에서 선택.
    static String addMean; //추가하고 싶은 뜻을 입력받음.
    static Button deleteBt; //삭제버튼
    static public void Didalog(View view,TextView no,TextView eglish,TextView koreanM) {

        View customDialogView = inflater1.inflate(R.layout.my_word_dialog_layout, null);
        EditText edMean=customDialogView.findViewById(R.id.edit_mean);
        TextView eg=customDialogView.findViewById(R.id.english_in_dialog);
        TextView add=customDialogView.findViewById(R.id.add_mean);
        Spinner spinner=customDialogView.findViewById(R.id.mean_spinner);
        int num=Integer.parseInt(no.getText().toString());
        ArrayList<String> meanSpinner=MyAdapter.itemList.get(num-1).getMean();
        Log.d("aaa",""+meanSpinner);
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(customDialogView.getContext(), android.R.layout.simple_spinner_item,meanSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wantEditMean=MyAdapter.itemList.get(num-1).mean.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                wantEditMean=MyAdapter.itemList.get(num-1).mean.get(0);
            }
        });
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog == customDialog && which == DialogInterface.BUTTON_POSITIVE) {
                    //Toast.makeText(koreanM.getContext(), "확인", Toast.LENGTH_SHORT).show();
                    editMean=edMean.getText().toString().trim();//수정하려고 입력한 단어
                    tvEnglish=eg.getText().toString(); //선택한 영단어(다이얼로그의 주인)
                    addMean=add.getText().toString(); //추가하려고 입력한 뜻

                    //int num=Integer.parseInt(no.getText().toString());

                    //수정하거나 삭제하는 단어가 있는지.
                    if("삭제!".equals(editMean)) { //삭제 입력하면 해당 뜻 삭제
                        editKoreanMean(1,num); //해당 뜻 삭제
                    }
                    else if(!("".equals(editMean))){ //삭제가 아니고 빈것도 아니라면 해당 뜻 수정.
                        //Toast.makeText(koreanM.getContext(), "확인"+editMean, Toast.LENGTH_SHORT).show();

                        editKoreanMean(0,num); //삭제하는게 아니라 수정임.
                    }
                    //
                    if(!("".equals(addMean))){
                        MyAdapter.itemList.get(num-1).mean.add(addMean);
                        adapter.notifyDataSetChanged();
                        //db수정.
                    }
                }

            }
        };

        deleteBt=customDialogView.findViewById(R.id.delete_in_my_word); //삭제 버튼 클릭시
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

    static public void showDeleteDialog(int num){
        fragmentActivity.startActivity(new Intent(fragmentActivity,DeleteDialogActivity.class).putExtra("num",num));
    }



//    editMean=edMean.getText().toString().trim();
//    tvEnglish=eg.getText().toString();
//    static String wantEditMean; //바꾸려는 기존 뜻
    static void editKoreanMean(int isDeleteMean,int num){
        if(isDeleteMean==0) {  //수정하는 경우
            int j = MyAdapter.itemList.get(num - 1).mean.indexOf(wantEditMean); //바꾸려는 뜻의 뜻 배열인덱스 찾기
            MyAdapter.itemList.get(num - 1).mean.set(j,editMean);
            //데이터베이스 수정
        }
        else{
            int j = MyAdapter.itemList.get(num - 1).mean.indexOf(wantEditMean); //삭제하려는 뜻의 뜻 배열인덱스 찾기
            MyAdapter.itemList.get(num - 1).mean.remove(j);
            //데이터베이스 수정
        }
        adapter.notifyDataSetChanged(); //어댑터 다시 시작(뷰 다시 채워넣음)
    }
}