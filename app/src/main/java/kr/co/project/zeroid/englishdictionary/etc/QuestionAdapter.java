package kr.co.project.zeroid.englishdictionary.etc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.project.zeroid.englishdictionary.databinding.QuestionListBinding;
import kr.co.project.zeroid.englishdictionary.vocatest.TestList;
import kr.co.project.zeroid.englishdictionary.vocatest.koreantest.KoreanTestViewModel;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.Holder>{
    Boolean[] problemList;
    KoreanTestViewModel viewModel;

    public QuestionAdapter(Boolean[] list, KoreanTestViewModel viewModel) {
        problemList = list;
        this.viewModel = viewModel;
    }

    class Holder extends RecyclerView.ViewHolder{
        QuestionListBinding binding;

        public Holder(QuestionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.number.setText(String.valueOf(position + 1));
            binding.setIsSolved(problemList[position]);
            binding.setPosition(position);
            binding.setViewModel(viewModel);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionListBinding binding = QuestionListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) { holder.bind(position); }

    @Override
    public int getItemCount() { return problemList.length; }

    public void updateList(int index) {
        problemList[index] = true;
        notifyItemChanged(index);
    }
}
