package kr.co.project.zeroid.englishdictionary.vocatest.koreantest;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;
import kr.co.project.zeroid.englishdictionary.vocatest.TestList;

public class KoreanTestViewModel extends ViewModel {
    final static int ONE_MINUTE = 60;

    public String[] questionList = TestList.questionList;
    public String[] submitList = TestList.submitList;
    public int totalQuestionNumber = TestList.totalQuestionNumber;
    public Boolean[] isSolvedList = TestList.isSolvedList;

    Thread thread;
    MutableLiveData<String> minute;
    public LiveData<String> getMinute() { return minute; }
    MutableLiveData<String> second;
    public LiveData<String> getSecond() { return second; }
    MutableLiveData<String> displayWord;
    public LiveData<String> getDisplayWord() { return displayWord; }
    MutableLiveData<Integer> currentProgress;
    public LiveData<Integer> getCurrentProgress() { return currentProgress; }
    MutableLiveData<String> currentQuestionNumber;
    public LiveData<String> getCurrentQuestionNumber() { return currentQuestionNumber; }
    MutableLiveData<Boolean> isFinished;
    public LiveData<Boolean> getIsFinished() { return isFinished; }
    MutableLiveData<Integer> currentWordIndex;
    public LiveData<Integer> getCurrentWordIndex() { return currentWordIndex; }
    SingleLiveEvent<Integer> updateSolvedList;
    String submitWord;
    int totalTime;
    int progressState = 0;

    public KoreanTestViewModel(int inputMinute, int inputSecond) {

        minute = new MutableLiveData<>();
        second = new MutableLiveData<>();
        displayWord = new MutableLiveData<>();
        currentProgress = new MutableLiveData<>();
        currentQuestionNumber = new MutableLiveData<>();
        currentWordIndex = new MutableLiveData<>();
        updateSolvedList = new SingleLiveEvent<>();
        isFinished = new MutableLiveData<>();

        currentWordIndex.setValue(0);
        displayWord.setValue(questionList[0]);
        currentQuestionNumber.setValue("1/" + totalQuestionNumber);

        totalTime = inputMinute * ONE_MINUTE + inputSecond;
        thread = new Thread(new Runnable() {

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
        });

        thread.start();
    }

    public void onAnswerChanged(CharSequence s, int start, int before, int count) {
        if (count != 0) {
            submitWord = s.toString();
        }
    }

    public void onClickCompleteWord() {
        int index = currentWordIndex.getValue();
        if (!isSolvedList[index]) currentProgress.setValue(++progressState);
        submitList[index] = submitWord;
        updateSolvedList.setValue(index);

        index = ++index % totalQuestionNumber;

        currentWordIndex.setValue(index);
        currentQuestionNumber.setValue((index % totalQuestionNumber + 1) + "/" + totalQuestionNumber);
        displayWord.setValue(questionList[index % totalQuestionNumber]);
    }

    public void onClickQuestion(int pos) {
        currentWordIndex.setValue(pos);
        currentQuestionNumber.setValue((pos + 1) +"/" + totalQuestionNumber);
        displayWord.setValue(questionList[pos]);
    }

    public void onSubmitButtonClick() {
        isFinished.setValue(true);
    }
}
