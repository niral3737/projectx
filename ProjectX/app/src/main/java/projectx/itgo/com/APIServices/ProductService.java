package projectx.itgo.com.APIServices;

import java.util.List;

import projectx.itgo.com.models.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Niral on 24-06-2016.
 */
public interface ProductService {

    @GET("products")
    Call<List<Product>> getProducts();

    @Headers("Content-Type: application/json")
    @POST("products")
    Call<String> addProduct(@Body Product product);

    @Headers("Content-Type: application/json")
    @PUT("products/{productId}")
    Call<String> updateProduct(@Path("productId") String id, @Body Product product);

    @DELETE("products/{productId}")
    Call<String> deleteProduct(@Path("productId") String id);
}
