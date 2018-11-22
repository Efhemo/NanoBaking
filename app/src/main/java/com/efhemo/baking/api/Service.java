package com.efhemo.baking.api;

import com.efhemo.baking.model.BakeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface Service {

    @GET
    Call<List<BakeResponse>> getBaker(@Url String url);
}
