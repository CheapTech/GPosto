package br.com.senacrs.gposto.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.senacrs.gposto.GUI.Callback.PostosCallback;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.WebApis.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostosController {
    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";

    public void sendUsuarioWeb(final Postos postos, final PostosCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitService services = retrofit.create(RetrofitService.class);

        final Call<Postos> posto = services.cadastrarPostos(postos);
        posto.enqueue(new Callback<Postos>() {
            @Override
            public void onResponse(Call<Postos> call, Response<Postos> response) {
                if (response.isSuccessful()){
                    callback.onPostosSuccess(response.body());
                }else {
                    callback.onPostosFailure("ERRO: " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Postos> call, Throwable t) {
                callback.onPostosFailure(t.getMessage());
            }
        });
    }
}
