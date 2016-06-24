package projectx.itgo.com.APIServices;

import projectx.itgo.com.models.AppUser;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Niral on 17-06-2016.
 */
public interface LoginService {

    @POST("login")
    Call<String> basicLogin();
}
