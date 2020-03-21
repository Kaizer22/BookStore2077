package com.example.bookstore2077.ui.shop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore2077.R;
import com.example.bookstore2077.data.Book;
import com.example.bookstore2077.data.Comment;
import com.example.bookstore2077.ui.containers.BookAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    View root ;
    RecyclerView recyclerView;

    List<Book> books;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);
        root = inflater.inflate(R.layout.fragment_shop, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.containers_books);

        initData(books);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // this is data fro recycler view
        books= new ArrayList<>();

        // 3. create an adapter
        BookAdapter mAdapter = new BookAdapter(getContext(), books);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //final TextView textView = root.findViewById(R.id.text_shop);
        //shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        // @Override
        //public void onChanged(@Nullable String s) {
        //textView.setText(s);
        //}
        //});
        return root;
    }

    private void initData(List<Book> books) {
        AsyncLoader asyncLoader = new AsyncLoader();
        asyncLoader.execute();
    }


    class AsyncLoader extends AsyncTask<String, String, String> {
        String answerHTTP;
        String server ="https://secure-shelf-96781.herokuapp.com/api/BooksList/3" ;

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(server);
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
            try {

                JSONArray jsonArray = new JSONArray(answerHTTP);
                String id;
                String bookName;
                //String ISBN;
                float price;
                String category;
                String author;
                String coverImageLink;
                float rating;
               // List<Comment> comments = new ArrayList<>();

                //String commentText;
                //String commentAuthor;
                //String commentDate;



                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);

                    id = row.getString("id");
                    bookName = row.getString("bookName");
                    //ISBN = row.getString("isbn");
                    price = (float) row.getDouble("price");
                    category = row.getString("category");
                    author = row.getString("author");
                    coverImageLink = row.getString("coverImageLink");
                    rating = (float) row.getDouble("rating");
                    //JSONArray commentsArray = row.getJSONArray("comments");
                    //for (int j = 0; j < commentsArray.length(); j++) {
                        //JSONObject com = commentsArray.getJSONObject(i);
                       //commentText = com.getString("text");
                        //commentAuthor = com.getString("author");
                        //commentDate = com.getString("date");
                        //comments.add(new Comment(commentText, commentAuthor, commentDate));
                   // }

                    books.add(new Book(id,bookName,author,price, coverImageLink, category,  rating));
                }

                for (Book b:books) {
                    Log.wtf("LOG", b.getAuthor());
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                BookAdapter mAdapter = new BookAdapter(getContext(), books);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
