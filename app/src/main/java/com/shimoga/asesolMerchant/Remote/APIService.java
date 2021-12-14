package com.shimoga.asesolMerchant.Remote;


import com.shimoga.asesolMerchant.Model.MyResponse;
import com.shimoga.asesolMerchant.Model.Sender;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAtuiEadk:APA91bFicfmSamFc4kTaL9hUGqcKB16S2j8CItZe1Q-2m_LM-q4_3jvQV6mPPwNpM5oJR7u4r3Sryvvc9nWKrgLAvk1pXWvGnZI3xwZwgP_bH3yCI7t8iEwux_GKMWjWiz8FlGheCFsj"

            }

    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
