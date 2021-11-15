package kr.co.project.zeroid.englishdictionary.etc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import kr.co.project.zeroid.englishdictionary.vocatest.koreantest.KoreanTestViewModel;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private int minute;
    private int second;

    public MyViewModelFactory(int minute, int second){
        this.minute = minute;
        this.second = second;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(KoreanTestViewModel.class)) {
            return (T) new KoreanTestViewModel(minute, second);
        }
        throw new IllegalArgumentException("ViewModel Not Found");
    }
}
