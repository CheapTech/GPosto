package br.com.senacrs.gposto.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.senacrs.gposto.GUI.Callback.BandeiraCallback;
import br.com.senacrs.gposto.LibClass.Bandeira;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.Utilities.Deserializer.BandeiraDeserializer;
import br.com.senacrs.gposto.WebApis.RetrofitService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BandeiraController {

    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";


    public void getBandeiraWeb(int id, final BandeiraCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Bandeira.class, new BandeiraDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService services = retrofit.create(RetrofitService.class);
    }

    public void postBandeiraWeb(final Bandeira bandeira, final BandeiraCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitService services = retrofit.create(RetrofitService.class);

    }
}
