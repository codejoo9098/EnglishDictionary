package kr.co.project.zeroid.englishdictionary.etc;

public class Result {
    public String problem;
    public String answer;
    public String userAnswer;
    public Boolean isCorrect;

    public Result(String problem, String answer, String userAnswer, Boolean isCorrect) {
        this.problem = problem;
        this.answer = answer;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
    }
}
