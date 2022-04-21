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

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();




    //== 연관관계 메서드  ==//

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);

        return cart;
    }


    //== 비즈니스 로직 ==//



    // 카트에 담긴 책 금액 총 합
    public int getTotalPrice() {
        return cartItems.stream()
                .mapToInt(c -> c.getBook().getPrice())
                .sum();
    }

    // 카트에 담긴 아이템의 갯수
    public int getTotalCount() {
        return cartItems.size();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}