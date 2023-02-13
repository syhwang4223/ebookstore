package hello.ebookstore.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;


    public Category(String name) {
        this.name = name;
    }

    protected Category(){}


}
