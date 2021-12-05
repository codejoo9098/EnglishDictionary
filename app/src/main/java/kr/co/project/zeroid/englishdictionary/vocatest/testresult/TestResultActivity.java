package kr.co.project.zeroid.englishdictionary.vocatest.testresult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityTestResultBinding;
import kr.co.project.zeroid.englishdictionary.etc.MyViewModelFactory;
import kr.co.project.zeroid.englishdictionary.etc.Result;
import kr.co.project.zeroid.englishdictionary.etc.ResultListAdapter;
import kr.co.project.zeroid.englishdictionary.util.NetworkStatus;
import kr.co.project.zeroid.englishdictionary.vocatest.settingvoca.SettingVocaTestActivity;

public class TestResultActivity extends AppCompatActivity {
    final static String WARNING_MESSAGE = "네트워크에 연결되어 있지 않아서 틀린횟수를 업데이트 할 수 없습니다.";
    ActivityTestResultBinding binding;
    TestResultViewModel viewModel;
    ResultListAdapter adapter;
    ViewModelProvider.Factory factory;
    int networkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_result);
        binding.setLifecycleOwner(this);
        networkStatus = NetworkStatus.getConnectivityStatus(this);
        factory = new MyViewModelFactory(networkStatus);
        viewModel = new ViewModelProvider(this, factory).get(TestResultViewModel.class);
        binding.setViewModel(viewModel);

        adapter = new ResultListAdapter();
        binding.resultListContainer.setAdapter(adapter);

        if (NetworkStatus.getConnectivityStatus(this) == 3) {
            Toast.makeText(this, WARNING_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        viewModel.resultList.observe(this, new Observer<Result[]>() {
            @Override
            public void onChanged(Result[] results) {
                adapter.setList(results);
            }
        });

        viewModel.navigateToTestEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                navigateToTestPage();
            }
        });

        viewModel.navigateToHomeEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                finish();
            }
        });
    }

    private void navigateToTestPage() {
        Intent intent = new Intent(this, SettingVocaTestActivity.class);
        startActivity(intent);
    }
}