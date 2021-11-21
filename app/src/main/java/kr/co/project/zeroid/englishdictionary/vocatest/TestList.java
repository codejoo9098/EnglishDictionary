package kr.co.project.zeroid.englishdictionary.vocatest;

import java.util.HashMap;

import kr.co.project.zeroid.englishdictionary.singleton.SingletonVocaMap;

public class TestList {
    public static HashMap<String, HashMap<String, String>> totalData = null;
    public static String[] questionList;
    public static int totalQuestionNumber;
    public static String[] answerList;
    public static String[] submitList;
    public static Boolean[] isSolvedList;

    static {
        totalData = SingletonVocaMap.getInstance();
    }

    public static void setKoreanData() {
        int index = 0;
        totalQuestionNumber = totalData.size();

        questionList = new String[totalQuestionNumber];
        answerList = new String[totalQuestionNumber];
        submitList = new String[totalQuestionNumber];
        isSolvedList = new Boolean[totalQuestionNumber];

        for (String key : totalData.keySet()) {
            questionList[index] = key;
            isSolvedList[index] = false;
            index++;
        }
    }
}
