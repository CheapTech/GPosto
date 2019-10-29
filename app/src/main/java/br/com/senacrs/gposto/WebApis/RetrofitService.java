package br.com.senacrs.gposto.WebApis;

import android.renderscript.Sampler;
import android.util.Base64;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Bandeira;
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

    @GET("LoginUsuario.php")
    Call<Usuario> logarUsuario(@Query(value = "email", encoded = true) String email, @Query(value = "senha", encoded = true) String senha);

    @POST("InserirUsuario.php")
    Call<Usuario> registerUser(@Body Usuario usuario);

    @PUT("AtualizarPreco.php")
    Call<Combustivel> updateValorCombustivel(@Query (value = "idValor")int id, @Query(value = "preco") float preco);

    @GET("ListarBandeiras.php")
    Call<List<Bandeira>> getBandeira();

    @POST("InserirPosto.php")
    Call<Postos> cadastrarPostos(@Body Postos postos);

    @POST()
    Call<Postos> sendRatingPosto(@Query(value = "posto_id") int id_posto,@Query(value = "usuario_id") int id_usuario, @Query(value = "pontucao") float aval);

    @PUT()
    Call<Usuario> updateUser(@Query (value = "idValor")int id,@Body Usuario usuario);

    @POST()
    Call<Usuario> sendUserPhoto(@Query(value = "usuario_id") int id, @Query(value = "photo")Base64 photo);

    @GET()
    Call<Postos> getBandeiraPosto(@Query(value = "posto_id") int posto_id);
}
