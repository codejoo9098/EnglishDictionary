package kr.co.project.zeroid.englishdictionary.addVoca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.project.zeroid.englishdictionary.R;

public class AddVocaCustomView extends BaseAdapter {
    LayoutInflater layoutInflater = null;
    private ArrayList<AddVocaListData> listViewData = null;
    private int count = 0;

    public AddVocaCustomView(ArrayList<AddVocaListData> listData) {
        listViewData = listData;
        count = listViewData.size();
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return listViewData.get(position).voca;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (layoutInflater == null)
            {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = layoutInflater.inflate(R.layout.custom_add_voca, parent, false);
        }

        TextView vocaNumber=convertView.findViewById(R.id.vocaNumber);
        TextView vocaTextView = convertView.findViewById(R.id.vocaTextView);

        vocaNumber.setText(position+1+". ");
        vocaTextView.setText(listViewData.get(position).voca);

        return convertView;
    }
}