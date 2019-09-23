package br.com.senacrs.gposto.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.senacrs.gposto.GUI.Callback.PrecosCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.WebApis.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrecosController {
    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";//

    public void getPrecosWeb(int id, final PrecosCallback callback) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Combustivel.class, new PrecosController())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService precosService = retrofit.create(RetrofitService.class);



        Call<List<Combustivel>> precosWeb = precosService.getListPrecos(id);

        precosWeb.enqueue(new Callback<List<Combustivel>>() {
            @Override
            public void onResponse(Call<List<Combustivel>> call, Response<List<Combustivel>> response) {
                if (response.isSuccessful()) {
                    callback.onPrecosSuccess(response.body());
                } else {
                    callback.onPrecosFailure("Erro " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Combustivel>> call, Throwable t) {
                callback.onPrecosFailure(t.getMessage());
            }
        });
    }
}
