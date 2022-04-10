package hello.ebookstore.service;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.CartItem;
import hello.ebookstore.domain.Category;
import hello.ebookstore.domain.Member;
import hello.ebookstore.dto.BookResponseDto;
import hello.ebookstore.exception.BadRequestException;
import hello.ebookstore.exception.NoLoginMemberException;
import hello.ebookstore.repository.BookRepository;
import hello.ebookstore.repository.MemberRepository;
import hello.ebookstore.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Long createBook(Book book) {
        bookRepository.save(book);
        return book.getId();
    }

    public List<Book> findBooks() {
        return bookRepository.findAll();
    }

    public Book findOne(Long id) {
        return bookRepository.findOne(id).orElseThrow(()->new BadRequestException("존재하지 않는 책입니다."));
    }

    public List<Book> findByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }


}
