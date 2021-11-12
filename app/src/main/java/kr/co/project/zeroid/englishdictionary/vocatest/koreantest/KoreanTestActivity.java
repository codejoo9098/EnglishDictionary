package kr.co.project.zeroid.englishdictionary.vocatest.koreantest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityKoreanTestBinding;

public class KoreanTestActivity extends AppCompatActivity {
    ActivityKoreanTestBinding binding;
    KoreanTestViewModel viewModel;
    final static String MINUTE = "minute";
    final static String SECOND = "second";
    int minute, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_korean_test);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        minute = bundle.getInt(MINUTE, 1);
        second = bundle.getInt(SECOND, 0);
        binding.minute.setText(String.valueOf(minute));
        binding.second.setText(String.valueOf(second));

        viewModel = new ViewModelProvider(this).get(KoreanTestViewModel.class);
        viewModel.minute.setValue(minute);
        viewModel.second.setValue(second);
    }
}