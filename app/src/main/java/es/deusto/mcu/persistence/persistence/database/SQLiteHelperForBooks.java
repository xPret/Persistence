package es.deusto.mcu.persistence.persistence.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import es.deusto.mcu.persistence.model.Book;

public class SQLiteHelperForBooks extends SQLiteOpenHelper{
    private static final String TAG = SQLiteHelperForBooks.class.getName();

    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKS = "books";
    private static final String COL_ID = "_id";
    private static final String COL_TITLE = "title";
    private static final String COL_AUTHOR = "author";
    private static final String COL_PUBLISHER = "publisher";
    private static final String COL_PUBLISHED_DATE = "publishedDate";
    private static final String COL_AVG_RATING = "averageRating";
    private static final String COL_THUMBNAIL = "thumbnail";

    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_BOOKS + "("
                    + COL_ID + " integer primary key autoincrement, "
                    + COL_TITLE + " text not null, "
                    + COL_AUTHOR + " text not null,"
                    + COL_PUBLISHER + " text not null,"
                    + COL_PUBLISHED_DATE + " text not null,"
                    + COL_AVG_RATING + " text not null,"
                    + COL_THUMBNAIL + " text not null" +
                    ");";

    private final SQLiteDatabase mReadableDB;
    private final SQLiteDatabase mWritableDB;

    public SQLiteHelperForBooks(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "<<< SQLiteHelperForBooks()");
        mReadableDB = getReadableDatabase();
        mWritableDB = getReadableDatabase();
        Log.d(TAG, ">>> SQLiteHelperForBooks()");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, ">>> onCreate()");
        db.execSQL(DATABASE_CREATE);
        Log.d(TAG, "<<< onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, ">>> onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
        Log.d(TAG, "<<< onUpgrade()");
    }

    public long deleteBook(Book book){
        Log.d(TAG, ">>> deleteBook()");
        String whereClause = String.format("%s LIKE ? AND %s LIKE ?", COL_TITLE, COL_AUTHOR);
        String[] selectionArgs = { book.getTitle(), book.getAuthor() };
        long affectedRows = mWritableDB.delete(TABLE_BOOKS, whereClause, selectionArgs );
        Log.d(TAG, "<<< deleteBook() <- RET[affectedRows=" + affectedRows + "]");
        return affectedRows;
    }

    public long putBook(Book newBook){
        Log.d(TAG, ">>> putBook()");
        ContentValues values = fromBookToContentValues(newBook);
        long rowID = mWritableDB.insert(TABLE_BOOKS, null, values);
        Log.d(TAG, "<<< putBook() <- RET[rowID=" + rowID + "]");
        return rowID;
    }

    public List<Book> getBooks(){
        Log.d(TAG, ">>> getBooks()");
        Cursor cursor = mReadableDB.query(
                TABLE_BOOKS,
                new String[] {
                        COL_ID,
                        COL_TITLE,
                        COL_AUTHOR,
                        COL_PUBLISHER,
                        COL_PUBLISHED_DATE,
                        COL_AVG_RATING,
                        COL_THUMBNAIL},
                null,
                null,
                null,
                null,
                COL_TITLE);

        /* Alternative
        Cursor cursor = db.rawQuery("SELECT "
                + COL_ID + ","
                + COL_TITLE + ","
                + COL_AUTHOR + ","
                + COL_PUBLISHER + ","
                + COL_PUBLISHED_DATE + ","
                + COL_AVG_RATING + ","
                + COL_THUMBNAIL
                + " FROM " + TABLE_BOOKS,
                null); //*/

        List<Book> books = new ArrayList<>();
        cursor.moveToNext();
        while(!cursor.isAfterLast()){
            books.add(fromCursorToBook(cursor));
            cursor.moveToNext();
        }
        Log.d(TAG, "<<< getBooks() <- [List with (" + books.size() + ") items]");
        return books;
    }


    private ContentValues fromBookToContentValues(Book newBook) {
        ContentValues values = new ContentValues();

        values.put(COL_TITLE, newBook.getTitle());
        values.put(COL_AUTHOR, newBook.getAuthor());
        values.put(COL_PUBLISHER, newBook.getPublisher());
        values.put(COL_PUBLISHED_DATE, newBook.getPublishedDate());
        values.put(COL_AVG_RATING, newBook.getAverageRating());
        values.put(COL_THUMBNAIL, newBook.getThumbnail());

        return values;
    }

    private Book fromCursorToBook(Cursor cs) {
        Book book = new Book();

        book.setTitle(cs.getString(cs.getColumnIndex(COL_TITLE)));
        book.setAuthor(cs.getString(cs.getColumnIndex(COL_AUTHOR)));
        book.setPublisher(cs.getString(cs.getColumnIndex(COL_PUBLISHER)));
        book.setPublishedDate(cs.getString(cs.getColumnIndex(COL_PUBLISHED_DATE)));
        book.setAverageRating(cs.getInt(cs.getColumnIndex(COL_AVG_RATING)));
        book.setThumbnail(cs.getString(cs.getColumnIndex(COL_THUMBNAIL)));

        return book;
    }
}