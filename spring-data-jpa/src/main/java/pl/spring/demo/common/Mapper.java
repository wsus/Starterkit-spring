package pl.spring.demo.common;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.BookEntity;
import pl.spring.demo.to.AuthorTo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
	public BookEntity toToEntity(BookTo bookIn) {
		List<AuthorTo> authors = new ArrayList<AuthorTo>();
		if (bookIn.getAuthors() != null) {
			String[] authorsOld = bookIn.getAuthors().split(",");
			Long id = 1L;
			for (String entry : authorsOld) {
				String[] author = entry.split(" ");
				AuthorTo authorNew = new AuthorTo(id,author[0],null);
				if (author.length > 1) {
					authorNew.setLastName(author[1]);
				}
				authors.add(authorNew);
				id++;
			}
		}
		return new BookEntity(bookIn.getId(), bookIn.getTitle(), authors);
	}
	
	public BookTo entityToTo(BookEntity bookIn) {
		StringBuilder authors = null;
		if (bookIn.getAuthors() != null) {
			authors = new StringBuilder();
			List<AuthorTo> authorsOld = bookIn.getAuthors();
			for (AuthorTo author : authorsOld) {
				authors.append(author.getFirstName() + " " + author.getLastName());
				if (authorsOld.indexOf(author) != authorsOld.size()-1) {
					authors.append(",");
				}
			}
		}
		return new BookTo(bookIn.getId(), bookIn.getTitle(), authors.toString().trim());
	}
	
	public List<BookEntity> toToEntityList(List<BookTo> listIn) {
		List<BookEntity> listOut = new ArrayList<BookEntity>();
		for (BookTo book : listIn)
		{
			listOut.add(toToEntity(book));
		}
		return listOut;
	}
	
	public List<BookTo> entityToToList(List<BookEntity> listIn) {
		List<BookTo> listOut = new ArrayList<BookTo>();
		for (BookEntity book : listIn)
		{
			listOut.add(entityToTo(book));
		}
		return listOut;
	}

}
