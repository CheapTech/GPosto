package br.com.senacrs.gposto.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.senacrs.gposto.GUI.Callback.UsuarioCallback;
import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.Utilities.Deserializer.CombustivelDeserializer;
import br.com.senacrs.gposto.Utilities.Deserializer.UsuarioDeserializer;
import br.com.senacrs.gposto.Utilities.Utils;
import br.com.senacrs.gposto.WebApis.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioController {
    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";

    public void getUserWeb(int id, final UsuarioCallback callback)throws Exception{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Usuario.class, new UsuarioDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService services = retrofit.create(RetrofitService.class);

       /* final Call<Usuario> user = services.getUser(id);
        user.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    callback.onUsuarioSuccess(response.body());
                }else {
                    callback.onUsuarioFailure("ERROR: " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onUsuarioFailure(t.getMessage());
            }
        });*/
    }

    public void postUserWeb(final Usuario usuario, final UsuarioCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitService services = retrofit.create(RetrofitService.class);

        final Call<Usuario> user = services.registerUser(usuario);
        user.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    callback.onUsuarioSuccess(response.body());
                }else {
                    callback.onUsuarioFailure("ERROR: " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onUsuarioFailure(t.getMessage());
            }
        });
    }

    public void updateUserWeb(final Usuario usuario, final UsuarioCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        final Call<Usuario> user = service.updateUser(usuario);
        user.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    callback.onUsuarioSuccess(response.body());
                }else {
                    callback.onUsuarioFailure("ERROR: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onUsuarioFailure(t.getMessage());
            }
        });
    }
}
