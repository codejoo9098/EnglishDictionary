package kr.co.project.zeroid.englishdictionary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import kr.co.project.zeroid.englishdictionary.addVoca.AddVocaActivity;
import kr.co.project.zeroid.englishdictionary.databinding.ActivityMainBinding;
import kr.co.project.zeroid.englishdictionary.myVocar.MyVocaActivity;
import kr.co.project.zeroid.englishdictionary.singleton.SingletonVocaMap;
import kr.co.project.zeroid.englishdictionary.util.MakeAlertDialog;
import kr.co.project.zeroid.englishdictionary.util.MakeToast;
import kr.co.project.zeroid.englishdictionary.util.NetworkStatus;
import kr.co.project.zeroid.englishdictionary.vocatest.settingvoca.SettingVocaTestActivity;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;

    Button logoutButton;
    Button withdrawalButton;

    Button myVocaButton;
    Button vocaTestButton;

    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;

    private SignInButton googleSignInButton;

    //?????????????????? ?????????????????? ?????? ??????????????? Firebase??? ????????????
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //DatabaseReference??? ????????????????????? ?????? ????????? ???????????? ????????? ???????????? ??????.
    //?????? ????????? ???????????????????????? ??? ???????????????
    //??????(????????? ?????? ??????)??? ?????? ????????? ??????????????? ?????? ????????????.
    private DatabaseReference databaseReference=database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        myVocaButton=findViewById(R.id.myVocaButton);
        vocaTestButton=findViewById(R.id.vocaTestButton);

        viewModel.navigateToSearchVoca.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                navigateToSearchVocaPage();
            }
        });

        viewModel.navigateToVocaTest.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                navigateToVocaTestPage();
            }
        });

        viewModel.navigateToMyVoca.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                navigateToMyVocaPage();
            }
        });

        // ?????????????????? ?????? ?????? ??????
        firebaseAuth = FirebaseAuth.getInstance();

        googleSignInButton = findViewById(R.id.googleSignInButton);

        // Google ???????????? ?????? ??????
        // GoogleSignInOptions ????????? ????????? ??? requestIdToken??? ??????
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkStatus.getConnectivityStatus(getApplicationContext())!=3) {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    launcher.launch(signInIntent);
                } else {
                    MakeToast.makeToast(getApplicationContext(),"????????? ????????? ????????? ?????????.").show();
                }
            }
        });

        logoutButton=findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                googleSignInButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
                withdrawalButton.setVisibility(View.GONE);
                myVocaButton.setVisibility(View.GONE);
                vocaTestButton.setVisibility(View.GONE);
            }
        });

        withdrawalButton=findViewById(R.id.withdrawalButton);

        withdrawalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getCurrentUser().delete();
                databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).removeValue();
                googleSignInButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
                withdrawalButton.setVisibility(View.GONE);
                myVocaButton.setVisibility(View.GONE);
                vocaTestButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(firebaseAuth.getCurrentUser()!=null) {
            //DB?????? ????????? ????????? Map?????? ??????
            if(NetworkStatus.getConnectivityStatus(getApplicationContext())!=3) {
                Log.d("????????????", "????????? ??????");
                SingletonVocaMap.readToFirebaseRealtimeDatabase(databaseReference,MainActivity.this);
                googleSignInButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);
                withdrawalButton.setVisibility(View.VISIBLE);
                myVocaButton.setVisibility(View.VISIBLE);
                vocaTestButton.setVisibility(View.VISIBLE);
            } else {
                MakeAlertDialog.makeAlertDialog(MainActivity.this).show();
            }
        }
    }

    void navigateToSearchVocaPage() {
        Intent intent = new Intent(this, AddVocaActivity.class);
        startActivity(intent);
    }

    void navigateToVocaTestPage() {
        Intent intent = new Intent(this, SettingVocaTestActivity.class);
        startActivity(intent);
    }

    void navigateToMyVocaPage() {
        Intent intent = new Intent(this, MyVocaActivity.class);
        startActivity(intent);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            // ?????? ????????? ??????
                            Log.d("????????????", "????????? ??????");
                            MainActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account);
                            googleSignInButton.setVisibility(View.GONE);
                            logoutButton.setVisibility(View.VISIBLE);
                            withdrawalButton.setVisibility(View.VISIBLE);
                            myVocaButton.setVisibility(View.VISIBLE);
                            vocaTestButton.setVisibility(View.VISIBLE);
                        } catch (ApiException e) {
                            MainActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            MakeToast.makeToast(getApplicationContext(),"?????????????????? ?????? ????????? ???????????????.").show();
                        }
                    }
                }
            });

    // ???????????? ??????????????? ???????????? ?????? GoogleSignInAccount ???????????? ID ????????? ????????????
    // Firebase ????????? ?????? ????????? ???????????? Firebase ????????? ?????? ????????? ????????? Firebase??? ???????????????.
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //DB?????? ????????? ????????? Map?????? ??????
                            MainActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            SingletonVocaMap.readToFirebaseRealtimeDatabase(databaseReference,MainActivity.this);
                            MakeToast.makeToast(getApplicationContext(),"????????? ??????").show();
                        } else {
                            MainActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            MakeToast.makeToast(getApplicationContext(),"????????? ??????").show();
                        }

                    }
                });
    }
}

