package kr.co.project.zeroid.englishdictionary.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.firebase.database.FirebaseDatabase;

import kr.co.project.zeroid.englishdictionary.singleton.SingletonVocaMap;

public class MakeAlertDialog {
    public static AlertDialog makeAlertDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("접속실패").setMessage("통신상태가 원활하지 않습니다.\n이동통신사 혹은 와이파이의 상태를 확인하시기 바랍니다.");
        builder.setPositiveButton("재연결", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SingletonVocaMap.readToFirebaseRealtimeDatabase(FirebaseDatabase.getInstance().getReference(), context);
            }
        });

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
}
