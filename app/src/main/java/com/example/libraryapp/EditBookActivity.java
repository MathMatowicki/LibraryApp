package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {

    public static final String EXTRA_EDIT_BOOK_TITLE = "pb.edu.pl.EDIT_BOOK_TITLE";
    public static final String EXTRA_EDIT_BOOK_AUTHOR = "pb.edu.pl.EDIT_BOOK_AUTHOR";

    private EditText editTitleEditText;
    private EditText editAuthorEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editTitleEditText = findViewById(R.id.edit_book_title);
        editAuthorEditText = findViewById(R.id.edit_book_author);

        final Button butt = findViewById(R.id.save_button);
        butt.setOnClickListener(view -> {
            Toast.makeText(this, "zapisz ksiązke", Toast.LENGTH_SHORT).show();
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editTitleEditText.getText())
                    || TextUtils.isEmpty(editAuthorEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(this, "puste ", Toast.LENGTH_SHORT).show();
            } else {
                String title = editTitleEditText.getText().toString();
                replyIntent.putExtra(EXTRA_EDIT_BOOK_TITLE, title);
                String author = editAuthorEditText.getText().toString();
                replyIntent.putExtra(EXTRA_EDIT_BOOK_AUTHOR, author);
                setResult(RESULT_OK, replyIntent);
                Toast.makeText(this, "rezultat "+ RESULT_OK, Toast.LENGTH_SHORT).show();

            }
            finish();
        });
    }
}