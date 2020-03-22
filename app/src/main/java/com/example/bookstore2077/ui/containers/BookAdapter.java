package com.example.bookstore2077.ui.containers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore2077.R;
import com.example.bookstore2077.StoreHome;
import com.example.bookstore2077.data.Book;
import com.example.bookstore2077.ui.book.BookPageFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookView> {

    private List<Book> books;
    LayoutInflater inflater;
     static StoreHome activity;

     String book_id;

    public BookAdapter(Context context, List<Book> books){
        this.books = books;
        this.inflater = LayoutInflater.from(context);
        this.activity = (StoreHome) context;
    }
    @NonNull
    @Override
    public BookView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.book_container_card, parent, false);
        BookView bookView = new BookView(v);
        return bookView;
    }

    @Override
    public void onBindViewHolder(@NonNull BookView holder, int position) {
        Book book = books.get(position);
        holder.author.setText( book.getAuthor());
        Picasso.get()
                .load(book.getImage_ref())
                .fit()
                .error(R.drawable.book1)
                .into(holder.icon);
        holder.name.setText(book.getName());
        holder.id.setText(book.getId());
        //holder.price.setText(book.getPrice() + "RUR");
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookView extends RecyclerView.ViewHolder {

        CardView cardView;

        ImageButton icon;
        TextView name;
        TextView author;
        TextView price;
        TextView id;


        public BookView(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.containers_books);
            icon = (ImageButton)itemView.findViewById(R.id.bookImage);
            name = (TextView)itemView.findViewById(R.id.bookName);
            author = (TextView)itemView.findViewById(R.id.bookAuthor);
            id = (TextView)itemView.findViewById(R.id.bookID);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == icon.getId()){
                        String id_text = (String) id.getText();
                        switchToFragment(id_text);
                        Toast.makeText(activity.getApplicationContext(), "Кнопка нажата" + id, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        public void switchToFragment(String id){
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            BookPageFragment bookPageFragment = new BookPageFragment();
            Bundle args = new Bundle();
            args.putString("chosen_id", id);

            bookPageFragment.setArguments(args);
            SharedPreferences sharedPreferences = activity.getPreferences(Activity.BIND_EXTERNAL_SERVICE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Log.wtf("LOG11", id);
            editor.clear();
            editor.putString("chosen_id",id);
            editor.commit();


            fragmentManager.beginTransaction().replace(R.id.fragment_shop,
                    bookPageFragment).commit();
        }
    }
}
