package kr.co.project.zeroid.englishdictionary;

import androidx.lifecycle.ViewModel;

import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;

public class MainViewModel extends ViewModel {
    SingleLiveEvent<Void> navigateToSearchVoca;
    SingleLiveEvent<Void> navigateToVocaTest;
    SingleLiveEvent<Void> navigateToMyVoca;

    public MainViewModel() {
        navigateToSearchVoca = new SingleLiveEvent<>();
        navigateToVocaTest = new SingleLiveEvent<>();
        navigateToMyVoca = new SingleLiveEvent<>();
    }

    public void onSearchButtonClick() { navigateToSearchVoca.call(); }
    public void onVocaTestButtonClick() { navigateToVocaTest.call(); }
    public void onMyVocaButtonClick() { navigateToMyVoca.call(); }
}
