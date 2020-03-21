package com.example.bookstore2077.ui.containers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore2077.R;
import com.example.bookstore2077.StoreHome;
import com.example.bookstore2077.data.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookView> {

    private List<Book> books;
    LayoutInflater inflater;
    StoreHome activity;

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
        holder.icon.setImageResource(R.drawable.book1);
        holder.name.setText(book.getName());
        holder.price.setText(book.getPrice() + "RUR");
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookView extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView icon;
        TextView name;
        TextView author;
        TextView price;


        public BookView(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.containers_books);
            icon = (ImageView)itemView.findViewById(R.id.bookImage);
            name = (TextView)itemView.findViewById(R.id.bookName);
            author = (TextView)itemView.findViewById(R.id.bookAuthor);
            price = (TextView) itemView.findViewById(R.id.bookPrice);


        }
    }
}
