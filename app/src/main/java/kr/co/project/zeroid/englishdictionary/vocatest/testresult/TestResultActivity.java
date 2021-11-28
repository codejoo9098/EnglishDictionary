package kr.co.project.zeroid.englishdictionary.vocatest.testresult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityTestResultBinding;
import kr.co.project.zeroid.englishdictionary.etc.Result;
import kr.co.project.zeroid.englishdictionary.etc.ResultListAdapter;

public class TestResultActivity extends AppCompatActivity {
    ActivityTestResultBinding binding;
    TestResultViewModel viewModel;
    ResultListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_result);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(TestResultViewModel.class);
        binding.setViewModel(viewModel);

        adapter = new ResultListAdapter();
        binding.resultListContainer.setAdapter(adapter);

        viewModel.resultList.observe(this, new Observer<Result[]>() {
            @Override
            public void onChanged(Result[] results) {
                adapter.setList(results);
            }
        });
    }
}