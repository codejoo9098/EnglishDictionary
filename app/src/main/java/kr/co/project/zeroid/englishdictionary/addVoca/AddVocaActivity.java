package kr.co.project.zeroid.englishdictionary.addVoca;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import kr.co.project.zeroid.englishdictionary.R;
import kr.co.project.zeroid.englishdictionary.addVoca.papagoRetrofit.PapagoResponseDto;
import kr.co.project.zeroid.englishdictionary.addVoca.papagoRetrofit.PapagoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddVocaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voca);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(getString(R.string.baseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TextView addVocaEditText=findViewById(R.id.addVocaEditText);
        Button addVocaButton=findViewById(R.id.searchButton);
        TextView vocaResultTextView=findViewById(R.id.vocaResultTextView);

        PapagoService papagoService=retrofit.create(PapagoService.class);
        addVocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText=addVocaEditText.getText().toString();
                Call<PapagoResponseDto> call=papagoService.requestPapago(
                        getString(R.string.CLIENT_ID),
                        getString(R.string.CLIENT_SECRET),
                        "en",
                        "ko",
                        inputText);

                call.enqueue(new Callback<PapagoResponseDto>() {

                    @Override
                    public void onResponse(Call<PapagoResponseDto> call, Response<PapagoResponseDto> response) {
                        PapagoResponseDto papagoResponse=response.body();
                        vocaResultTextView.setText(getTranslatedText(papagoResponse));
                    }

                    @Override
                    public void onFailure(Call<PapagoResponseDto> call, Throwable t) {
                        AlertDialog.Builder dialog= new AlertDialog.Builder(AddVocaActivity.this);
                        dialog.setTitle("알림!");
                        dialog.setMessage("통신에 실패했습니다.");
                        dialog.show();
                    }
                });
            }
        });
    }

    private String getTranslatedText(PapagoResponseDto papagoResponse) {
        JsonObject jsonObject = papagoResponse.message;
        jsonObject = jsonObject.getAsJsonObject("result");
        String translatedText=jsonObject.get("translatedText").getAsString();
        return translatedText;
    }
}