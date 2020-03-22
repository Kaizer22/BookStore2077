package com.example.bookstore2077.ui.book;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore2077.R;
import com.example.bookstore2077.StoreHome;
import com.example.bookstore2077.data.Book;
import com.example.bookstore2077.data.Comment;
import com.example.bookstore2077.ui.containers.BookAdapter;
import com.example.bookstore2077.ui.shop.ShopFragment;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookPageFragment extends Fragment {

    private BookPageViewModel mViewModel;
    String bookId;


    public static BookPageFragment newInstance() {
        return new BookPageFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bookId = getArguments().getString("chosen_id");
        return inflater.inflate(R.layout.book_page_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookPageViewModel.class);
        // TODO: Use the ViewModel
        AsyncBookLoader loader = new AsyncBookLoader();
        Button button_back = getActivity().findViewById(R.id.button_back);
        Button button_isRead = getActivity().findViewById(R.id.bookPageAlreadyRead);
        Button button_music = getActivity().findViewById(R.id.bookPageMusic);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.book_page_fragment,
                        new ShopFragment()).commit();
            }
        });

        button_isRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncBookMarker marker = new AsyncBookMarker();
                marker.execute();
            }
        });

        button_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://music.yandex.ru/album/9658385"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        loader.execute();
    }



    private class AsyncBookMarker extends AsyncTask<String, String, String>{
        String answerHTTP;
        String server ="https://secure-shelf-96781.herokuapp.com/api/Read/" ;

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();

            SharedPreferences sharedPreferences = getActivity().getPreferences(Activity.MODE_PRIVATE);

            String user_id = sharedPreferences.getString("id", "NONE");
            HttpPost httpPost = new HttpPost(server);
            httpPost.setHeader("userId", user_id);
            httpPost.setHeader("bookId", bookId);

            Log.wtf("LOG", server + user_id + "---" + bookId);
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Button isRead = getActivity().findViewById(R.id.bookPageAlreadyRead);
            isRead.setText("ПРОЧИТАНО");
            Log.wtf("LOG_MARKER", answerHTTP);
        }
    }
    private class AsyncBookLoader extends AsyncTask<String, String, String>{

        String answerHTTP;
        String server ="https://secure-shelf-96781.herokuapp.com/api/Books/" ;

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();

            SharedPreferences sharedPreferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
            String id = sharedPreferences.getString("chosen_id", "NONE");
            server += id;
            HttpGet httpGet = new HttpGet(server);
            Log.wtf("LOG", server);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    answerHTTP = EntityUtils.toString(entity);
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
            String id;
            String bookName = "NONE";
            String ISBN = "NONE";
            float price = (float) 99.99;
            String category = "NONE";
            String author = "NONE";
            String coverImageLink = "";
            String description = "NONE";
            float rating = (float) 1.0;
            List<Comment> comments = new ArrayList<>();

            String commentText;
            String commentAuthor;
            String commentDate;
            try {

                Log.wtf("LOG", answerHTTP);

                JSONObject row = new JSONObject(answerHTTP);
                id = row.getString("id");
                bookName = row.getString("bookName");
                ISBN = row.getString("isbn");
                price = (float) row.getDouble("price");
                category = row.getString("category");
                author = row.getString("author");
                coverImageLink = row.getString("coverImageLink");
                rating = (float) row.getDouble("rating");
                description = row.getString("description");
                JSONArray commentsArray = row.getJSONArray("comments");
                for (int j = 0; j < commentsArray.length(); j++) {
                    JSONObject com = commentsArray.getJSONObject(j);
                    commentText = com.getString("text");
                    commentAuthor = com.getString("author");
                    commentDate = com.getString("date");
                    comments.add(new Comment(commentText, commentAuthor, commentDate));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ImageView imageView = getActivity().findViewById(R.id.bookPageImage);
            TextView bookTags = getActivity().findViewById(R.id.bookPageBookTags);
            TextView bookNameText = getActivity().findViewById(R.id.bookPageName);
            TextView bookAuthor = getActivity().findViewById(R.id.bookPageAuthor);
            TextView bookRating = getActivity().findViewById(R.id.bookPageRating);
            TextView bookDescription = getActivity().findViewById(R.id.bookPageDescription);
            Button bookBuy = getActivity().findViewById(R.id.bookPageButtonBuy);

            Picasso.get()
                    .load(coverImageLink)
                    .fit()
                    .error(R.drawable.book1)
                    .into(imageView);
            bookTags.setText(category);
            bookNameText.setText(bookName);
            bookAuthor.setText(author);
            bookRating.setText(Float.toString(rating));
            bookDescription.setText(description);
            bookBuy.setText("КУПИТЬ ЗА " + price + "RUR");
        }
    }
}


