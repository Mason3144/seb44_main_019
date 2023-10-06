package com.shellwe.back.domain.cart.repository;

import com.shellwe.back.domain.cart.entity.Cart;
import com.shellwe.back.entity.Member;
import com.shellwe.back.entity.Shell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteAllByShellId(long shellId);

    void deleteAllByOwnerId(long memberId);

    List<Cart> findAllByOwnerId(long memberId);

    Optional<Cart> findByOwnerAndShell(Member member, Shell shell);
}
