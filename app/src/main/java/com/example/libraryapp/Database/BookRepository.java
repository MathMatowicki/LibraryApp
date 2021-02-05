package com.example.libraryapp.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private BookDAO bookDAO;
    private LiveData<List<Book>> listOfBooks;


    public BookRepository(Application application) {
        BookDatabase database = BookDatabase.getDatabase(application);
        bookDAO = database.bookDAO();
        listOfBooks = bookDAO.findAll();
    }

    public  void insert(Book book) {
        BookDatabase.databaseWriterEXECUTOR_SERVICE.execute(() -> {
            bookDAO.insert(book);
        });
    }

    public void update(Book book) {
        BookDatabase.databaseWriterEXECUTOR_SERVICE.execute(() -> {
            bookDAO.update(book);
        });
    }

    public void delete(Book book) {
        BookDatabase.databaseWriterEXECUTOR_SERVICE.execute(() -> {
            bookDAO.delete(book);
        });
    }

    public void deleteAll() {
        BookDatabase.databaseWriterEXECUTOR_SERVICE.execute(() -> {
            bookDAO.deleteAll();
        });
    }

    public LiveData<List<Book>> findAll() {
        return listOfBooks;
    }

    public List<Book> findBookWithTitle(String title) {
        return bookDAO.findBookWithTitle(title);
    }
}
