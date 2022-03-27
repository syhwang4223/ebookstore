package hello.ebookstore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Library {

    @Id
    @GeneratedValue
    @Column(name = "library_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

}
