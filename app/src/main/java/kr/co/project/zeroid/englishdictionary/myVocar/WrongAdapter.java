package kr.co.project.zeroid.englishdictionary.myVocar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.project.zeroid.englishdictionary.R;

public class WrongAdapter extends RecyclerView.Adapter<WrongAdapter.WrongViewHolder> {
    ArrayList<WordAndMean> itemList;
    private WrongViewHolder holder;
    public WrongAdapter(){
        itemList=new ArrayList<>();
    }
    @NonNull
    @Override
    public WrongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        //from은 해당 context로부터 LayoutInflater 가져오기.
        View view=inflater.inflate(R.layout.item_wrong_word,parent,false);
        holder=new WrongViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WrongViewHolder holder, int position) {
        WordAndMean wam=itemList.get(position);
        holder.setItem(wam);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(WordAndMean item ){
        itemList.add(item);
    } //뷰를 만들 정보 추가(즉 뷰 추가)



    public class WrongViewHolder extends RecyclerView.ViewHolder {
        private TextView no;
        private TextView english;
        private TextView koreanMean;
        private TextView wrongCount;
        public WrongViewHolder(@NonNull View itemView) {
            super(itemView);
            no=itemView.findViewById(R.id.wrong_no);
            english=itemView.findViewById(R.id.wrong_english);
            koreanMean=itemView.findViewById(R.id.wrong_koreanMean);
            wrongCount=itemView.findViewById(R.id.wrong_count);
        }
        public void setItem(WordAndMean wam){ //뷰 한개 구성하기
            no.setText(""+wam.number);
            english.setText(wam.englishWord);

            String s="";
            int s_count=wam.meanCount();
            if(s_count==1){ //뜻이 한개인 경우
                s=s+wam.getOneMean(0);
            }
            else{ //뜻이 두개 이상인 경우
                for(int i=0;i<s_count;i++) {
                    if (i == s_count - 1) {
                        s = s + (i + 1) + "." + wam.getOneMean(i);
                    } else {
                        s = s + (i + 1) + "." + wam.getOneMean(i) + "\n";
                    }
                }
            }
            koreanMean.setText(s);
            wrongCount.setText(wam.wrongCount+"번");
        }
    }
}
