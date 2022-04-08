package hello.ebookstore.service;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.CartItem;
import hello.ebookstore.domain.Category;
import hello.ebookstore.domain.Member;
import hello.ebookstore.dto.BookDto;
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
    private final MemberRepository memberRepository;

    @Transactional
    public Long createBook(Book book) {
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

    public void addToCart(Long bookId) {
        Book book = bookRepository.findOne(bookId);
        CartItem cartItem = new CartItem(book);

        // 이걸 모든 요청에 대해서 알아서 해주는게 분명 방법이 있을텐데
        // 이거 방법 좀 고쳐보자...
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        if (currentMemberId == null) {
            throw new NoLoginMemberException("로그인 유저 정보가 없습니다");
        }

        // 분명 유효한 토큰인지에 대한 검사를 필터에서 할텐데 뭘까....
        // 시큐리티 좀 더 공부해보자..
        // 아 근데 지금 경우는 토큰은 유효시간이 지나지 않아 유효한데
        // 서버가 내려갔다 올라가며 디비가 날아가서 그런 것 같다
        log.debug(String.valueOf(currentMemberId));
        Member member = memberRepository.findOne(currentMemberId).get();

        member.addCartItem(cartItem);
        memberRepository.save(member);

    }

    public List<CartItem> getCart() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findOne(memberId).orElseThrow(() -> new NoLoginMemberException("로그인 유저 정보가 없습니다."));
        return member.getCartItems();
    }
}
