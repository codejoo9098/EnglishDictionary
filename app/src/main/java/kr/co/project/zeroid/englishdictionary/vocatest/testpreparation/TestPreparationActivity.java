package kr.co.project.zeroid.englishdictionary.vocatest.testpreparation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityTestPreparationBinding;
import kr.co.project.zeroid.englishdictionary.vocatest.englishtest.EnglishTestActivity;
import kr.co.project.zeroid.englishdictionary.vocatest.koreantest.KoreanTestActivity;
import kr.co.project.zeroid.englishdictionary.vocatest.testpreparation.TestPreparationViewModel;

public class TestPreparationActivity extends AppCompatActivity {
    ActivityTestPreparationBinding binding;
    TestPreparationViewModel viewModel;
    final static String MINUTE = "minute";
    final static String SECOND = "second";
    final static String TEST_TYPE = "test_type";
    final static boolean KOREAN_TEST = true;
    final static boolean ENGLISH_TEST = false;
    int minute, second;
    boolean testType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_preparation);
        viewModel = new ViewModelProvider(this).get(TestPreparationViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        minute = bundle.getInt(MINUTE);
        second = bundle.getInt(SECOND);
        testType = bundle.getBoolean(TEST_TYPE);

        viewModel.isFinished.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) { navigateToTestPage(); }
        });
    }

    void navigateToTestPage() {
        Bundle bundle = new Bundle();
        bundle.putInt(MINUTE, minute);
        bundle.putInt(SECOND, second);

        if (testType == KOREAN_TEST) {
            Intent intent = new Intent(this, KoreanTestActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (testType == ENGLISH_TEST){
            Intent intent = new Intent(this, EnglishTestActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}