package com.example.kosza.recipesapp.rest;

import com.example.kosza.recipesapp.model.ApiMultiResult;
import com.example.kosza.recipesapp.model.ApiSingleResult;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by kosza on 23.01.2016.
 */
public class RecipesController {

    public static final String API_KEY = "X-Mashape-Key: B96xQQNUeFmshzTERF80qvDxvveAp1SZYaJjsnJmsAuxCzt3Wi";
    public static final String AUTH_KEY = "0e72defab142ebbf3abc7765270c758d";
    private static Food2Work food2Work ;
    private static String baseUrl = "https://community-food2fork.p.mashape.com" ;

    public static Food2Work getClient() {
        if (food2Work == null) {

            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            food2Work = client.create(Food2Work.class);
        }
        return food2Work ;
    }

    public interface Food2Work {

        @Headers(API_KEY)
        @GET("/search")
        Call<ApiMultiResult> getRecipes(@Query("key") String key, @Query("q") String name);

        @Headers(API_KEY)
        @GET("/get")
        Call<ApiSingleResult> getRecipe(@Query("key") String key, @Query("rId") String id);

    }

}
