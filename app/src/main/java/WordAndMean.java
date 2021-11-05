public class WordAndMean {
    int n;
    String englishWord;
    String mean;
    int wrongCount;

    public WordAndMean(int n,String e,String m,int wc){
        this.n=n;
        this.englishWord=e;
        this.mean=m;
        this.wrongCount=wc;
    }

    public int getN() {
        return n;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getMean() {
        return mean;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

}
