package hello.ebookstore.repository;

import hello.ebookstore.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findOne(Long id) {
        return Optional.of(em.find(Member.class, id));
    }

    public Optional<Member> findByEmail(String emailAddress) {
        return em.createQuery("select m from Member m where m.emailAddress = :emailAddress", Member.class)
                .setParameter("emailAddress", emailAddress)
                .getResultList()
                .stream().findAny();
    }

    public Optional<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList()
                .stream().findAny();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


}
