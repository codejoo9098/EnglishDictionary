package kr.co.project.zeroid.englishdictionary.vocatest.settingvoca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.databinding.ActivitySettingVocaTestBinding;
import kr.co.project.zeroid.englishdictionary.vocatest.TestList;
import kr.co.project.zeroid.englishdictionary.vocatest.testpreparation.TestPreparationActivity;

public class SettingVocaTestActivity extends AppCompatActivity {
    final static String MINUTE = "minute";
    final static String SECOND = "second";
    final static String TEST_TYPE = "test_type";
    final static boolean KOREAN_TEST = true;
    final static boolean ENGLISH_TEST = false;
    ActivitySettingVocaTestBinding binding;
    SettingVocaTestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_voca_test);
        viewModel = new ViewModelProvider(this).get(SettingVocaTestViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.navigateToKoreanTest.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                onNavigateKoreanTestPage();
            }
        });

        viewModel.navigateToEnglishTest.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                onNavigateEnglishTestPage();
            }
        });

        viewModel.closeTestEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                unableToStartTestEvent();
            }
        });
    }

    private void onNavigateKoreanTestPage() {
        Bundle bundle = new Bundle();

        bundle.putInt(MINUTE, viewModel.minute);
        bundle.putInt(SECOND, viewModel.second);
        bundle.putBoolean(TEST_TYPE, KOREAN_TEST);

        Intent intent = new Intent(this, TestPreparationActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void onNavigateEnglishTestPage() {
        Bundle bundle = new Bundle();

        bundle.putInt(MINUTE, viewModel.minute);
        bundle.putInt(SECOND, viewModel.second);
        bundle.putBoolean(TEST_TYPE, ENGLISH_TEST);

        Intent intent = new Intent(this, TestPreparationActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void unableToStartTestEvent() {
        Toast.makeText(getApplicationContext(), "저장되어 있는 단어가 없습니다!", Toast.LENGTH_SHORT).show();
        finish();
    }
}