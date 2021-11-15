package kr.co.project.zeroid.englishdictionary.vocatest.koreantest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.hilt.lifecycle.ViewModelFactoryModules;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityKoreanTestBinding;
import kr.co.project.zeroid.englishdictionary.etc.MyViewModelFactory;

public class KoreanTestActivity extends AppCompatActivity {
    ActivityKoreanTestBinding binding;
    KoreanTestViewModel viewModel;
    final static String MINUTE = "minute";
    final static String SECOND = "second";
    ViewModelProvider.Factory factory;
    int minute, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_korean_test);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        minute = bundle.getInt(MINUTE, 1);
        second = bundle.getInt(SECOND, 0);

        factory = new MyViewModelFactory(minute, second);
        viewModel = new ViewModelProvider(this, factory).get(KoreanTestViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        viewModel.minute.setValue(String.valueOf(minute));
        viewModel.second.setValue(String.valueOf(second));

        viewModel.isFinished.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                navigateToResultPage();
            }
        });
    }

    private void navigateToResultPage() {
        finish();
    }
}