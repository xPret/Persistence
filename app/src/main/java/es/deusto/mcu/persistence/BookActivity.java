package es.deusto.mcu.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.Calendar;

import es.deusto.mcu.persistence.model.Book;
import es.deusto.mcu.persistence.model.ParcelableBook;
import es.deusto.mcu.persistence.persistence.settings.SharedPreferencesForBooks;

public class BookActivity extends AppCompatActivity {

    private final static String BOOK_EXTRA = BookActivity.class.getName() + ".BOOK";

    private TextView mResultBookTitleText;
    private TextView mResultAuthorText;
    private TextView mResultBookPublisherNameText;
    private TextView mResultBookPublishedDateText;
    private TextView mResultBookRatingText;
    private ImageView mResultBookImage;
    private TextView mLastAccessedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mResultBookTitleText = findViewById(R.id.tvResultBookTitle);
        mResultAuthorText = findViewById(R.id.tvResultBookAuthors);
        mResultBookPublisherNameText = findViewById(R.id.tvResultBookPublisherName);
        mResultBookPublishedDateText = findViewById(R.id.tvResultBookPublishedDate);
        mResultBookRatingText = findViewById(R.id.tvResultBookRating);
        mResultBookImage = findViewById(R.id.ivBookImage);

        Book mBook = getBookFromIntent();
        loadResultsFromBook(mBook);

        mLastAccessedText = findViewById(R.id.tvLastAccessed);
        loadAndSaveLastAccess();
    }

    private void loadAndSaveLastAccess() {
        Calendar calendar = Calendar.getInstance();
        SharedPreferencesForBooks mSharedPreferencesForBooks = new SharedPreferencesForBooks(this);
        long last = mSharedPreferencesForBooks.getLastAccessDate();
        mSharedPreferencesForBooks.saveLastAccessDate(calendar.getTimeInMillis());
        if (last < 0) {
            mLastAccessedText.setText(String.format("Last access: %s", "Never"));
        } else {
            calendar.setTimeInMillis(last);
            mLastAccessedText.setText(String.format("Last access: %s",
                    DateFormat.getDateTimeInstance().format(calendar.getTime())));
        }
    }

    private void loadResultsFromBook(Book book) {
        if (book != null) {
            mResultBookTitleText.setText(book.getTitle());
            mResultAuthorText.setText(book.getAuthor());
            mResultBookPublisherNameText.setText(book.getPublisher());
            mResultBookPublishedDateText.setText(book.getPublishedDate());
            mResultBookRatingText.setText(String.valueOf(book.getAverageRating()));
            Glide.with(this).load(book.getThumbnail()).into(mResultBookImage);
        }
    }

    public static Intent getIntentForStartActivity(Context context, Book book) {
        Intent i = new Intent(context, BookActivity.class);
        ParcelableBook pBook = new ParcelableBook(book);
        i.putExtra(BOOK_EXTRA, pBook);
        return i;
    }

    private Book getBookFromIntent() {
        return getIntent().getExtras().<ParcelableBook>getParcelable(BOOK_EXTRA);
    }
}