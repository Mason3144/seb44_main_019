package com.shellwe.back.service;

import com.shellwe.back.dto.ChatRoom;
import com.shellwe.back.entity.*;
import com.shellwe.back.entity.websocket.*;
import com.shellwe.back.exception.businessLogicException.BusinessLogicException;
import com.shellwe.back.exception.businessLogicException.ExceptionCode;
import com.shellwe.back.repository.MessageRepository;
import com.shellwe.back.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class AsyncService {
    private final MessageRepository messageRepository;
    private final TradeRepository tradeRepository;

    public AsyncService(MessageRepository messageRepository,TradeRepository tradeRepository) {
        this.messageRepository = messageRepository;
        this.tradeRepository = tradeRepository;
    }

    @Async("threadPoolTaskExecutor")
    public void saveMessage(Map<Long, ChatRoom> chatRooms, TextMessage textMessage, long roomId, long memberId){
        long joinedMemberNumber = chatRooms.get(roomId).getSessions().size();
        Message message = new Message();
                message.setRoom(new Room(roomId));
                message.setMember(new Member(memberId));
                message.setPayload(textMessage.getPayload());
        if(joinedMemberNumber<2) message.setUnread(true);

        messageRepository.save(message);
    }

    @Async("threadPoolTaskExecutor")
    public void deleteTrade(long myShellId, long traderShellId){
        Trade trade = findExistsTrade(myShellId, traderShellId);
        tradeRepository.delete(trade);
    }

    private Trade findExistsTrade(long myShellId, long traderShellId){
        Optional<Trade> optionalTrade = tradeRepository.findByBuyerShellAndSellerShell(new Shell(traderShellId), new Shell(myShellId));
        return optionalTrade.orElseThrow(()->new BusinessLogicException(ExceptionCode.TRADE_NOT_FOUND));
    }
}
