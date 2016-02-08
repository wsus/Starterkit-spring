package pl.spring.demo.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.service.impl.BookServiceImpl;
import pl.spring.demo.to.BookTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.common.Mapper;
import pl.spring.demo.to.BookEntity;
import pl.spring.demo.to.AuthorTo;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "ServiceImplMockTest-context.xml")
public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookDao bookDao;
    @Autowired
    private Mapper mapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookService.testSetMapper(mapper);
    }

    @Test
    public void testShouldSaveBook() {
        // given
        BookTo book = new BookTo(null, "title", "author");
        List<AuthorTo> authors = new ArrayList<AuthorTo>();
        authors.add(new AuthorTo(1L, "author", ""));
        Mockito.when(bookDao.save(Mockito.anyObject())).thenReturn(new BookEntity(1L, "title", authors));
        // when
        BookTo result = bookService.saveBook(book);
        // then
        Mockito.verify(bookDao).save(Mockito.anyObject());
        assertEquals(1L, result.getId().longValue());
    }
}
