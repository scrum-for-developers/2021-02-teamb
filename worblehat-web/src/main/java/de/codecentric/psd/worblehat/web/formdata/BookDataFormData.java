package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.web.validation.ISBN;
import de.codecentric.psd.worblehat.web.validation.Numeric;

import java.time.Year;
import java.util.Calendar;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;

/** This class represent the form data of the add book form. */
public class BookDataFormData {

    @NotEmpty(message = "{empty.bookDataFormData.title}")
    private String title;

    @NotEmpty(message = "{empty.bookDataFormData.edition}")
    @Numeric(message = "{notvalid.bookDataFormData.edition}")
    private String edition;

    private static final int MIN_YEAR = 1000;
    private static final int MAX_YEAR = 2021;
    @NotEmpty(message = "{empty.bookDataFormData.yearOfPublication}")
    @Min(value = MIN_YEAR, message = "{invalid.notInFuture.bookDataFormData.yearOfPublicationNotInPast}")
    @Max(value = MAX_YEAR, message = "{invalid.notInFuture.bookDataFormData.yearOfPublicationNotInFuture}")
    private String yearOfPublication;

    @NotEmpty(message = "{empty.bookDataFormData.isbn}")
    @ISBN(message = "{notvalid.bookDataFormData.isbn}")
    private String isbn;

    @NotEmpty(message = "{empty.bookDataFormData.author}")
    private String author;

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = trimISBN(isbn);
    }

    public String trimISBN(String _isbn) {
        return _isbn.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "BookDataFormData [title=" + title + ", edition=" + edition + ", yearOfPublication=" + yearOfPublication
                + ", isbn=" + isbn + ", author=" + author + "]";
    }
}
