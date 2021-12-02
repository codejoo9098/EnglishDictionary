package kr.co.project.zeroid.englishdictionary.vocatest.settingvoca;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;
import kr.co.project.zeroid.englishdictionary.vocatest.TestList;

public class SettingVocaTestViewModel extends ViewModel {
    SingleLiveEvent<Void> navigateToKoreanTest;
    SingleLiveEvent<Void> navigateToEnglishTest;
    SingleLiveEvent<Void> closeTestEvent;
    boolean isEmptyData;
    int minute = 0;
    int second = 0;

    public SettingVocaTestViewModel() {
        navigateToKoreanTest = new SingleLiveEvent<>();
        navigateToEnglishTest = new SingleLiveEvent<>();
        closeTestEvent = new SingleLiveEvent<>();

        isEmptyData = TestList.checkEmptyData();
        if (isEmptyData) {
            closeTestEvent.call();
        }
    }

    public void onKoreanTestButtonClick() {
        TestList.setKoreanQuestionList();
        TestList.setKoreanAnswerList();
        navigateToKoreanTest.call();
    }
    public void onEnglishTestButtonClick() {
        TestList.setEnglishQuestionList();
        TestList.setEnglishAnswerList();
        navigateToEnglishTest.call();
    }
    public void onMinuteChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("")) {
            minute = Integer.parseInt(s.toString());
        }
        else {
            minute = 1;
        }
    }
    public void onSecondChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("")) {
            second = Integer.parseInt(s.toString());
        }
        else {
            second = 30;
        }
    }
}
