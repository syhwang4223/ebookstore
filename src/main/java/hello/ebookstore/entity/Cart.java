package hello.ebookstore.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cart")
    private Member member;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();




    //== 연관관계 메서드  ==//

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }


    //== 비즈니스 로직 ==//



    // 카트에 담긴 책 금액 총 합
    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (CartItem cartItem : cartItems) {
//            totalPrice += cartItem.getBook().getPrice();
//        }
//        return totalPrice;
//
        return cartItems.stream()
                .mapToInt(c -> c.getBook().getPrice())
                .sum();
    }


}
