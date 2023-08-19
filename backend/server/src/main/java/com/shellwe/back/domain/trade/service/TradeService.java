package com.shellwe.back.domain.trade.service;

import com.shellwe.back.entity.Member;
import com.shellwe.back.domain.member.service.MemberService;
import com.shellwe.back.domain.trade.dto.request.UpdateTradeStatusRequestDto;
import com.shellwe.back.domain.trade.dto.response.InquiryToMainResponseDto;
import com.shellwe.back.entity.Shell;
import com.shellwe.back.domain.shell.service.ShellService;
import com.shellwe.back.domain.trade.dto.request.TradeRequestDto;
import com.shellwe.back.domain.trade.dto.response.MyTradeResponseDetailsDto;
import com.shellwe.back.domain.trade.dto.response.MyTradeResponseDto;
import com.shellwe.back.entity.Trade;
import com.shellwe.back.domain.trade.mapper.TradeMapper;
import com.shellwe.back.domain.trade.respository.TradeRepository;
import com.shellwe.back.entity.type.Status;
import com.shellwe.back.exception.customexception.TradeLogicException;
import com.shellwe.back.exception.exceptioncode.TradeExceptionCode;
import com.shellwe.back.utils.event.MemberRemoveEvent;
import com.shellwe.back.utils.event.ShellRemoveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Transactional
@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final MemberService memberService;
    private final ShellService shellService;
    private final TradeMapper tradeMapper;

    @Autowired
    public TradeService(TradeRepository tradeRepository, MemberService memberService, ShellService shellService, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.memberService = memberService;
        this.shellService = shellService;
        this.tradeMapper = tradeMapper;
    }

    public void trade(TradeRequestDto tradeRequestDto, long sellerId, long buyerId) {
        Member buyer = memberService.getMemberByOtherLayer(buyerId);
        Member seller = memberService.getMemberByOtherLayer(sellerId);

        Shell buyerShell = shellService.getShellByOtherLayer(tradeRequestDto.getBuyerShellId());
        Shell sellerShell = shellService.getShellByOtherLayer(tradeRequestDto.getSellerShellId());

        if (buyerShell.getMember().getId() != buyer.getId() || sellerShell.getMember().getId() != seller.getId()) {
            throw new TradeLogicException(TradeExceptionCode.TRADE_FAILED);
        } else {
            Optional<Trade> existTrade = tradeRepository.findBySellerAndBuyerAndBuyerShellAndSellerShell(seller, buyer, buyerShell, sellerShell);

            if (existTrade.isEmpty()) {
                tradeRepository.save(new Trade(seller, buyer, buyerShell, sellerShell));
            } else {
                throw new TradeLogicException(TradeExceptionCode.TRADE_ALREADY_EXISTS);
            }
        }
    }

    public void deleteTrade(TradeRequestDto tradeRequestDto, long buyerId, long sellerId) {
        Member buyer = memberService.getMemberByOtherLayer(buyerId);
        Member seller = memberService.getMemberByOtherLayer(sellerId);

        Shell buyerShell = shellService.getShellByOtherLayer(tradeRequestDto.getBuyerShellId());
        Shell sellerShell = shellService.getShellByOtherLayer(tradeRequestDto.getSellerShellId());

        if (buyerShell.getMember().getId() != buyer.getId() || sellerShell.getMember().getId() != seller.getId()) {
            throw new TradeLogicException(TradeExceptionCode.TRADE_DELETE_FAILED);
        } else {
            Optional<Trade> existTrade = tradeRepository.findBySellerAndBuyerAndBuyerShellAndSellerShell(seller, buyer, buyerShell, sellerShell);

            if (existTrade.isPresent()) {
                tradeRepository.delete(existTrade.get());
            } else {
                throw new TradeLogicException(TradeExceptionCode.TRADE_DOES_NOT_EXIST);
            }
        }
    }

    @Transactional(readOnly = true)
    public MyTradeResponseDto myTrade(long memberId) {
        List<Long> sellerShellsId = tradeRepository.getSellerShellsBySellerId(memberId);
        List<Shell> sellerShells = shellService.getShellsByOtherLayer(sellerShellsId);

        MyTradeResponseDto myTradeResponseDto = new MyTradeResponseDto();
        myTradeResponseDto.setShells(tradeMapper.sellerTradeListToMyTradeResponseDto(sellerShells));
        return myTradeResponseDto;
    }

    @Transactional(readOnly = true)
    public MyTradeResponseDetailsDto mySpecificShellTrade(long shellId, long memberId) {
        List<Long> buyerShellsId = tradeRepository.getSellerTradesByShellIdAndMemberId(memberId, shellId);
        List<Shell> buyerShells = shellService.getShellsByOtherLayer(buyerShellsId);

        MyTradeResponseDetailsDto myTradeResponseDetailsDto = new MyTradeResponseDetailsDto();
        myTradeResponseDetailsDto.setShells(tradeMapper.buyerTradeListToMyTradeResponseDtoDetails(buyerShells));
        return myTradeResponseDetailsDto;
    }

    public void updateTradeStatus(long shellId, UpdateTradeStatusRequestDto updateTradeStatusRequestDto, long memberId) {
        Shell shell = shellService.getShellByOtherLayer(shellId);

        if (shell.getMember().getId() == memberId) {
            shell.setStatus(updateTradeStatusRequestDto.getStatus());
            shellService.saveFromOtherLayer(shell);
            if (updateTradeStatusRequestDto.getStatus().equals(Status.INACTIVE)) {
                tradeRepository.deleteAllBySellerId(shell.getId());
            }
        } else {
            throw new TradeLogicException(TradeExceptionCode.TRADE_NOT_MY_ID);
        }
    }

    @Transactional(readOnly = true)
    public InquiryToMainResponseDto inquiryToMain(int size) {
        List<Object[]> shellsInform = tradeRepository.findShells(size);
        List<Long> shellIds = shellsInform.stream()
                .map(data -> (Long) data[0])
                .collect(Collectors.toList());
        List<Integer> shellCounts = shellsInform.stream()
                .map(data -> ((Long) data[1]).intValue())
                .collect(Collectors.toList());

        List<Shell> shells = shellService.getShellsByOtherLayer(shellIds);
        InquiryToMainResponseDto inquiryToMainResponseDto = new InquiryToMainResponseDto();
        inquiryToMainResponseDto.setShells(tradeMapper.shellsToInquiryToMainResponseDto(shells));
        IntStream.range(0, shells.size())
                .forEach(i -> inquiryToMainResponseDto.getShells().get(i).setNumberOfTrades(shellCounts.get(i)));
        return inquiryToMainResponseDto;
    }

    @EventListener
    public void handleShellRemoveEvent(ShellRemoveEvent shellRemoveEvent) {
        tradeRepository.deleteAllByBuyerShellId(shellRemoveEvent.getId());
        tradeRepository.deleteAllBySellerShellId(shellRemoveEvent.getId());
    }

    @EventListener
    public void handleMemberRemoveEvent(MemberRemoveEvent memberRemoveEvent) {
        tradeRepository.deleteAllBySellerId(memberRemoveEvent.getId());
        tradeRepository.deleteAllByBuyerId(memberRemoveEvent.getId());
    }
}

