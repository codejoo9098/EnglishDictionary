package kr.co.project.zeroid.englishdictionary.vocatest.koreantest;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KoreanTestViewModel extends ViewModel {
    MutableLiveData<Integer> minute;
    public LiveData<Integer> getMinute() {
        Log.d("Fuck", "시발");
        return minute;
    }

    MutableLiveData<Integer> second;
    public LiveData<Integer> getSecond() {
        return second;
    }

    public KoreanTestViewModel() {
        Log.d("Fuck", "좆같네");
        minute = new MutableLiveData<>();
        second = new MutableLiveData<>();
    }
}
