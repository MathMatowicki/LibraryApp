package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Database.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_BOOK_ACTIVITY_REQUEST_CODE = 1;
    public static final int DELETE_BOOK_ACTIVITY_REQUEST_CODE = 2;
    public static final int EDIT_BOOK_ACTIVITY_REQUEST_CODE = 3;


    private BookViewModel bookViewModel;

    private class BookHolder extends RecyclerView.ViewHolder {
        private TextView bookTitleTextView;
        private TextView bookAuthorTextView;


        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.book_list_item, parent, false));

            bookTitleTextView = (TextView) itemView.findViewById(R.id.txt_book_title);
            bookAuthorTextView = (TextView) itemView.findViewById(R.id.txt_book_author);
        }

        public void bind(Book book) {
            bookTitleTextView.setText(book.getTitle());
            bookAuthorTextView.setText(book.getAuthor());
        }

    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> bookList;

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            if (bookList != null) {
                Book book = bookList.get(position);
                holder.bind(book);
            } else {
                Toast.makeText(MainActivity.this, "No books", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getItemCount() {
            if (bookList != null) {
                return bookList.size();
            } else {
                return 0;
            }
        }

        public void setBookList(List<Book> bookList) {
            this.bookList = bookList;
            notifyDataSetChanged();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BookAdapter adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.findAllBooks().observe(this, adapter::setBookList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_button);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Book book = new Book(data.getStringExtra(EditBookActivity.EXTRA_EDIT_BOOK_TITLE),
                    data.getStringExtra(EditBookActivity.EXTRA_EDIT_BOOK_AUTHOR));
            bookViewModel.insert(book);
            Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.book_added),
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(findViewById(R.id.coordinator_layout),
                    getString(R.string.empty_not_saved), Snackbar.LENGTH_SHORT).show();
        }

        if (requestCode == DELETE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {



            Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Couldn't delete", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == EDIT_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {


            Toast.makeText(this, "Edit success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Couldn't delete", Toast.LENGTH_SHORT).show();
        }
    }
}