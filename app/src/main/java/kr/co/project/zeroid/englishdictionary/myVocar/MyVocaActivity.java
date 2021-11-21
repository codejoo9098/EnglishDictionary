package kr.co.project.zeroid.englishdictionary.myVocar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.singleton.SingletonVocaMap;

public class MyVocaActivity extends AppCompatActivity {
    static BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment search;
    Fragment myWord;
    Fragment wrongNote;
    static int searchPage;
    static int myWordPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voca);
        Log.d("firekmj","나만의단어장액티비티 생성");
        MyWordFragment.isFirst=0; //처음 액티비티 시작시에는 통신으로 최신화하기.
        frameLayout=findViewById(R.id.frame);

        myWord= MyWordFragment.newInstance();
        wrongNote= WrongNoteFragment.newInstance();
        bottomNavigationView=findViewById(R.id.BottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.myWord); //초기 선택 바텀네비게이션뷰 아이템.


        getSupportFragmentManager().beginTransaction().replace(R.id.frame,myWord).commit();
        //초기화면 설정
        searchPage=0; myWordPage=1;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.search:
                        searchPage=1;
                        myWordPage=0;
                        search = SearchFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, search).commit();
                        break;
                    case R.id.myWord:
                        searchPage=0;
                        myWordPage=1;
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


    public void finish_myVocaActivity(View view){

        finish();
    }

}