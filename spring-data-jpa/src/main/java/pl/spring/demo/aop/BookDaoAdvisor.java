package pl.spring.demo.aop;


import org.springframework.aop.MethodBeforeAdvice;
import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.BookEntity;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.to.IdAware;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class BookDaoAdvisor implements MethodBeforeAdvice {

	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private Sequence sequence;
	
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {

        if (hasAnnotation(method, o, NullableId.class)) {
        	checkNotNullId(objects[0]);
            setBookId(objects[0]);
        }
    }
    
    private void setBookId(Object book) {
        ((BookEntity) book).setId(sequence.nextValue(bookDao.findAll()));
    }

    private void checkNotNullId(Object o) {
        if (o instanceof IdAware && ((IdAware) o).getId() != null) {
        	throw new BookNotNullIdException();
        }
    }

    private boolean hasAnnotation (Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
        boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;

        if (!hasAnnotation && o != null) {
            hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(annotationClazz) != null;
        }
        return hasAnnotation;
    }
}
