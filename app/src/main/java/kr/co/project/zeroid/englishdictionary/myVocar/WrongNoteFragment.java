package kr.co.project.zeroid.englishdictionary.myVocar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.network.NetworkStatus;
import kr.co.project.zeroid.englishdictionary.singleton.SingletonVocaMap;

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
        Log.d("firekmj","여기는 오답노트의  onCreate");
        super.onCreate(savedInstanceState);

    }

    static AlertDialog.Builder wrongNoteBuilder;
    static WrongAdapter adapter;
    RecyclerView rc;
    private Thread wrongPullDataThread;
    private Handler wrongHandler=new Handler();
    static public int isWrongNoteFirst;
    static public ArrayList<WordAndMean> wrongNoteRemember;
    static FragmentActivity fragmentActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wrong_note, container, false);
        rc=view.findViewById(R.id.myWrongRecyclerView);
        fragmentActivity=getActivity();
        if(isWrongNoteFirst==0) {
            wrongPullDataThread = new Thread("wrong Data") {
                @Override
                public void run() {
                    Log.d("firekmj", "오답노트의 작업스레드 안입니다.");
                    //어차피 나만의 단어장 한번 들어오면 틀린횟수가 바뀔 일은 없으므로 나만의 단어장에서의 데이터를 가져옴.
                    //근데 지금 갱신이 느린 듯함. 테스트 보고 나서 한번 봐야할듯.
                    isWrongNoteFirst++;
                    adapter=new WrongAdapter();
                    wrongNoteRemember=new ArrayList<>();
                    for (int i = 0; i < MyWordFragment.stay_remember.size(); i++) {
                        if (MyWordFragment.stay_remember.get(i).getWrongCount() != 0) {
                            adapter.addItem(MyWordFragment.stay_remember.get(i));
                        }
                    }
                    wrongHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("firekmj", "오답노트)작업스레드에서 메인스레드에게 화면 변화 요청");
                            rc.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rc.setAdapter(adapter);
                        }
                    });
                    Collections.sort(adapter.getItemList()); //틀린 순서대로 정렬
                    wrongNoteRemember=adapter.getItemList();
                }
            };
            wrongPullDataThread.start();
        }
        else{
            adapter=new WrongAdapter();
            adapter.setItemList(wrongNoteRemember);
            rc.setLayoutManager(new LinearLayoutManager(getActivity()));
            rc.setAdapter(adapter);
        }

        //wrongNoteinflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wrongNoteBuilder=new AlertDialog.Builder(getContext());
        return view;
    }

    @Override
    public void onPause() {
        wrongPullDataThread.interrupt();
        super.onPause();
    }

    //static LayoutInflater wrongNoteinflater;

    static AlertDialog alertDialog;
    static public void wrongNoteDialog(TextView no,TextView eg) {
        if (NetworkStatus.getConnectivityStatus(fragmentActivity.getApplicationContext()) != 3) {
            int num = Integer.parseInt(no.getText().toString());
            String english = eg.getText().toString();
            DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == DialogInterface.BUTTON_POSITIVE) {
                        //DB먼저 처리(틀린횟수를 0으로 해줌.)하고 리스트에서 삭제
                        MyWordFragment.myRef.child(adapter.getItemList().get(num - 1).englishWord).child("-틀린횟수").setValue("0"); //교체
                        adapter.getItemList().remove(num - 1);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(no.getContext(), english + " 단어가 삭제됐습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            wrongNoteBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            wrongNoteBuilder.setTitle("CONFIRM");
            wrongNoteBuilder.setMessage("정말로 " + english + " 단어를\n오답노트에서 삭제하시겠습니까?");
            wrongNoteBuilder.setPositiveButton("네", dialogListener);
            wrongNoteBuilder.setNegativeButton("아니요", null);
            alertDialog = wrongNoteBuilder.create();
            alertDialog.show();
            TextView messageText = (TextView) alertDialog.findViewById(android.R.id.message);
            messageText.setGravity(Gravity.CENTER);
        }
        else{
            Toast.makeText(fragmentActivity.getApplicationContext(), "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}