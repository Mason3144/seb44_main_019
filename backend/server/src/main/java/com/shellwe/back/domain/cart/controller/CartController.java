package com.shellwe.back.domain.cart.controller;

import com.shellwe.back.auth.memberDetails.MemberContextInform;
import com.shellwe.back.domain.cart.service.CartService;
import com.shellwe.back.domain.member.dto.response.GetMyShellListDto;
import com.shellwe.back.exception.customexception.AccessTokenException;
import com.shellwe.back.exception.exceptioncode.AccessTokenExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/cart")
@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{shellId}")
    public void saveInMyCart(@PathVariable long shellId,
                             Authentication authentication) {
        cartService.saveShellInMyCart(shellId, getId(authentication));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{shellId}")
    public void deleteInMyCart(@PathVariable long shellId,
                               Authentication authentication) {
        cartService.deleteShellInMyCart(shellId, getId(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/myshell/{memberId}")
    public GetMyShellListDto getMyShellList(@PathVariable long memberId) {
        return cartService.getMyCartShells(memberId);
    }

    private Long getId(Authentication authentication) {
        Long id;
        if (authentication == null) {
            throw new AccessTokenException(AccessTokenExceptionCode.TOKEN_EXPIRED);
        } else {
            MemberContextInform memberInform = (MemberContextInform) authentication.getPrincipal();
            id = memberInform.getId();
        }
        return id;
    }
}
