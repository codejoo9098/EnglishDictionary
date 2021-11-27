package kr.co.project.zeroid.englishdictionary.myVocar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

import kr.co.project.zeroid.englishdictionary.R;

public class AddWordDialogActivity extends AppCompatActivity {
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference databaseReference = database.getReference();
//    DatabaseReference myRef=databaseReference.child("users").child(FirebaseAuth.getInstance().getUid());

    Button addOkay;
    Button cancelAdd;
    EditText addWordEnglish;
    EditText addWordKorean;
    String newEg;
    String newKm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_dialog);

        addOkay=findViewById(R.id.add_word_okay);
        cancelAdd=findViewById(R.id.cancel_add_word);
        addWordEnglish=findViewById(R.id.add_word_english);
        addWordKorean=findViewById(R.id.add_word_korean);

        addOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newEg=addWordEnglish.getText().toString().trim();
                newKm=addWordKorean.getText().toString().trim();

                if((!newEg.equals(""))&&(!newKm.equals(""))){
                    String newEgUpper=newEg.substring(0,1).toUpperCase()+newEg.substring(1,newEg.length()).toLowerCase();
                    DatabaseReference addMyRef=MyWordFragment.myRef.child(newEgUpper);
                    //기존에 추가한 단어를 중복해서 추가할경우 기존에 저장한 값은 없어짐
                    addMyRef.setValue(newEgUpper);
                    addMyRef.child("-1").setValue(newKm);
                    addMyRef.child("-틀린횟수").setValue("0");

                    ArrayList<String> m=new ArrayList<>();
                    m.add(newKm);
                    WordAndMean w=MyWordFragment.adapter.searchItemEnglish(newEgUpper);
                    if(w!=null){
                        w.mean=m;
                        w.wrongCount=0;
                        MyWordFragment.adapter.notifyDataSetChanged();
                    }
                    else{
                        MyWordFragment.adapter.addItem(new WordAndMean(newEgUpper,m,0));
                        Collections.sort(MyWordFragment.adapter.getItemList(),new EnglishComparator());
                        MyWordFragment.adapter.notifyDataSetChanged();
                    }
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"영어단어와 뜻 모두 입력해주세요",Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"단어추가 취소",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}