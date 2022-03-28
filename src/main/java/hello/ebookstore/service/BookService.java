package hello.ebookstore.service;

import hello.ebookstore.domain.Book;
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
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findBooks() {
        return bookRepository.findAll();
    }

    public Book findOne(Long id) {
        return bookRepository.findOne(id);
    }

}
