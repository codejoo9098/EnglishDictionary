package kr.co.project.zeroid.englishdictionary.myVocar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;

import kr.co.project.zeroid.englishdictionary.R;

public class DeleteDialogActivity extends AppCompatActivity {
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference databaseReference = database.getReference();
     DatabaseReference myRef=databaseReference.child("users").child(FirebaseAuth.getInstance().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_dialog);


        Intent intent=getIntent();

        Button cancelBt=findViewById(R.id.cancel_delete_MyWord);
        Button deleteOkayBt=findViewById(R.id.delete_okay);

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "삭제 취소", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        deleteOkayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=intent.getIntExtra("num",-1);
                myRef.child(MyWordFragment.adapter.getItemList().get(num - 1).englishWord).removeValue();
                MyWordFragment.adapter.getItemList().remove(num-1);
                MyWordFragment.adapter.notifyDataSetChanged();
                WrongNoteFragment.wrongNoteRemember=MyWordFragment.adapter.getItemList();
                Collections.sort(WrongNoteFragment.wrongNoteRemember);
                finish();
            }
        });
    }
}