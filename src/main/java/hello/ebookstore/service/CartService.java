package hello.ebookstore.service;

import hello.ebookstore.domain.Cart;
import hello.ebookstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

}
