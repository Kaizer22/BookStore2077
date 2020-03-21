package com.example.bookstore2077.ui.login;

import android.app.Activity;

import android.annotation.SuppressLint;

import android.app.Activity;



import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProviders;



import android.content.Intent;

import android.content.pm.PackageInfo;

import android.content.pm.PackageManager;

import android.graphics.Typeface;

import android.os.AsyncTask;

import android.os.Bundle;



import androidx.annotation.Nullable;

import androidx.annotation.StringRes;

import androidx.appcompat.app.AppCompatActivity;



import android.text.Editable;

import android.text.TextWatcher;

import android.util.Log;

import android.view.KeyEvent;

import android.view.View;

import android.view.inputmethod.EditorInfo;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ProgressBar;

import android.widget.TextView;

import android.widget.Toast;

import com.example.bookstore2077.R;
import com.example.bookstore2077.RegistrationActivity;
import com.example.bookstore2077.StoreHome;
import com.example.bookstore2077.data.model.LoggedInUser;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);
                //loginViewModel.login(usernameEditText.getText().toString(),
                        //passwordEditText.getText().toString());
                AsyncLogger asyncLogger = new AsyncLogger(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                asyncLogger.execute();
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();


    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    public void onRegistrationClick(View view){
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    private class AsyncLogger extends AsyncTask<String, String, String> {


        String answerHTTP;
        String id;
        String login;
        String password;
        String email;
        String name;
        String surname;
        String server = "https://secure-shelf-96781.herokuapp.com/api/Login/";

       ;

        HttpURLConnection connection;
        DataOutputStream wr;
        String responseCode = "-1";
        URL url;
        public AsyncLogger(String login, String password) {
            this.login = login;
            this.password = password;
            answerHTTP = "NONE";

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(server);
            httpPost.setHeader("login", login);
            httpPost.setHeader("password", password);
            try {

                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    answerHTTP = EntityUtils.toString(entity);
                } else if (response.getStatusLine().getStatusCode() == 204){
                    answerHTTP = "ERROR";
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            Log.wtf("LOG", answerHTTP);
            Toast.makeText(getApplicationContext(), answerHTTP, Toast.LENGTH_SHORT).show();
            if (answerHTTP.contains("id")){
                try{


                    JSONObject row = new JSONObject(answerHTTP);
                    id = row.getString("id");
                    login = row.getString("login");
                    password = row.getString("password");
                    email = row.getString("email");
                    name = row.getString("name");
                    surname = row.getString("surname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent storeIntent = new Intent(getApplicationContext(), StoreHome.class);
                storeIntent.putExtra("id", id);
                storeIntent.putExtra("login", login);
                storeIntent.putExtra("password", password);
                storeIntent.putExtra("email", email);
                storeIntent.putExtra("name", name);
                storeIntent.putExtra("surname", surname);
                startActivity(storeIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Ошибка! Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                Log.wtf("ERROR", "HELLO");
            }
        }
    }
}
