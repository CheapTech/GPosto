package br.com.senacrs.gposto.Controller;

public class UsuarioController {
    private final String BASE_URL = "http://www.gestoo.com.br/gposto/api/";

     /*
    public void getUsuarioWeb (String email, String senha, final UsuarioCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Usuario.class, new UsuarioDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Interface usuarioInterface = retrofit.create(Interface.class);

        Call<Usuario> usuarioWeb = usuarioInterface.logar(email, senha);

        usuarioWeb.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    callback.onUsuarioSuccess(response.body());
                }else {
                    callback.onUsuarioFailure("Erro "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onUsuarioFailure(t.getMessage());
            }
        });
    }

    public void sendUsuarioWeb(final Usuario usuario, final UsuarioCallback callback) throws Exception{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Interface services = retrofit.create(Interface.class);

        final Call<Usuario> user = services.cadastrarUsuario(usuario);
        user.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    callback.onUsuarioSuccess(response.body());
                }else {
                    callback.onUsuarioFailure("ERRO: " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onUsuarioFailure(t.getMessage());
            }
        });
    }*/
}
