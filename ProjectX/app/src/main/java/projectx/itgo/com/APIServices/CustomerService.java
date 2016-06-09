package projectx.itgo.com.APIServices;

import java.util.List;

import projectx.itgo.com.models.Customer;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Niral on 09-06-2016.
 */
public interface CustomerService {

    @GET("regularCustomers")
    Call<List<Customer>> getRegularCustomers();
}
