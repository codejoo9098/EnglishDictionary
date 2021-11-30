package kr.co.project.zeroid.englishdictionary.vocatest.koreantest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityKoreanTestBinding;
import kr.co.project.zeroid.englishdictionary.etc.MyViewModelFactory;
import kr.co.project.zeroid.englishdictionary.etc.KoreanQuestionListAdapter;
import kr.co.project.zeroid.englishdictionary.vocatest.TestList;
import kr.co.project.zeroid.englishdictionary.vocatest.testresult.TestResultActivity;

public class KoreanTestActivity extends AppCompatActivity {
    ActivityKoreanTestBinding binding;
    KoreanTestViewModel viewModel;
    final static String MINUTE = "minute";
    final static String SECOND = "second";
    final static String TEST_TYPE = "test_type";
    ViewModelProvider.Factory factory;
    int minute, second;
    boolean testType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_korean_test);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        minute = bundle.getInt(MINUTE, 1);
        second = bundle.getInt(SECOND, 30);
        if (second > 59) {
            second = 59;
        }
        testType = bundle.getBoolean(TEST_TYPE);

        factory = new MyViewModelFactory(minute, second, testType);
        viewModel = new ViewModelProvider(this, factory).get(KoreanTestViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        viewModel.minute.setValue(String.valueOf(minute));
        viewModel.second.setValue(String.valueOf(second));
        KoreanQuestionListAdapter adapter = new KoreanQuestionListAdapter(TestList.isSolvedList, viewModel);
        binding.wordSelectContainer.setAdapter(adapter);

        viewModel.updateSolvedList.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {
                adapter.updateList(position);
            }
        });

        viewModel.eraseCurrentInput.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                binding.inputKorean.setText("");
            }
        });

        viewModel.isFinished.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) { navigateToResultPage(); }
        });
    }

    private void navigateToResultPage() {
        Intent intent = new Intent(this, TestResultActivity.class);
        startActivity(intent);
    }
}