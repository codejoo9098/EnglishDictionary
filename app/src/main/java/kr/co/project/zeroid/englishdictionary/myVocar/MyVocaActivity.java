package kr.co.project.zeroid.englishdictionary.myVocar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import kr.co.project.zeroid.englishdictionary.R;

public class MyVocaActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment search;
    Fragment myWord;
    Fragment wrongNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voca);

        frameLayout=findViewById(R.id.frame);

        myWord= MyWordFragment.newInstance();
        wrongNote= WrongNoteFragment.newInstance();
        bottomNavigationView=findViewById(R.id.BottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.myWord); //초기 선택 바텀네비게이션뷰 아이템.
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,myWord).commit();
        //초기화면 설정

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.search:
                        search = SearchFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, search).commit();
                        break;
                    case R.id.myWord:
                        //매번 프래그먼트 객체 초기화하지말고 그냥 교체만 하자.
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,myWord).commit();
                        break;
                    case R.id.wrongNote:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,wrongNote).commit();

                }

                return true; //
                // 트루로 해야 메뉴 아이템 선택시 바텀네비게이션뷰가 바뀐다.
            }
        });
    }

    public void wordAdd(View view){

        myWord=MyWordFragment.newInstance();
    }
}