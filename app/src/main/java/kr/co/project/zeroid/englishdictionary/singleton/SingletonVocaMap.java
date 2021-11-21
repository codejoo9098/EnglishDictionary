package kr.co.project.zeroid.englishdictionary.singleton;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class SingletonVocaMap {
    private static HashMap<String,HashMap<String,String>> singletonVocaMap = null;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SingletonVocaMap() { }
    public static synchronized HashMap<String,HashMap<String,String>> getInstance(Context context) {
        if (singletonVocaMap == null) {
            HashMap<String,HashMap<String,String>> vocaMap = new HashMap<String, HashMap<String,String>>();
            singletonVocaMap = vocaMap;
        }
        return singletonVocaMap;
    }

    public static void readToFirebaseRealtimeDatabase(DatabaseReference databaseReference) {
        databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", "Error getting data", task.getException());
                    SingletonVocaMap.singletonVocaMap=null;
                }
                else {
                    SingletonVocaMap.singletonVocaMap= (HashMap<String,HashMap<String,String>>)task.getResult().getValue();
                }
            }
        });
    }
}