package kr.co.project.zeroid.englishdictionary.vocatest.koreantest;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class KoreanTestViewModel extends ViewModel {
    final static int ONE_MINUTE = 60;
    MutableLiveData<String> minute;
    public LiveData<String> getMinute() { return minute; }
    MutableLiveData<String> second;
    public LiveData<String> getSecond() { return second; }
    MutableLiveData<String> displayWord;
    public LiveData<String> getDisplayWord() { return displayWord; }
    MutableLiveData<Integer> currentProgress;
    public LiveData<Integer> getCurrentProgress() { return currentProgress; }
    MutableLiveData<String> currentWord;
    public LiveData<String> getCurrentWord() { return currentWord; }
    MutableLiveData<Boolean> isFinished;
    public LiveData<Boolean> getIsFinished() { return isFinished; }
    ArrayList<String> wordList; // 나중에 교체 예정
    int questionNumber;
    public int getQuestionNumber() { return questionNumber; }
    int completeWord = 0;
    int totalTime;

    public KoreanTestViewModel(int inputMinute, int inputSecond) {
        minute = new MutableLiveData<>();
        second = new MutableLiveData<>();
        displayWord = new MutableLiveData<>();
        currentProgress = new MutableLiveData<>();
        currentWord = new MutableLiveData<>();
        isFinished = new MutableLiveData<>();
        wordList = new ArrayList<>();

        wordList.add("apple");
        wordList.add("banana");
        wordList.add("hello");

        displayWord.setValue(wordList.get(0));
        questionNumber = wordList.size();
        currentWord.setValue("1/" + questionNumber);

        totalTime = inputMinute * ONE_MINUTE + inputSecond;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int countMinute = inputMinute;
                int countSecond = inputSecond;

                for (int i = 1; i <= totalTime; i++) {
                    try {
                        Thread.sleep(1000);
                        if ((totalTime - i) % ONE_MINUTE == ONE_MINUTE - 1) {
                            countMinute--;
                            countSecond = ONE_MINUTE - 1;
                            minute.postValue(String.valueOf(countMinute));
                            second.postValue(String.valueOf(countSecond));
                        }
                        else {
                            countSecond--;
                            second.postValue(String.valueOf(countSecond));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                isFinished.postValue(true);
            }
        }).start();
    }

    public void onClickCompleteWord() {
        completeWord++;
        currentProgress.setValue(completeWord);
        currentWord.setValue((completeWord % questionNumber + 1) + "/" + questionNumber);
        displayWord.setValue(wordList.get(completeWord % questionNumber));
    }
}
