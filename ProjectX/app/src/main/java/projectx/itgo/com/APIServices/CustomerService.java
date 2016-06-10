package projectx.itgo.com.APIServices;

import java.util.List;

import projectx.itgo.com.models.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Customer Resource for Api Calls
 * Created by Niral on 09-06-2016.
 */
public interface CustomerService {

    @GET("regularCustomers")
    Call<List<Customer>> getRegularCustomers();

    @Headers("Content-Type: application/json")
    @POST("regularCustomers")
    Call<String> addRegularCustomer(@Body Customer customer);
}
