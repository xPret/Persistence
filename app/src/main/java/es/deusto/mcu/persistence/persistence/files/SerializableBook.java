package es.deusto.mcu.persistence.persistence.files;

import java.io.Serializable;

import es.deusto.mcu.persistence.model.Book;

class SerializableBook implements Serializable {

    private String title;
    private String author;
    private String publisher;
    private String publishedDate;
    private int averageRating;
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    SerializableBook(Book book) {
        this.setTitle(book.getTitle());
        this.setAuthor(book.getAuthor());
        this.setPublisher(book.getPublisher());
        this.setPublishedDate(book.getPublishedDate());
        this.setAverageRating(book.getAverageRating());
        this.setThumbnail(book.getThumbnail());
    }

    Book toBook() {
        Book b = new Book();
        b.setTitle(this.getTitle());
        b.setAuthor(this.getAuthor());
        b.setPublisher(this.getPublisher());
        b.setPublishedDate(this.getPublishedDate());
        b.setAverageRating(this.getAverageRating());
        b.setThumbnail(this.getThumbnail());
        return b;
    }
}
