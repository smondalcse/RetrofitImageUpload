package com.nitolmotors.retrofitimageupload;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface OurRetrofitClient {

    @POST("retrofit.php")
    @FormUrlEncoded
    Call<ResponseClass> UploadImage(@Field("name") String name, @Field("image") String image);

    @Multipart
    @POST("controller/upload")
    Call<ResponseClass> Upload(
            @Part MultipartBody.Part photo,
            @Part("description")RequestBody description
            );

    @Streaming
    @GET
    Call<ResponseBody> downloadFileByUrl(@Url String fileUrl);
}
