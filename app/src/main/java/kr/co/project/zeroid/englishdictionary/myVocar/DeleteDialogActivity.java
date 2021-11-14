package kr.co.project.zeroid.englishdictionary.myVocar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


import kr.co.project.zeroid.englishdictionary.R;

public class DeleteDialogActivity extends AppCompatActivity {

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
                Log.d("aaa",""+num);
                MyAdapter.itemList.remove(num-1);
                MyWordFragment.adapter.notifyDataSetChanged();
                finish();
            }
        });
    }
}