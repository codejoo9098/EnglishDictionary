package kr.co.project.zeroid.englishdictionary.etc;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.project.zeroid.englishdictionary.databinding.ResultListBinding;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.Holder> {
    Result[] resultList;

    class Holder extends RecyclerView.ViewHolder {
        ResultListBinding binding;

        public Holder(ResultListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Result result) {
            binding.setResult(result);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResultListBinding binding = ResultListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(resultList[position]);
    }

    @Override
    public int getItemCount() {
        return resultList.length;
    }

    public void setList(Result[] list) {
        this.resultList = list;
    }
}
