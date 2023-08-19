package com.shellwe.back.domain.trade.controller;

import com.shellwe.back.auth.memberDetails.MemberContextInform;
import com.shellwe.back.domain.trade.dto.request.UpdateTradeStatusRequestDto;
import com.shellwe.back.domain.trade.dto.response.InquiryToMainResponseDto;
import com.shellwe.back.domain.trade.dto.request.TradeRequestDto;
import com.shellwe.back.domain.trade.dto.response.MyTradeResponseDetailsDto;
import com.shellwe.back.domain.trade.dto.response.MyTradeResponseDto;
import com.shellwe.back.domain.trade.service.TradeService;
import com.shellwe.back.exception.customexception.AccessTokenException;
import com.shellwe.back.exception.exceptioncode.AccessTokenExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("trades")
@RestController
public class TradeController {

    public final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public MyTradeResponseDto getMyTrades(Authentication authentication) {
        return tradeService.myTrade(getId(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{sellerId}")
    public void tradesToSeller(@RequestBody TradeRequestDto tradeRequestDto, @PathVariable long sellerId, Authentication authentication) {
        tradeService.trade(tradeRequestDto, sellerId, getId(authentication));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{buyerId}")
    public void deleteTrade(@RequestBody TradeRequestDto tradeRequestDto, @PathVariable long buyerId, Authentication authentication) {
        tradeService.deleteTrade(tradeRequestDto, buyerId, getId(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shellId}")
    public MyTradeResponseDetailsDto getMySpecificShellTrades(@PathVariable long shellId, Authentication authentication) {
        return tradeService.mySpecificShellTrade(shellId, getId(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{shellId}")
    public void updateShellTradeStatus(@PathVariable long shellId,
                                                               @RequestBody UpdateTradeStatusRequestDto updateTradeStatusRequestDto,
                                                               Authentication authentication) {
        tradeService.updateTradeStatus(shellId, updateTradeStatusRequestDto, getId(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/main")
    public InquiryToMainResponseDto inquiryShellsToMain(@RequestParam("size") int size) {
        return tradeService.inquiryToMain(size);
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
