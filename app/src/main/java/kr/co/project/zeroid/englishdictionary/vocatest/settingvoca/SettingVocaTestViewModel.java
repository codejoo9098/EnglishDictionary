package kr.co.project.zeroid.englishdictionary.vocatest.settingvoca;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;

public class SettingVocaTestViewModel extends ViewModel {
    SingleLiveEvent<Void> navigateToKoreanTest;
    SingleLiveEvent<Void> navigateToEnglishTest;
    int minute = 0;
    int second = 0;

    public SettingVocaTestViewModel() {
        navigateToKoreanTest = new SingleLiveEvent<>();
        navigateToEnglishTest = new SingleLiveEvent<>();
    }

    public void onKoreanTestButtonClick() { navigateToKoreanTest.call(); }
    public void onEnglishTestButtonClick() { navigateToEnglishTest.call(); }
    public void onMinuteChanged(CharSequence s, int start, int before, int count) {
        if (count != 0) {
            minute = Integer.parseInt(s.toString());
        }
    }
    public void onSecondChanged(CharSequence s, int start, int before, int count) {
        if (count != 0) {
            second = Integer.parseInt(s.toString());
        }
    }
}
