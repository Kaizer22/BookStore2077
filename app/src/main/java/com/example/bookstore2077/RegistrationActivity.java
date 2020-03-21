package com.example.bookstore2077;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookstore2077.data.model.LoggedInUser;
import com.example.bookstore2077.ui.login.LoginActivity;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void onBackClick(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void onRegisterClick(View view){
        EditText login = findViewById(R.id.registrationLogin);
        EditText password = findViewById(R.id.registrationPassword);
        EditText name = findViewById(R.id.registrationName);
        EditText surname = findViewById(R.id.registrationSurname);
        EditText email = findViewById(R.id.registrationEmail);
        AsyncRegistrator asyncRegistrator = new AsyncRegistrator(login.getText().toString(), password.getText().toString(), email.getText().toString(),
                name.getText().toString(), surname.getText().toString());
        Toast.makeText(getApplicationContext(),  "HELLo", Toast.LENGTH_SHORT).show();
        asyncRegistrator.execute();
    }

    private class AsyncRegistrator extends AsyncTask<String, String, String> {

        HttpURLConnection connection;
        DataOutputStream wr;
        String responseCode = "-1";
        URL url;

        String answerHTTP;
        String login;
        String password;
        String email;
        String name;
        String surname;
        String server = "https://secure-shelf-96781.herokuapp.com/api/User/";

        public AsyncRegistrator(String login, String password, String email, String name, String surname) {
            this.login = login;
            this.password = password;
            this.email = email;
            this.name = name;
            this.surname = surname;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            /*
            try{
                url = new URL(server);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.connect();




                LoggedInUser user = new LoggedInUser(login, password, email, name, surname);
                String strSuf = new Gson().toJson(user);

                wr = new DataOutputStream(connection.getOutputStream());
                wr.
                wr.writeBytes(strSuf);
                wr.close();

                responseCode = Integer.toString(connection.getResponseCode());

                return responseCode;
            } catch (IOException e) {
                e.printStackTrace();
            } finally() {
                connection.disconnect();
            }
*/




/*
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(server);
            HttpPost httpPost = new HttpPost(server);
            HttpPut httpPut = new HttpPut(server);
            httpPost.setHeader("login", login);
            httpPost.setHeader("password", password);
            httpPost.setHeader("email", email);
            httpPost.setHeader("name", name);
            httpPost.setHeader("surname", surname);
            try {

                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    answerHTTP = EntityUtils.toString(entity);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

 */
            return null;


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.wtf("LOG", answerHTTP);
            Toast.makeText(getApplicationContext(), answerHTTP, Toast.LENGTH_LONG).show();
            if (answerHTTP.contains("2")) {
                Log.wtf("LOG", answerHTTP + " Error");
                Intent storeIntent = new Intent(getApplicationContext(), StoreHome.class);
                startActivity(storeIntent);
                Toast.makeText(getApplicationContext(), "BRUH", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
