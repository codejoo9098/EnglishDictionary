package kr.co.project.zeroid.englishdictionary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import kr.co.project.zeroid.englishdictionary.addVoca.AddVocaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addVocaButton=findViewById(R.id.addVocaButton);
        addVocaButton.setOnClickListener(view -> {
            Intent addVocaActivityIntent= new Intent(this, AddVocaActivity.class);
            startActivity(addVocaActivityIntent);
        });
    }
}