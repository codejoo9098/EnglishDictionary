package kr.co.project.zeroid.englishdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import kr.co.project.zeroid.englishdictionary.myVocar.MyWordFragment;
import kr.co.project.zeroid.englishdictionary.myVocar.SearchFragment;
import kr.co.project.zeroid.englishdictionary.myVocar.WrongNoteFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}