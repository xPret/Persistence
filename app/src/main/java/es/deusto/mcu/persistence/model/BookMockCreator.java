package es.deusto.mcu.persistence.model;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class BookMockCreator {

    private static final String[] mockNames = {
            "Cletis", "Larrie", "Sydnie", "Kizzy", "Trisha",
            "Nanette", "Jaylee", "Trisha", "Matty", "Epiphany"};

    private static final String[] mockThings = {
            "Coach", "Story", "Tale", "Key", "Life",
            "Garden", "Wisdom", "Chair", "Country", "Room"};
    /**
     * Books mock creator
     * @return a new instace of a Mock Book
     */
    public static Book createBook() {
        Random r = new Random();
        Book newBook = new Book();
        newBook.setTitle(String.format("The %s of %s",
                mockThings[r.nextInt(mockThings.length)],
                mockNames[r.nextInt(mockNames.length)]));
        newBook.setAuthor(mockNames[r.nextInt(mockNames.length)]);
        newBook.setPublisher(mockNames[r.nextInt(mockNames.length)] + " Ed.");
        newBook.setPublishedDate(DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime()));
        newBook.setAverageRating(5);
        newBook.setThumbnail("https://bulma.io/images/placeholders/256x256.png");
        return newBook;
    }
}
