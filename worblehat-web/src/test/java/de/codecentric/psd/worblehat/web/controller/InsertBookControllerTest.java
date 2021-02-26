package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

public class InsertBookControllerTest {

    private InsertBookController insertBookController;

    private BookService bookService;

    private BookDataFormData bookDataFormData;

    private BindingResult bindingResult;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

    @BeforeEach
    public void setUp() {
        bookService = mock(BookService.class);
        insertBookController = new InsertBookController(bookService);
        bookDataFormData = new BookDataFormData();
        bindingResult = new MapBindingResult(new HashMap<>(), "");
    }

    @Test
    public void shouldSetupForm() {
        ModelMap modelMap = new ModelMap();

        insertBookController.setupForm(modelMap);

        assertThat(modelMap.get("bookDataFormData"), is(not(nullValue())));
    }

    @Test
    public void shouldRejectErrors() {
        bindingResult.addError(new ObjectError("", ""));

        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

        assertThat(navigateTo, is("insertBooks"));
    }

    @Test
    public void shouldCreateNewCopyOfExistingBook() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(true);
        when(bookService.createBook(any(), any(), any(), any(), anyInt())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    @Test
    public void shouldCreateBookAndNavigateToBookList() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(false);
        when(bookService.createBook(any(), any(), any(), any(), anyInt())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    @Test
    public void shouldGetTrimmedISBN() {
        String wrongIsbn = " 1111111111 ";
        String expectedIsbn = "1111111111";
        assertEquals(bookDataFormData.trimISBN(wrongIsbn), expectedIsbn);
    }

    private void verifyBookIsCreated() {
        verify(bookService).createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(), TEST_BOOK.getEdition(),
                TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication());
    }

    private void setupFormData() {
        bookDataFormData.setTitle(TEST_BOOK.getTitle());
        bookDataFormData.setAuthor(TEST_BOOK.getAuthor());
        bookDataFormData.setEdition(TEST_BOOK.getEdition());
        bookDataFormData.setIsbn(TEST_BOOK.getIsbn());
        bookDataFormData.setYearOfPublication(String.valueOf(TEST_BOOK.getYearOfPublication()));
    }
}
