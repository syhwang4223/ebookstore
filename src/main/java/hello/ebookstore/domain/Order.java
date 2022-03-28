package hello.ebookstore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderItems = new ArrayList<>();

    private LocalDateTime orderDateTime;

    /**
     * 연관관계 메서드
     */
    public void addOrderBook(OrderLine orderLine) {
        orderItems.add(orderLine);
        orderLine.setOrder(this);
    }



    /**
     * 생성 메서드
     */
    public static Order createOrder(Cart cart, Member member) {
        Order order = new Order();
        order.setMember(member);

        order.setOrderDateTime(LocalDateTime.now());

        return order;
    }


}
