package com.dsm.iriscalendar.data;

import com.dsm.iriscalendar.data.model.AuthResponse;
import com.dsm.iriscalendar.data.model.Category;
import com.dsm.iriscalendar.data.model.Schedule;
import com.dsm.iriscalendar.data.model.TimeResponse;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @POST("auth/login")
    Flowable<Response<AuthResponse>> login(@Body Object params);

    @POST("auth/signup")
    Flowable<Response<AuthResponse>> signUp(@Body Object params);

    @POST("time")
    Flowable<Response<Object>> timeSet(@Body Object params);

    @GET("time/{uuid}")
    Flowable<Response<TimeResponse>> getTimeSet(@Path("uuid") String uuid);

    @PATCH("time/{uuid}")
    Flowable<Response<Object>> updateTimeSet(@Body Object params, @Path("uuid") String uuid);

    @GET("category")
    Flowable<Response<Category>> getCategory();

    @PATCH("category")
    Flowable<Response<Category>> modifyCategory(@Body Category params);

    @POST("calender")
    Flowable<Response<Schedule>> addSchedule(@Body Object params);
}
