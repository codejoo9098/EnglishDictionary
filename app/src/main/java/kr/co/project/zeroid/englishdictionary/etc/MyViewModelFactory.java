package kr.co.project.zeroid.englishdictionary.etc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import kr.co.project.zeroid.englishdictionary.vocatest.koreantest.KoreanTestViewModel;
import kr.co.project.zeroid.englishdictionary.vocatest.testresult.TestResultViewModel;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private int minute;
    private int second;
    private boolean testType;

    public MyViewModelFactory(int minute, int second, boolean testType){
        this.minute = minute;
        this.second = second;
        this.testType = testType;
    }

    public MyViewModelFactory(int minute) {
        this.minute = minute;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(KoreanTestViewModel.class)) {
            return (T) new KoreanTestViewModel(minute, second, testType);
        }
        else if (modelClass.isAssignableFrom(TestResultViewModel.class)) {
            return (T) new TestResultViewModel(minute);
        }
        throw new IllegalArgumentException("ViewModel Not Found");
    }
}
