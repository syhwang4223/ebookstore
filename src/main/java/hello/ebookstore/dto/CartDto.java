package hello.ebookstore.dto;

import hello.ebookstore.entity.Cart;
import hello.ebookstore.entity.CartItem;
import hello.ebookstore.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CartDto {

    private Long memberId;
    private List<CartItemDto> cartItems = new ArrayList<>();
    private int totalCount;
    private int totalPrice;

    public CartDto(Cart cart) {
        memberId = cart.getMember().getId();
        cartItems = cart.getCartItems().stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
        totalCount = cart.getTotalCount();
        totalPrice = cart.getTotalPrice();
    }

    @Getter
    public static class CartItemDto {

        private Long bookId;
        private String title;
        private String author;
        private int price;

        public CartItemDto(CartItem cartItem) {
            bookId = cartItem.getBook().getId();
            title = cartItem.getBook().getTitle();
            author = cartItem.getBook().getAuthor();
            price = cartItem.getBook().getPrice();
        }
    }

}
