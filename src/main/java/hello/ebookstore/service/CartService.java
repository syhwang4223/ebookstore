package hello.ebookstore.service;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Cart;
import hello.ebookstore.entity.CartItem;
import hello.ebookstore.entity.Member;
import hello.ebookstore.exception.BadRequestException;
import hello.ebookstore.repository.BookRepository;
import hello.ebookstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final EntityManager em;

    public Cart getCart(Member member) {
        return cartRepository.findByMember(member);
    }

    @Transactional
    public CartItem addToCart(Long bookId, Member loginMember) {
        Book book = bookRepository.findOne(bookId).orElseThrow(() -> new BadRequestException("존재하지 않는 책입니다. bookId = " + bookId));
        Cart cart = cartRepository.findByMember(loginMember);
        if (isExistInCart(bookId, cart)) {
            throw new BadRequestException("이미 카트에 존재하는 책입니다.");
        }

        CartItem cartItem = new CartItem(book);
        cart.addCartItem(cartItem);
        return cartItem;
    }

    public Boolean isExistInCart(Long bookId, Cart cart) {
        Book book = bookRepository.findOne(bookId).orElseThrow(() -> new BadRequestException("존재하지 않는 책입니다. bookId = " + bookId));

        for (CartItem item : cart.getCartItems()) {
            if (item.getBook().getId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void outFromCart(Long bookId, Member loginMember) {
        Cart cart = cartRepository.findByMember(loginMember);

        for (CartItem item : cart.getCartItems()) {
            if (item.getBook().getId().equals(bookId)) {
                cart.getCartItems().remove(item);
                em.remove(item);
                return;
            }
        }
        throw new BadRequestException("카트에 해당 책이 존재하지 않습니다. bookId : " + bookId);
    }


}
