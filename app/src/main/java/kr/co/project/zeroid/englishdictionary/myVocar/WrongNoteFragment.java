package kr.co.project.zeroid.englishdictionary.myVocar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    static AlertDialog.Builder wrongNoteBuilder;
    static WrongAdapter adapter;
    RecyclerView rc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wrong_note, container, false);
        rc=view.findViewById(R.id.myWrongRecyclerView);
        adapter=new WrongAdapter();

        ArrayList<String> arr=new ArrayList<>();
        arr.add("바나나");
        WordAndMean wam1=new WordAndMean("Banana",arr,7);
        adapter.addItem(wam1);

        ArrayList<String> arr2=new ArrayList<>();
        arr2.add("의미하다"); arr2.add("사악한");
        WordAndMean wam2=new WordAndMean("Mean",arr2,5);
        adapter.addItem(wam2);

        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(adapter);

        //wrongNoteinflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wrongNoteBuilder=new AlertDialog.Builder(getContext());
        return view;
    }


    //static LayoutInflater wrongNoteinflater;

    static AlertDialog alertDialog;
    static public void wrongNoteDialog(TextView no,TextView eg){

        int num=Integer.parseInt(no.getText().toString());
        String english=eg.getText().toString();
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==DialogInterface.BUTTON_POSITIVE){
                    WrongAdapter.itemList.remove(num-1);
                    adapter.notifyDataSetChanged();
                    //db처리
                    Toast.makeText(no.getContext(), english+" 단어가 삭제됐습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        wrongNoteBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        wrongNoteBuilder.setTitle("CONFIRM");
        wrongNoteBuilder.setMessage("정말로 "+english+" 단어를\n오답노트에서 삭제하시겠습니까?");
        wrongNoteBuilder.setPositiveButton("네",dialogListener);
        wrongNoteBuilder.setNegativeButton("아니요",null);
        alertDialog=wrongNoteBuilder.create();
        alertDialog.show();
        TextView messageText = (TextView)alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }


}