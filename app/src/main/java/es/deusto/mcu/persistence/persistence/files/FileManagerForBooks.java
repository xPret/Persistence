package es.deusto.mcu.persistence.persistence.files;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import es.deusto.mcu.persistence.model.Book;

public class FileManagerForBooks {

    private static final String FILENAME = "BooksFile";
    private final Context mContext;

    public FileManagerForBooks(Context context){
        mContext = context;
    }

    private File getBooksFile(boolean createFile) throws IOException {
        File rootDir = mContext.getCacheDir(); // mContext.getFilesDir();
        File booksFile = new File(rootDir, FILENAME);
        if (createFile) booksFile.createNewFile();
        return booksFile.exists() ? booksFile : null;
    }

    public List<Book> loadBooks(){
        List<Book> books = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(getBooksFile(false));
            // Alternative: FileInputStream fis = mContext.openFileInput(FILENAME);

            ObjectInputStream ois = new ObjectInputStream(fis);
            List<SerializableBook> readBooks = (List<SerializableBook>) ois.readObject();
            for (SerializableBook sb : readBooks) {
                books.add(sb.toBook());
            }
            ois.close();
            fis.close();
        } catch (NullPointerException | IOException | ClassNotFoundException e) {
            books = Collections.emptyList();
        }
        return books;
    }

    public void saveBooks(List<Book> books) {
        List<SerializableBook> serializableBooks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            serializableBooks = books.stream().map(SerializableBook::new).collect(Collectors.toList());
        } else {
            serializableBooks = new ArrayList<>();
            for (Book b : books) serializableBooks.add(new SerializableBook(b));
        }

        try {
            try (FileOutputStream fos = new FileOutputStream(getBooksFile(true))) {
                try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(serializableBooks);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}