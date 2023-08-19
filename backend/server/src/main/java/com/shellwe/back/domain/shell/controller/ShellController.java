package com.shellwe.back.domain.shell.controller;

import com.shellwe.back.auth.memberDetails.MemberContextInform;
import com.shellwe.back.domain.shell.dto.request.RegisterRequestDto;
import com.shellwe.back.domain.shell.dto.request.UpdateRequestDto;
import com.shellwe.back.domain.shell.dto.response.*;
import com.shellwe.back.domain.shell.service.ShellService;
import com.shellwe.back.entity.type.ShellType;
import com.shellwe.back.entity.ShellCategory;
import com.shellwe.back.exception.customexception.AccessTokenException;
import com.shellwe.back.exception.exceptioncode.AccessTokenExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/shells")
@RestController
public class ShellController {

    private final ShellService shellService;

    @Autowired
    public ShellController(ShellService shellService) {
        this.shellService = shellService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShellIdDto registerShell(@Valid  @RequestPart("register") RegisterRequestDto registerRequestDto,
                                             @RequestPart("pictures") List<MultipartFile> pictures,
                                             Authentication authentication) {
        return shellService.register(registerRequestDto, getId(authentication), pictures);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shellId}")
    public FindDetailsResponseDto findShellDetails(@PathVariable long shellId, Authentication authentication) {
        return shellService.findDetails(shellId, getIdAllowNull(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{shellId}/update")
    public ShellIdDto updateShell(@Valid @PathVariable long shellId,
                                         @RequestPart("update") UpdateRequestDto updateRequestDto,
                                         @RequestPart("pictures") List<MultipartFile> pictures,
                                         Authentication authentication) {
        return shellService.update(shellId, updateRequestDto, getId(authentication), pictures);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{shellId}")
    public void deleteShell(@PathVariable long shellId, Authentication authentication) {
        shellService.delete(shellId, getId(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public InquiryResponseDto inquiryShells(@RequestParam("limit") int limit,
                                            @RequestParam("cursor") Long cursor,
                                            @RequestParam("type") String shellType,
                                            @RequestParam("category") String shellCategory,
                                            @RequestParam("sort") String sort) {
        return shellService.inquiry(limit, cursor, ShellType.valueOf(shellType.toUpperCase()), ShellCategory.valueOf(shellCategory.toUpperCase()), sort);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public SearchResponseDto searchShells(@RequestParam("limit") int limit,
                                          @RequestParam("cursor") Long cursor,
                                          @RequestParam("title") String title) {
        return shellService.search(limit, cursor, title);
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

    private Long getIdAllowNull(Authentication authentication) {
        Long id;
        if (authentication == null) {
            id = null;
        } else {
            MemberContextInform memberInform = (MemberContextInform) authentication.getPrincipal();
            id = memberInform.getId();
        }
        return id;
    }
}
