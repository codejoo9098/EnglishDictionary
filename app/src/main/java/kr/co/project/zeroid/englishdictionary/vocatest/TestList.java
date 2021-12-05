package kr.co.project.zeroid.englishdictionary.vocatest;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import kr.co.project.zeroid.englishdictionary.etc.Result;
import kr.co.project.zeroid.englishdictionary.singleton.SingletonVocaMap;

public class TestList {
    final static String WRONG_COUNT = "-틀린횟수";
    public static HashMap<String, HashMap<String, String>> totalData = null;
    public static boolean testType;

    public static String[] questionList;
    public static int totalQuestionNumber;
    public static ArrayList<String>[] answerList;

    public static String[] submitList;
    public static Boolean[] isSolvedList;

    public static Boolean[] correctList;
    public static int[] wrongCountList;
    public static int correctNumber = 0;
    public static double correctRate = 0.0;
    public static Result[] resultList;

    public static boolean checkEmptyData() {
        totalData = SingletonVocaMap.getInstance();
        return totalData.size() == 0;
    }

    public static void setKoreanQuestionList() {
        int index = 0;
        testType = true;
        totalQuestionNumber = totalData.size();

        questionList = new String[totalQuestionNumber];
        answerList = new ArrayList[totalQuestionNumber];
        submitList = new String[totalQuestionNumber];
        isSolvedList = new Boolean[totalQuestionNumber];
        correctList = new Boolean[totalQuestionNumber];
        resultList = new Result[totalQuestionNumber];
        wrongCountList = new int[totalQuestionNumber];
        correctNumber = 0;
        correctRate = 0.0;

        for (String key: totalData.keySet()) {
            questionList[index] = key;
            isSolvedList[index] = false;
            index++;
        }
    }

    public static void setEnglishQuestionList() {
        int index = 0;
        testType = false;
        totalQuestionNumber = totalData.size();

        questionList = new String[totalQuestionNumber];
        answerList = new ArrayList[totalQuestionNumber];
        submitList = new String[totalQuestionNumber];
        isSolvedList = new Boolean[totalQuestionNumber];
        correctList = new Boolean[totalQuestionNumber];
        resultList = new Result[totalQuestionNumber];
        wrongCountList = new int[totalQuestionNumber];
        correctNumber = 0;
        correctRate = 0.0;

        for (String key: totalData.keySet()) {
            HashMap<String, String> questionData = totalData.get(key);
            int num = (int)( Math.random() * (questionData.size() - 1) ) + 1;
            num *= -1;

            questionList[index] = questionData.get(String.valueOf(num));
            wrongCountList[index] = Integer.parseInt(questionData.get(WRONG_COUNT));
            isSolvedList[index] = false;
            index++;
        }
    }

    public static void setKoreanAnswerList() {
        HashMap<String, String> map;

        for (int i = 0; i < totalQuestionNumber; i++) {
            map = totalData.get(questionList[i]);
            answerList[i] = new ArrayList<>();

            if (map == null) {
                answerList[i].add("데이터 없음");
                continue;
            }

            for (String key: map.keySet()) {
                if (!key.equals(WRONG_COUNT)) {
                    answerList[i].add(map.get(key));
                }
                else {
                    wrongCountList[i] = Integer.parseInt(map.get(key));
                }
            }
        }
    }

    public static void setEnglishAnswerList() {
        int index = 0;

        for (String key: totalData.keySet()) {
            answerList[index] = new ArrayList<>();
            answerList[index].add(key);
            index++;
        }
    }

    public static void setCheckList() {
        String userAnswer;

        for (int i = 0; i < totalQuestionNumber; i++) {
            correctList[i] = false;

            if (submitList[i] != null && !submitList[i].equals("")) {
                submitList[i] = submitList[i].trim();
                userAnswer = submitList[i];
            }
            else {
                submitList[i] = "미입력";
                continue;
            }

            for (String s: answerList[i]) {
                if (userAnswer.equals(s)) {
                    correctList[i] = true;
                    correctNumber++;
                    break;
                }
            }
        }

        correctRate = (double) correctNumber * 100 / totalQuestionNumber;
    }

    public static void setResultList() {
        String problem;
        String answer;
        String userAnswer;

        for (int i = 0; i < totalQuestionNumber; i++) {
            StringBuilder temp = new StringBuilder();

            problem = questionList[i];
            for (String s: answerList[i]) {
                temp.append(s).append("\n");
            }
            answer = temp.toString();
            answer = answer.trim();
            userAnswer = submitList[i];

            resultList[i] = new Result(problem, answer, userAnswer, correctList[i]);
        }

    }
}
