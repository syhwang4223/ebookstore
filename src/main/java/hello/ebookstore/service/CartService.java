package hello.ebookstore.service;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.CartItem;
import hello.ebookstore.domain.Member;
import hello.ebookstore.exception.BadRequestException;
import hello.ebookstore.exception.NoLoginMemberException;
import hello.ebookstore.repository.BookRepository;
import hello.ebookstore.repository.MemberRepository;
import hello.ebookstore.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Transactional
    public void addToCart(Long bookId) {
        Book book = bookRepository.findOne(bookId);
        CartItem cartItem = new CartItem(book);

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findOne(currentMemberId).orElseThrow(() -> new NoLoginMemberException("로그인이 필요합니다"));

        for (CartItem item : member.getCartItems()) {
            if (item.getBook().getId().equals(bookId)) {
                throw new BadRequestException("이미 카트에 존재하는 책입니다.");
            }
        }

        member.addCartItem(cartItem);
    }

    @Transactional
    public void outFromCart(Long bookId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findOne(memberId).orElseThrow(() -> new NoLoginMemberException("로그인 유저 정보가 없습니다."));
        List<CartItem> cart = member.getCartItems();

        for (CartItem item : cart) {
            if (item.getBook().getId().equals(bookId)) {
                cart.remove(item);
                em.remove(item);
                return;
            }
        }
        throw new BadRequestException("카트에 해당 책이 존재하지 않습니다. bookId : " + bookId);

    }

    public List<CartItem> getCartItems() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findOne(memberId).orElseThrow(() -> new NoLoginMemberException("로그인 유저 정보가 없습니다."));
        return member.getCartItems();
    }

}
