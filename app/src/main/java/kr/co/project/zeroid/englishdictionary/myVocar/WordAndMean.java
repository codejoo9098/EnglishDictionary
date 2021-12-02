package kr.co.project.zeroid.englishdictionary.myVocar;

import java.util.ArrayList;
import java.util.Comparator;

public class WordAndMean implements Comparable<WordAndMean>{ //뷰홀더에 담길 객체
    String englishWord;
    ArrayList<String> mean;
    int mean_count;
    int wrongCount;

    public WordAndMean(String e,ArrayList<String> m,int wc){
        this.englishWord=e;
        this.mean=m;
        this.mean_count=m.size();
        this.wrongCount=wc;
    }
    
    public String getEnglishWord() { return englishWord; }
    public ArrayList<String> getMean() {
        return mean;
    }
    public int getMean_count(){return mean_count;}
    public int getWrongCount() {
        return wrongCount;
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

    @Override
    public int compareTo(WordAndMean wordAndMean) {
        if (wordAndMean.wrongCount < wrongCount) {
            return 1;
        } else if (wordAndMean.wrongCount > wrongCount) {
            return -1;
        }
        return 0;
    }
}
class EnglishComparator implements Comparator<WordAndMean> {
    @Override
    public int compare(WordAndMean f1, WordAndMean f2) {
        return f1.englishWord.compareTo(f2.englishWord);
    }
}


