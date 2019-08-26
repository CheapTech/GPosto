package br.com.senacrs.gposto.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.Utilities.TopPostosDeserializer;
import br.com.senacrs.gposto.WebApis.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopPostosController {
    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";

    public void getTopPostosWeb(int id, final TopPostosCallback callback) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(TopPostos.class, new TopPostosDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService topPostosService = retrofit.create(RetrofitService.class);



        Call<List<TopPostos>> topPostosWeb = topPostosService.getListTopPostos(id);

        topPostosWeb.enqueue(new Callback<List<TopPostos>>() {
            @Override
            public void onResponse(Call<List<TopPostos>> call, Response<List<TopPostos>> response) {
                if (response.isSuccessful()) {
                    callback.onTopPostosSuccess(response.body());
                } else {
                    callback.onTopPostosFailure("Erro " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TopPostos>> call, Throwable t) {
                callback.onTopPostosFailure(t.getMessage());
            }
        });
    }

}
