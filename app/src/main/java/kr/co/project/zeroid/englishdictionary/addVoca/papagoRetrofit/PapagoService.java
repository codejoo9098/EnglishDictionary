package kr.co.project.zeroid.englishdictionary.addVoca.papagoRetrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PapagoService {
    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    Call<PapagoResponseDto> requestPapago(
            @Header("X-Naver-Client-Id")String clientId,
            @Header("X-Naver-Client-Secret")String clientSecret,
            @Field("source")String source,
            @Field("target")String target,
            @Field("text")String text);
}
