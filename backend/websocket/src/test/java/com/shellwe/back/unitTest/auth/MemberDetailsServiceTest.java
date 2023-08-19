package com.shellwe.back.unitTest.auth;

import com.shellwe.back.auth.memberDetails.MemberDetails;
import com.shellwe.back.auth.memberDetails.MemberDetailsService;
import com.shellwe.back.entity.Member;
import com.shellwe.back.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberDetailsServiceTest {
    @InjectMocks
    private MemberDetailsService memberDetailsService;
    @Mock
    private MemberRepository memberRepository;

    @Test
    void loadUserByUsernameSuccess(){
        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(new Member()));
        assertThat(memberDetailsService.loadUserByUsername(Mockito.anyString())).isInstanceOf(MemberDetails.class);
    }
    @Test
    void loadUserByUsernameFail(){
        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->memberDetailsService.loadUserByUsername(Mockito.anyString())).isInstanceOf(IllegalStateException.class);
    }
}
