package projectx.itgo.com.utilities;

import projectx.itgo.com.APIServices.CustomerService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Niral on 10-06-2016.
 */
public class RetrofitUtil {

    private static Retrofit retrofit;

    private static void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ResourceURI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static CustomerService getCustomerService() {
        initRetrofit();
        return retrofit.create(CustomerService.class);
    }
}
