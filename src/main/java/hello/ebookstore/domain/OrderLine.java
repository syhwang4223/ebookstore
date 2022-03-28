package hello.ebookstore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderLine {

    @Id @GeneratedValue
    @Column(name = "order_line_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    public static OrderLine createOrderLine(Book book, int orderPrice) {
        OrderLine orderLine = new OrderLine();
        orderLine.setBook(book);
        orderLine.setOrderPrice(orderPrice);

        return orderLine;
    }


}
