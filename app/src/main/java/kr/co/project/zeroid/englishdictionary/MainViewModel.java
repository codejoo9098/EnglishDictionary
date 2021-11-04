package kr.co.project.zeroid.englishdictionary;

import androidx.lifecycle.ViewModel;

import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;

public class MainViewModel extends ViewModel {
    SingleLiveEvent<Void> navigateToSearchVoca;

    public MainViewModel() {
        navigateToSearchVoca = new SingleLiveEvent<>();
    }

    public void onSearchButtonClick() {
        navigateToSearchVoca.call();
    }
}
