package hello.ebookstore.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {

//    @CreatedBy
//    @Column(updatable = false)
//    private String creater;

    @CreatedDate
    @Column(updatable = true)
    private LocalDateTime createdDateTime;

//    @LastModifiedBy
//    private String modifier;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;
}
