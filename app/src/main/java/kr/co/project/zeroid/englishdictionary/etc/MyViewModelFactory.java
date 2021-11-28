package kr.co.project.zeroid.englishdictionary.etc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import kr.co.project.zeroid.englishdictionary.vocatest.koreantest.KoreanTestViewModel;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private int minute;
    private int second;
    private boolean testType;

    public MyViewModelFactory(int minute, int second, boolean testType){
        this.minute = minute;
        this.second = second;
        this.testType = testType;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(KoreanTestViewModel.class)) {
            return (T) new KoreanTestViewModel(minute, second, testType);
        }
        throw new IllegalArgumentException("ViewModel Not Found");
    }
}
