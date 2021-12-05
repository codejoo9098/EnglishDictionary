package kr.co.project.zeroid.englishdictionary.vocatest.testresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.project.zeroid.englishdictionary.etc.Result;
import kr.co.project.zeroid.englishdictionary.etc.SingleLiveEvent;
import kr.co.project.zeroid.englishdictionary.vocatest.TestList;

public class TestResultViewModel extends ViewModel {
    final static String WRONG_COUNT = "-틀린횟수";

    DatabaseReference databaseReference, myDatabaseReference;

    MutableLiveData<Result[]> resultList;
    public LiveData<Result[]> getResultList() { return resultList; }
    MutableLiveData<String> totalQuestion;
    public LiveData<String> getTotalQuestion() { return totalQuestion; }
    MutableLiveData<String> correctQuestion;
    public LiveData<String> getCorrectQuestion() { return correctQuestion; }
    MutableLiveData<String> correctRate;
    public LiveData<String> getCorrectRate() { return correctRate; }
    SingleLiveEvent<Void> disconnectedNetworkEvent;
    SingleLiveEvent<Void> navigateToHomeEvent;
    SingleLiveEvent<Void> navigateToTestEvent;

    public TestResultViewModel(int networkStatus) {
        TestList.setCheckList();
        TestList.setResultList();

        resultList = new MutableLiveData<>();
        totalQuestion = new MutableLiveData<>();
        correctQuestion = new MutableLiveData<>();
        correctRate = new MutableLiveData<>();
        navigateToHomeEvent = new SingleLiveEvent<>();
        navigateToTestEvent = new SingleLiveEvent<>();
        disconnectedNetworkEvent = new SingleLiveEvent<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        myDatabaseReference = databaseReference.child("users").child(FirebaseAuth.getInstance().getUid());

        resultList.setValue(TestList.resultList);
        totalQuestion.setValue(String.valueOf(TestList.totalQuestionNumber));
        correctQuestion.setValue(String.valueOf(TestList.correctNumber));
        correctRate.setValue(String.format("%.1f", TestList.correctRate) + "%");

        if (networkStatus != 3) {
            updateWrongCount();
        }
    }

    private void updateWrongCount() {
        for (int i = 0; i < TestList.totalQuestionNumber; i++) {
            int wrongCount;

            if (!TestList.correctList[i]) {
                wrongCount = TestList.wrongCountList[i];
                wrongCount++;

                if (TestList.testType) {
                    myDatabaseReference
                            .child(TestList.questionList[i])
                            .child(WRONG_COUNT)
                            .setValue(String.valueOf(wrongCount));
                }
                else {
                    myDatabaseReference
                            .child(TestList.answerList[i].get(0))
                            .child(WRONG_COUNT)
                            .setValue(String.valueOf(wrongCount));
                }

            }
        }
    }

    public void onHomeButtonClick() {
        navigateToHomeEvent.call();
    }

    public void onTestButtonClick() {
        navigateToTestEvent.call();
    }
}
