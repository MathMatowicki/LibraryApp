package com.example.libraryapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, entities = {Book.class}, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDAO bookDAO();

    private static volatile BookDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterEXECUTOR_SERVICE = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BookDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "book_db_1.0.0")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriterEXECUTOR_SERVICE.execute(() -> {
                BookDAO dao = INSTANCE.bookDAO();
                dao.deleteAll();

                Book book = new Book("The Lord of the Rings", "J. R. R. Tolkien");
                dao.insert(book);
                book = new Book("Harry Potter", "J. K. Rowling");
                dao.insert(book);
            });
        }
    };

}
