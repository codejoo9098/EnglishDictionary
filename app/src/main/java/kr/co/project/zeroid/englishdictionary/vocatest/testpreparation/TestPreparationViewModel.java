package kr.co.project.zeroid.englishdictionary.vocatest.testpreparation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;

public class TestPreparationViewModel extends ViewModel {
    MutableLiveData<String> second;
    public LiveData<String> getSecond() { return second; }
    MutableLiveData<Boolean> isFinished;
    SingleLiveEvent<Void> navigateToTest;

    public TestPreparationViewModel() {
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
