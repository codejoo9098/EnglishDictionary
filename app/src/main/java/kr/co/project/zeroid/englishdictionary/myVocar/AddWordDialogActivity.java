package kr.co.project.zeroid.englishdictionary.myVocar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.util.MakeToast;
import kr.co.project.zeroid.englishdictionary.util.NetworkStatus;

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
                if (NetworkStatus.getConnectivityStatus(getApplicationContext()) != 3) {
                    newEg = addWordEnglish.getText().toString().trim();
                    newKm = addWordKorean.getText().toString().trim();

                    if ((!newEg.equals("")) && (!newKm.equals(""))) {
                        String newEgUpper = newEg.substring(0, 1).toUpperCase() + newEg.substring(1, newEg.length()).toLowerCase();
                        DatabaseReference addMyRef = MyWordFragment.myRef.child(newEgUpper);
                        //????????? ????????? ????????? ???????????? ??????????????? ????????? ????????? ?????? ?????????
                        addMyRef.setValue(newEgUpper);
                        addMyRef.child("-1").setValue(newKm);
                        addMyRef.child("-????????????").setValue("0");

                        ArrayList<String> m = new ArrayList<>();
                        m.add(newKm);
                        WordAndMean w = MyWordFragment.adapter.searchItemEnglish(newEgUpper);
                        if (w != null) {
                            w.mean = m;
                            w.wrongCount = 0;
                            MyWordFragment.adapter.notifyDataSetChanged();
                        } else {
                            MyWordFragment.adapter.addItem(new WordAndMean(newEgUpper, m, 0));
                            Collections.sort(MyWordFragment.adapter.getItemList(), new EnglishComparator());
                            MyWordFragment.adapter.notifyDataSetChanged();
                        }
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "??????????????? ??? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    MakeToast.makeToast(getApplicationContext(),"???????????? ????????? ??????????????????.").show();
                }

            }
        });
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"???????????? ??????",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}