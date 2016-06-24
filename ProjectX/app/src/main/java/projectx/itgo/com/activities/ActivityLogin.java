package projectx.itgo.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import projectx.itgo.com.APIServices.LoginService;
import projectx.itgo.com.ActivityMain;
import projectx.itgo.com.R;
import projectx.itgo.com.database.DBHelper;
import projectx.itgo.com.utilities.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button loginButton;
    ProgressWheel loginProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginProgressWheel = (ProgressWheel) findViewById(R.id.login_activity_progress_wheel);
        loginButton = (Button) findViewById(R.id.user_login_button);
        usernameEditText = (EditText) findViewById(R.id.user_username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.user_password_edit_text);
        usernameEditText.setText("");
        passwordEditText.setText("");
        usernameEditText.setError(null);
        passwordEditText.setError(null);
        final DBHelper dbHelper = new DBHelper(this);
        if (dbHelper.getUser() != null) {
            Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
            startActivity(intent);
            finish();
        } else {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validate()) {
                        loginProgressWheel.setVisibility(View.VISIBLE);
                        dbHelper.addUser(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                        LoginService loginService = ServiceGenerator.createService(LoginService.class, usernameEditText.getText().toString(), passwordEditText.getText().toString());
                        Call<String> appUserCall = loginService.basicLogin();
                        appUserCall.clone().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() == 200) {
                                    Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                                    startActivity(intent);
                                    finish();
                                } else if (response.code() == 401) {
                                    Toast.makeText(ActivityLogin.this, "Invalid login credentials.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ActivityLogin.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                                }
                                loginProgressWheel.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(ActivityLogin.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                                loginProgressWheel.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            });

        }

    }

    private boolean validate() {
        if (usernameEditText.getText().toString().isEmpty()) {
            usernameEditText.setError("Please enter username.");
            return false;
        }
        if (passwordEditText.getText().toString().isEmpty()) {
            passwordEditText.setError("Please enter password.");
            return false;
        }
        return true;
    }
}

