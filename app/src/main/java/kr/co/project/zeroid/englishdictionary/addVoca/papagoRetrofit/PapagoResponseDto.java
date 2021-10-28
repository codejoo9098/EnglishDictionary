package kr.co.project.zeroid.englishdictionary.addVoca.papagoRetrofit;

import com.google.gson.JsonObject;

public class PapagoResponseDto {
    public JsonObject message;

    public PapagoResponseDto(JsonObject message) {
        this.message=message;
    }
}
