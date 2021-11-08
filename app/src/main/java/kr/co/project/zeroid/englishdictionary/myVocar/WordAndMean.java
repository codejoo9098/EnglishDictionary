package kr.co.project.zeroid.englishdictionary.myVocar;

import java.util.ArrayList;

public class WordAndMean { //뷰홀더에 담길 객체
    int number; //NO.몇번인지
    String englishWord;
    ArrayList<String> mean;
    int mean_count;
    int wrongCount;


    public WordAndMean(int number,String e,ArrayList<String> m,int mc,int wc){
        this.number=number;
        this.englishWord=e;
        this.mean=m;
        this.mean_count=mc;
        this.wrongCount=wc;
    }

    public int getNumber() {
        return number;
    }
    public String getEnglishWord() { return englishWord; }
    public ArrayList<String> getMean() {
        return mean;
    }
    public int getMean_count(){return mean_count;}
    public int getWrongCount() {
        return wrongCount;
    }


    public void setNumber(int n) {
        this.number = n;
    }
    public void setEnglishWord(String englishWord) { this.englishWord = englishWord; }
    public void setMean(ArrayList<String> mean) {
        this.mean = mean;
    } //만들긴하나 안씀
    public void setMean_count(int c){this.mean_count=c;}
    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

    public int indexOfMean(String mean){
        return mean.indexOf(mean);
    }

    public int meanCount(){
        return mean.size();
    } //뜻 개수
    public void replaceMean(String replace_mean,int i) {
        mean.set(i,replace_mean);
    } //뜻 교체

    public void addMean(String addMean,int i){
        mean.add(addMean);
    }//뜻 추가

    public void deleteMean(String deleteMean){
        mean.remove(deleteMean);
    }//뜻 삭제. 어떻게 구현할까

    public String getOneMean(int i){
        return mean.get(i);
    }

}
