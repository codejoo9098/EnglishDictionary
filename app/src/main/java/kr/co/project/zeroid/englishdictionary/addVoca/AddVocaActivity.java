package kr.co.project.zeroid.englishdictionary.addVoca;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import kr.co.project.zeroid.englishdictionary.R;

public class AddVocaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voca);

        TextView addVocaEditText=findViewById(R.id.addVocaEditText);
        Button addVocaButton=findViewById(R.id.searchButton);
        TextView vocaResultTextView=findViewById(R.id.vocaResultTextView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        addVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= addVocaEditText.getText().toString();
                Pattern onlyEnglish = Pattern.compile("^[a-zA-Z0-9]+$");
                Pattern onlyKorean=Pattern.compile("^[가-힣]+$");
                if(onlyEnglish.matcher(text).matches()) {
                    addVocaEditText.setText(null);
                    String result=searchEnglish(text);
                    vocaResultTextView.setText(result);
                } else if(onlyKorean.matcher(text).matches()) {
                    addVocaEditText.setText(null);
                    String result=searchKorean(text);
                    vocaResultTextView.setText(result);
                } else {
                    addVocaEditText.setText(null);
                    Toast.makeText(getApplicationContext(),"영어만 입력하거나 한글만 입력하세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String searchEnglish(String text) {
        Document doc = null;
        try {
            doc = Jsoup.connect(getString(R.string.baseURL)+text).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result=doc.head().select("meta[property=og:description]").first().attr("content");
        return result;
    }

    private String searchKorean(String text) {
        Document doc = null;
        try {
            doc = Jsoup.connect(getString(R.string.baseURL)+text).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element elem=doc.getElementsByAttributeValue("data-tiara-layer","word eng").select("ul[class=list_search]").first();
        String result=elem.text();
        return result;
    }
}