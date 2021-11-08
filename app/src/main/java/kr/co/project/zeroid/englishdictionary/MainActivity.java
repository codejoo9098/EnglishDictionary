package kr.co.project.zeroid.englishdictionary;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.co.project.zeroid.englishdictionary.databinding.ActivityMainBinding;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import kr.co.project.zeroid.englishdictionary.addVoca.AddVocaActivity;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.navigateToSearchVoca.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                navigateToSearchVocaPage();
            }
        });
    }

    void navigateToSearchVocaPage() {
        Intent intent = new Intent(this, AddVocaActivity.class);
        startActivity(intent);
    }
}