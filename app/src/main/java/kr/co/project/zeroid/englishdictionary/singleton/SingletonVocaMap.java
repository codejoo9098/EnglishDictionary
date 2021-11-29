package kr.co.project.zeroid.englishdictionary.singleton;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import androidx.annotation.NonNull;
import kr.co.project.zeroid.englishdictionary.util.MakeAlertDialog;

public class SingletonVocaMap {
    private static HashMap<String,HashMap<String,String>> singletonVocaMap = null;
    private static boolean flag=false;
    private SingletonVocaMap() { }
    public static synchronized HashMap<String,HashMap<String,String>> getInstance() {
        if (singletonVocaMap == null) {
            HashMap<String,HashMap<String,String>> vocaMap = new HashMap<String, HashMap<String,String>>();
            singletonVocaMap = vocaMap;
        }
        return singletonVocaMap;
    }

    public static void readToFirebaseRealtimeDatabase(DatabaseReference databaseReference, Context context) {
        flag=false;
        databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", "Error getting data", task.getException());
                    MakeAlertDialog.makeAlertDialog(context).show();
                    SingletonVocaMap.singletonVocaMap=null;
                }
                else {
                    SingletonVocaMap.singletonVocaMap= (HashMap<String,HashMap<String,String>>)task.getResult().getValue();
                    flag=true;
                }
            }
        });
    }

    public static boolean getFlag() {
        return flag;
    }
}
