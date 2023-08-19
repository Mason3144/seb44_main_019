package com.shellwe.back.domain.cart.service;

import com.shellwe.back.domain.cart.entity.Cart;
import com.shellwe.back.domain.cart.mapper.CartMapper;
import com.shellwe.back.domain.cart.repository.CartRepository;
import com.shellwe.back.domain.member.dto.response.GetMyShellListDto;
import com.shellwe.back.entity.Member;
import com.shellwe.back.domain.member.service.MemberService;
import com.shellwe.back.entity.Shell;
import com.shellwe.back.domain.shell.service.ShellService;
import com.shellwe.back.exception.customexception.CartLogicException;
import com.shellwe.back.exception.exceptioncode.CartExceptionCode;
import com.shellwe.back.utils.event.MemberRemoveEvent;
import com.shellwe.back.utils.event.ShellRemoveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ShellService shellService;
    private final MemberService memberService;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, ShellService shellService, MemberService memberService, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.shellService = shellService;
        this.memberService = memberService;
        this.cartMapper = cartMapper;
    }

    public void saveShellInMyCart(long shellId, long memberId) {
        Member member = memberService.getMemberByOtherLayer(memberId);
        Shell shell = shellService.getShellByOtherLayer(shellId);

        Optional<Cart> existCart = cartRepository.findByOwnerAndShell(member, shell);

        if (existCart.isEmpty()) {
            cartRepository.save(new Cart(member, shell));
        } else {
            throw new CartLogicException(CartExceptionCode.CART_ALREADY_EXISTS);
        }
    }

    public void deleteShellInMyCart(long shellId, long memberId) {
        cartRepository.deleteAllByShellId(shellId);
    }

    @Transactional(readOnly = true)
    public GetMyShellListDto getMyCartShells(long memberId) {
        GetMyShellListDto getMyShellListDto = new GetMyShellListDto();

        List<Cart> carts = cartRepository.findAllByOwnerId(memberId);
        List<Shell> cartShellList = new ArrayList<>();
        for (Cart cart : carts) {
            cartShellList.add(cart.getShell());
        }
        getMyShellListDto.setShells(cartMapper.shellListToGetMyShellListDto(cartShellList));

        return getMyShellListDto;
    }

    @EventListener
    public void handleShellRemoveEvent(ShellRemoveEvent shellRemoveEvent) {
        cartRepository.deleteAllByShellId(shellRemoveEvent.getId());
    }

    @EventListener
    public void handleMemberRemoveEvent(MemberRemoveEvent memberRemoveEvent) {
        cartRepository.deleteAllByOwnerId(memberRemoveEvent.getId());
        for(Long shellId : memberRemoveEvent.getShellIds()) {
            cartRepository.deleteAllByShellId(shellId);
        }
    }
}
