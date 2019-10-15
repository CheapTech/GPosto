package br.com.senacrs.gposto.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.senacrs.gposto.GUI.Callback.BandeiraCallback;
import br.com.senacrs.gposto.LibClass.Bandeira;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.Utilities.Deserializer.BandeiraDeserializer;
import br.com.senacrs.gposto.WebApis.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BandeiraController {
    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";

    public void getBandeiraWeb(final BandeiraCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Bandeira.class, new BandeiraDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        final Call<List<Bandeira>> bandeiraCall = service.getBandeira();
        bandeiraCall.enqueue(new Callback<List<Bandeira>>() {
            @Override
            public void onResponse(Call<List<Bandeira>> call, Response<List<Bandeira>> response) {
                if (response.isSuccessful()){
                    callback.onBandeiraSuccess(response.body());
                }else {
                    callback.onBandeiraFailure("ERROR: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Bandeira>> call, Throwable t) {
                callback.onBandeiraFailure(t.getMessage());
            }
        });
    }

    /*
    public void postBandeiraWeb(final Bandeira bandeira, final BandeiraCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        final Call<Bandeira> bandeiraCall = service.registerBandeira(bandeira);
        bandeiraCall.enqueue(new Callback<Bandeira>() {
            @Override
            public void onResponse(Call<Bandeira> call, Response<Bandeira> response) {
                if (response.isSuccessful()){
                    callback.onBandeiraSuccess(response.body());
                }else {
                    callback.onBandeiraFailure("ERROR: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Bandeira> call, Throwable t) {
                callback.onBandeiraFailure(t.getMessage());
            }
        });
    }*/
}
