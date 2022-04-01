package hello.ebookstore.service;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.Category;
import hello.ebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Long saveBook(Book book) {
        bookRepository.save(book);
        return book.getId();
    }

    public List<Book> findBooks() {
        return bookRepository.findAll();
    }

    public Book findOne(Long id) {
        return bookRepository.findOne(id);
    }

    public List<Book> findByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }
}
