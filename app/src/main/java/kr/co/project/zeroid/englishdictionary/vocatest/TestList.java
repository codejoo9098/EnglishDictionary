package kr.co.project.zeroid.englishdictionary.vocatest;

public class TestList {
    public static String[] questionList = {"apple", "banana", "hello"};
    public static int totalQuestionNumber;
    public static String[] answerList;
    public static String[] submitList;
    public static Boolean[] isSolvedList;

    static {
        totalQuestionNumber = questionList.length;
        answerList = new String[totalQuestionNumber];
        submitList = new String[totalQuestionNumber];
        isSolvedList = new Boolean[totalQuestionNumber];
    }

    public static void resetData() {
        for (int i = 0; i < totalQuestionNumber; i++) {
            isSolvedList[i] = false;
        }
    }
}
