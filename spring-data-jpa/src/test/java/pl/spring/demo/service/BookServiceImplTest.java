package pl.spring.demo.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() {
        cacheManager.getCache("booksCache").clear();
    }

    @Test
    public void testShouldFindAllBooks() {
        // when
        List<BookTo> allBooks = bookService.findAllBooks();
        // then
        assertNotNull(allBooks);
        assertFalse(allBooks.isEmpty());
        assertEquals(6, allBooks.size());
    }

    @Test
    public void testShouldFindAllBooksByTitle() {
        // given
        final String title = "oPiUm w";
        // when
        List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
        // then
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
    }
    
    @Test
    public void testShouldFindAllBooksByAuthor() {
        // given
        final String author1 = "aLekSAnd";
        final String author2 = "aLekSAnder fRE";
        final String author3 = "fREd";
        // when
        List<BookTo> books = bookService.findBooksByAuthor(author1);
        books.addAll(bookService.findBooksByAuthor(author2));
        books.addAll(bookService.findBooksByAuthor(author3));
        // then
        assertNotNull(books);
        assertEquals(3, books.size());
    }

    @Test(expected = BookNotNullIdException.class)
    public void testShouldThrowBookNotNullIdException() {
        // given
        final BookTo bookToSave = new BookTo();
        bookToSave.setId(22L);
        // when
        bookService.saveBook(bookToSave);
        // then
        fail("test should throw BookNotNullIdException");
    }
}
