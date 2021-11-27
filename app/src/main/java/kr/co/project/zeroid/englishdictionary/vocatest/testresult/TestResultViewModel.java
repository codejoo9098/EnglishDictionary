package kr.co.project.zeroid.englishdictionary.vocatest.testresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.project.zeroid.englishdictionary.etc.Result;
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

    public TestResultViewModel() {
        TestList.setKoreanAnswerList();
        TestList.setCheckList();
        TestList.setResultList();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        myDatabaseReference = databaseReference.child("users").child(FirebaseAuth.getInstance().getUid());

        resultList = new MutableLiveData<>();
        totalQuestion = new MutableLiveData<>();
        correctQuestion = new MutableLiveData<>();
        correctRate = new MutableLiveData<>();

        resultList.setValue(TestList.resultList);
        totalQuestion.setValue(String.valueOf(TestList.totalQuestionNumber));
        correctQuestion.setValue(String.valueOf(TestList.correctNumber));
        correctRate.setValue(String.format("%.2f", TestList.correctRate));

        for (int i = 0; i < TestList.totalQuestionNumber; i++) {
            int wrongCount;

            if (!TestList.correctList[i]) {
                wrongCount = TestList.wrongCountList[i];
                wrongCount++;

                myDatabaseReference
                        .child(TestList.questionList[i])
                        .child(WRONG_COUNT)
                        .setValue(String.valueOf(wrongCount));
            }
        }
    }
}
