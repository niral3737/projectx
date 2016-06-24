package projectx.itgo.com.APIServices;

import java.util.List;

import projectx.itgo.com.models.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Customer Resource for Api Calls
 * Created by Niral on 09-06-2016.
 */
public interface CustomerService {

    @GET("customers")
    Call<List<Customer>> getRegularCustomers();

    @Headers("Content-Type: application/json")
    @POST("customers")
    Call<String> addRegularCustomer(@Body Customer customer);

    @Headers("Content-Type: application/json")
    @PUT("customers/{customerId}")
    Call<String> updateCustomer(@Path("customerId") String id, @Body Customer customer);

    @DELETE("customers/{customerId}")
    Call<String> deleteRegularCustomer(@Path("customerId") String id);
}
