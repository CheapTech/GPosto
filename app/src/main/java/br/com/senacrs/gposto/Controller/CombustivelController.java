package br.com.senacrs.gposto.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.senacrs.gposto.GUI.Callback.CombustivelCallback;
import br.com.senacrs.gposto.GUI.Callback.CombustivelUpdateCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.Utilities.Deserializer.CombustivelDeserializer;
import br.com.senacrs.gposto.WebApis.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CombustivelController {
    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";

    public void getCombustivelWeb (final CombustivelCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Combustivel.class, new CombustivelDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService combustivelService = retrofit.create(RetrofitService.class);

        Call<List<Combustivel>> combustivelWeb = combustivelService.getListCombustivel();

        combustivelWeb.enqueue(new Callback<List<Combustivel>>() {
            @Override
            public void onResponse(Call<List<Combustivel>> call, Response<List<Combustivel>> response) {
                if(response.isSuccessful()){
                    callback.onCombustivelSuccess(response.body());
                }else {
                    callback.onCombustivelFailure("Erro "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Combustivel>> call, Throwable t) {
                callback.onCombustivelFailure(t.getMessage());
            }
        });
    }

    public void getCombustivelById (int id, final CombustivelCallback callback) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Combustivel.class, new CombustivelDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService precosService = retrofit.create(RetrofitService.class);

        Call<List<Combustivel>> precosWeb = precosService.getListCombustivelByPosto(id);

        precosWeb.enqueue(new Callback<List<Combustivel>>() {
            @Override
            public void onResponse(Call<List<Combustivel>> call, Response<List<Combustivel>> response) {
                if (response.isSuccessful()) {
                    callback.onCombustivelSuccess(response.body());
                } else {
                    callback.onCombustivelFailure("Erro " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Combustivel>> call, Throwable t) {
                callback.onCombustivelFailure(t.getMessage());
            }
        });
    }

    public void updateCombustivelWeb(int id, Float preco, final CombustivelUpdateCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitService services = retrofit.create(RetrofitService.class);

        final Call<Combustivel> user = services.updateValorCombustivel(id, preco);
        user.enqueue(new Callback<Combustivel>() {
            @Override
            public void onResponse(Call<Combustivel> call, Response<Combustivel> response) {
                if (response.isSuccessful()){
                    callback.onCombustivelUpdateSuccess(true);
                }else {
                    callback.onCombustivelUpdateFailure("ERROR: " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Combustivel> call, Throwable t) {
                callback.onCombustivelUpdateFailure(t.getMessage());
            }
        });
    }
}
