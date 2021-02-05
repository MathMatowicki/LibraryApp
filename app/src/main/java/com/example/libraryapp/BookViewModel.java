package com.example.libraryapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.libraryapp.Database.Book;
import com.example.libraryapp.Database.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private final BookRepository bookRepository;
    private final LiveData<List<Book>> listOfBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        listOfBooks = bookRepository.findAll();
    }

    public LiveData<List<Book>> findAllBooks() {
        return listOfBooks;
    }

    public void insert(Book book) {
        bookRepository.insert(book);
    }

    public void update(Book book){
        bookRepository.update(book);
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }
}
