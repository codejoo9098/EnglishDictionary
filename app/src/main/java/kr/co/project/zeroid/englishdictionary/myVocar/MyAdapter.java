package kr.co.project.zeroid.englishdictionary.myVocar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.project.zeroid.englishdictionary.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyWordViewHolder> {
    static public ArrayList<WordAndMean> itemList;
    private MyWordViewHolder holder; //뷰홀더 객체생성.

    public MyAdapter(){
        itemList=new ArrayList<>();
    }
    @NonNull
    @Override
    public MyAdapter.MyWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_my_word,parent,false);
        holder=new MyWordViewHolder(view);
        return holder;
    } //껍데기 만들기

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(WordAndMean item ){
        itemList.add(item);
    } //뷰를 만들 정보 추가(즉 뷰 추가)

    @Override
    public void onBindViewHolder(@NonNull MyWordViewHolder holder, int position) {
        WordAndMean wam=itemList.get(position);
        holder.setItem(wam); //해당 뷰를 추가.
    }

    class MyWordViewHolder extends RecyclerView.ViewHolder{
        private TextView no;
        private TextView english;
        private TextView koreanMean;
        public MyWordViewHolder(@NonNull View itemView) {
            super(itemView);
            no=itemView.findViewById(R.id.no);
            english=itemView.findViewById(R.id.english);
            koreanMean=itemView.findViewById(R.id.koreanMean);
            if(MyVocaActivity.myWordPage==1) {
                koreanMean.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        MyWordFragment.Didalog(view, no, english, koreanMean);
                        return false;
                    }
                });
            }
        }

        public void setItem(WordAndMean wam){ //뷰 한개 구성하기
            no.setText(""+(itemList.indexOf(wam)+1));
            Log.d("aaa",""+itemList.indexOf(wam)+","+wam.englishWord);
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
        }
    }


}
