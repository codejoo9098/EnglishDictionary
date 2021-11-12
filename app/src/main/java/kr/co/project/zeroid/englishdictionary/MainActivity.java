package kr.co.project.zeroid.englishdictionary;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import kr.co.project.zeroid.englishdictionary.addVoca.AddVocaActivity;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityMainBinding;
import kr.co.project.zeroid.englishdictionary.vocatest.settingvoca.SettingVocaTestActivity;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;
    String androidId;

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

        viewModel.navigateToVocaTest.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                navigateToVocaTestPage();
            }
        });

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Toast.makeText(this,"androidId : "+androidId, Toast.LENGTH_SHORT).show();
    }

    void navigateToSearchVocaPage() {
        Intent intent = new Intent(this, AddVocaActivity.class);
        startActivity(intent);
    }

    void navigateToVocaTestPage() {
        Intent intent = new Intent(this, SettingVocaTestActivity.class);
        startActivity(intent);
    }
}