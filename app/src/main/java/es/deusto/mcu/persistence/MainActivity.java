package es.deusto.mcu.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.deusto.mcu.persistence.model.Book;
import es.deusto.mcu.persistence.model.BookMockCreator;
import es.deusto.mcu.persistence.persistence.database.SQLiteHelperForBooks;
import es.deusto.mcu.persistence.persistence.files.FileManagerForBooks;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    SQLiteHelperForBooks mSQLiteHelperForBooks;
    FileManagerForBooks mFileManagerForBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bSaveToFile).setOnClickListener(v -> saveToFile());
        findViewById(R.id.bReadFromFile).setOnClickListener(v -> readFromFile());
        findViewById(R.id.bSaveToDB).setOnClickListener(v -> saveToDB());
        findViewById(R.id.bReadDB).setOnClickListener(v -> readFromDB());
        findViewById(R.id.bGoBookActivity).setOnClickListener(v -> goBookActivity());
        mFileManagerForBooks = new FileManagerForBooks(this);
        mSQLiteHelperForBooks = new SQLiteHelperForBooks(this);
    }

    @Override
    protected void onDestroy() {
        mSQLiteHelperForBooks.close();
        super.onDestroy();
    }

    private void saveToFile() {
        Log.d(TAG, ">>> saveToFile()");
        List<Book> books = readFromFile();
        books = (books.isEmpty() ? new ArrayList<>() : books);
        books.add(BookMockCreator.createBook());
        mFileManagerForBooks.saveBooks(books);
        loadBooksInView(books);
        Log.d(TAG, "<<< saveToFile()");
    }

    private List<Book> readFromFile() {
        Log.d(TAG, ">>> readFromFile()");
        List<Book> books = mFileManagerForBooks.loadBooks();
        loadBooksInView(books);
        Log.d(TAG, "<<< readFromFile()");
        return books;
    }

    private void saveToDB() {
        Log.d(TAG, ">>> saveToDB()");
        long newBookId = mSQLiteHelperForBooks.putBook(BookMockCreator.createBook());
        Log.d(TAG, "    saveToDB(): Created new Book, id = " + newBookId);
        Log.d(TAG, "<<< saveToDB()");
    }

    private void readFromDB() {
        Log.d(TAG, ">>> readFromDB()");
        loadBooksInView(mSQLiteHelperForBooks.getBooks());
        Log.d(TAG, "<<< readFromDB()");
    }

    private void loadBooksInView(List<Book> books) {
        StringBuilder text = new StringBuilder();
        TextView tv = findViewById(R.id.tvResults);
        for (Book b : books) {
            text.append(String.format("Book: '%s' (%s)\n", b.getTitle(), b.getAuthor()));
        }
        tv.setText(text.toString());
    }

    private void goBookActivity() {
        Book book = BookMockCreator.createBook();
        startActivity(BookActivity.getIntentForStartActivity(this, book));
    }
}