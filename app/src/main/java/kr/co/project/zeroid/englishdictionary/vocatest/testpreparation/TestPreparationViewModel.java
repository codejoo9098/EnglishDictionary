package kr.co.project.zeroid.englishdictionary.vocatest.testpreparation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;
import kr.co.project.zeroid.englishdictionary.vocatest.TestList;

public class TestPreparationViewModel extends ViewModel {
    MutableLiveData<String> second;
    public LiveData<String> getSecond() { return second; }
    MutableLiveData<Boolean> isFinished;
    SingleLiveEvent<Void> navigateToTest;

    public TestPreparationViewModel() {
        Log.d("제로이드", "뷰모델 생성");
        navigateToTest = new SingleLiveEvent<>();
        isFinished = new MutableLiveData<>();
        second = new MutableLiveData<>();
        second.setValue("5");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    try {
                        Thread.sleep(1000);
                        second.postValue(String.valueOf(5 - i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                isFinished.postValue(false);
            }
        }).start();
    }
}
