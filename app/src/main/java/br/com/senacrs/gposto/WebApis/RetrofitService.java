package br.com.senacrs.gposto.WebApis;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.LibClass.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("CombustivelGet.php")
    Call<List<Combustivel>> getListCombustivel();

    @GET("ListarPrecosPosto.php")
    Call<List<Combustivel>> getListCombustivelByPosto(@Query(value = "id") int id);

    @GET("ListarCombustivel.php")
    Call<List<TopPostos>> getListTopPostos(@Query(value = "id") int id);

    @GET("UsuarioGETid.php")
    Call<Usuario> logarUsuario(@Query(value = "email", encoded = true) String email, @Query(value = "senha", encoded = true) String senha);

    @POST("InserirUsuario.php")
    Call<Usuario> registerUser(@Body Usuario usuario);

    @PUT("AtualizarPreco.php")
    Call<Combustivel> updateValorCombustivel(@Query (value = "idValor")int id, @Query(value = "preco") float preco);

    @POST()
    Call<Usuario> updateUser(@Body Usuario usuario);

    @POST("inserir.php")
    Call<Postos> cadastrarPostos(@Body Postos postos);

}
