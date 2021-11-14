package kr.co.project.zeroid.englishdictionary.addVoca;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import kr.co.project.zeroid.englishdictionary.R;

public class CheckableAddVocaListView extends LinearLayout implements Checkable {
    public CheckableAddVocaListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox vocaCheckBox=findViewById(R.id.vocaCheckBox);
        if (vocaCheckBox.isChecked() != checked) {
            vocaCheckBox.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        CheckBox vocaCheckBox=findViewById(R.id.vocaCheckBox);
        return vocaCheckBox.isChecked();
    }

    @Override
    public void toggle() {
        CheckBox vocaCheckBox=findViewById(R.id.vocaCheckBox);

        setChecked(vocaCheckBox.isChecked() ? false : true) ;
    }
}
