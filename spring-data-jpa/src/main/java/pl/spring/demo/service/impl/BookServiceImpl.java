package pl.spring.demo.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.BookEntity;
import pl.spring.demo.common.Mapper;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
	private BookDao bookDao;
    
    @Autowired
    private Mapper mapper;

    @Override
    @Cacheable("booksCache")
    public List<BookTo> findAllBooks() {
        return mapper.entityToToList(bookDao.findAll());
    }

    @Override
    public List<BookTo> findBooksByTitle(String title) {
        return mapper.entityToToList(bookDao.findBookByTitle(title));
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        return mapper.entityToToList(bookDao.findBooksByAuthor(author));
    }

    public void testSetMapper(Mapper mapper) {
    	this.mapper = mapper;
    }
    
    @Override
    public BookTo saveBook(BookTo book) {
        return mapper.entityToTo(bookDao.save(mapper.toToEntity(book)));
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
