package br.com.senacrs.gposto.WebApis;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.TopPostos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("CombustivelGet.php")
    Call<List<Combustivel>> getListCombustivel();

    @GET("gasolinaTeste.php")
    Call<List<TopPostos>> getListTopPostos(@Query(value = "id") int id);
}
