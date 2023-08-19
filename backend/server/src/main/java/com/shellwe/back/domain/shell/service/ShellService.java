package com.shellwe.back.domain.shell.service;

import com.shellwe.back.entity.type.Category;
import com.shellwe.back.domain.category.service.CategoryService;
import com.shellwe.back.entity.Member;
import com.shellwe.back.domain.member.service.MemberService;
import com.shellwe.back.domain.shell.dto.request.RegisterRequestDto;
import com.shellwe.back.domain.shell.dto.request.UpdateRequestDto;
import com.shellwe.back.domain.shell.dto.response.*;
import com.shellwe.back.entity.Shell;
import com.shellwe.back.domain.shell.mapper.ShellMapper;
import com.shellwe.back.domain.shell.repository.ShellRepository;
import com.shellwe.back.entity.type.ShellType;
import com.shellwe.back.entity.ShellCategory;
import com.shellwe.back.exception.customexception.ShellLogicException;
import com.shellwe.back.exception.exceptioncode.ShellExceptionCode;
import com.shellwe.back.file.service.UploadPictureService;
import com.shellwe.back.utils.event.ShellRemoveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class ShellService {

    private final ShellRepository shellRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final UploadPictureService uploadPictureService;
    private final ShellMapper shellMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ShellService(ShellRepository shellRepository, MemberService memberService,
                        CategoryService categoryService, ShellMapper shellMapper, UploadPictureService uploadPictureService,
                        ApplicationEventPublisher eventPublisher) {
        this.shellRepository = shellRepository;
        this.memberService = memberService;
        this.categoryService = categoryService;
        this.shellMapper = shellMapper;
        this.uploadPictureService = uploadPictureService;
        this.eventPublisher = eventPublisher;
    }

    public ShellIdDto register(RegisterRequestDto registerRequestDto, long memberId, List<MultipartFile> pictures) {
        Shell registerShell = shellMapper.registerRequestDtoToShell(registerRequestDto);
        List<String> pictureUrls = uploadPictureService.severalPictureFilesToUrls(pictures);
        Category category = categoryService.findOrCreate(registerRequestDto.getCategory());

        registerShell.statusActive();
        registerShell.setMember(memberService.getMemberByOtherLayer(memberId));
        registerShell.setPictureUrls(pictureUrls);
        registerShell.setCategory(category);

        ShellIdDto shellIdDto = new ShellIdDto();
        shellIdDto.setId(shellRepository.save(registerShell).getId());
        return shellIdDto;
    }

    @Transactional(readOnly = true)
    public FindDetailsResponseDto findDetails(Long shellId, Long memberId) {
        Shell shell = findById(shellId);
        FindDetailsResponseDto findDetailsResponseDto = shellMapper.shellToFindDetailsResponseDto(shell);
        findDetailsResponseDto.getMember().setMe(false);

        if (memberId != null && memberId.equals(shell.getMember().getId())) {
            findDetailsResponseDto.getMember().setMe(true);
        }
        return findDetailsResponseDto;
    }

    public ShellIdDto update(long shellId, UpdateRequestDto updateRequestDto, long memberId, List<MultipartFile> pictures) {
        ShellIdDto shellIdDto = new ShellIdDto();
        Shell shell = findById(shellId);
        Member member = shell.getMember();

        if (member.getId() == memberId) {
            Shell requestShell = shellMapper.updateRequestDtoToShell(updateRequestDto);
            Category category = categoryService.findOrCreate(updateRequestDto.getCategory());
            requestShell.setCategory(category);
            shell.deleteAllTags();
            shell.updateShellInformExceptPictureUrl(requestShell);
            List<String> shellPicturesUrl = uploadPictureService.severalPictureFilesToUrls(pictures);
            shell.deleteAllPictureUrls();
            shell.setPictureUrls(shellPicturesUrl);
            shellIdDto.setId(shellRepository.save(shell).getId());
        } else {
            throw new ShellLogicException(ShellExceptionCode.SHELL_NOT_MY_ID);
        }
        return shellIdDto;
    }

    public void delete(long shellId, long memberId) {
        Shell shell = findById(shellId);

        if (shell.getMember().getId() == memberId) {
            eventPublisher.publishEvent(new ShellRemoveEvent(shell.getId()));
            shellRepository.delete(shell);
        } else {
            throw new ShellLogicException(ShellExceptionCode.SHELL_NOT_MY_ID);
        }
    }

    @Transactional(readOnly = true)
    public InquiryResponseDto inquiry(int limit, Long cursor, ShellType shellType, ShellCategory shellCategory, String sort) {
        if (cursor == 0 && sort.equals("newest")) {
            cursor = shellRepository.findMaxId().orElse(0L) + 1L;
        } else if (cursor == 0 && sort.equals("oldest")) {
            cursor = shellRepository.findMinId().orElse(0L) - 1L;
        }
        String sortOrder = checkSortType(sort);
        Pageable pageable = PageRequest.of(0, limit);

        List<Shell> shells = findShellsCondition(cursor, shellType, shellCategory, sortOrder, pageable);
        InquiryResponseDto inquiryResponseDto = new InquiryResponseDto();

        List<ShellResponseDto> shellsDto = shellMapper.shellsToInquiryResponseDto(shells);
        inquiryResponseDto.setShells(shellsDto);
        return inquiryResponseDto;
    }

    private List<Shell> findShellsCondition(Long cursor, ShellType shellType, ShellCategory shellCategory, String sortOrder, Pageable pageable) {
        List<Shell> shells = null;
        if (shellCategory == ShellCategory.P_ALL || shellCategory == ShellCategory.T_ALL) {
            if (sortOrder.equals("desc")) {
                shells = shellRepository.findAllCategoryShellsDesc(cursor, shellType, pageable);
            } else {
                shells = shellRepository.findAllCategoryShellsAsc(cursor, shellType, pageable);
            }
        } else {
            if (sortOrder.equals("desc")) {
                shells = shellRepository.findShellsDesc(cursor, shellType, shellCategory, pageable);
            } else {
                shells = shellRepository.findShellsAsc(cursor, shellType, shellCategory, pageable);
            }
        }
        return shells;
    }

    private String checkSortType(String sort) {
        String sortOrder;
        if (sort.equals("newest")) {
            sortOrder = "desc";
        } else if (sort.equals("oldest")) {
            sortOrder = "asc";
        } else {
            throw new ShellLogicException(ShellExceptionCode.NOT_SUPPORT_TYPE);
        }
        return sortOrder;
    }

    @Transactional(readOnly = true)
    public SearchResponseDto search(int limit, Long cursor, String title) {
        if (cursor == 0) {
            cursor = shellRepository.findMaxId().orElse(0L) + 1;
        }
        Pageable pageable = PageRequest.of(0, limit);
        List<Shell> shells = shellRepository.searchShells("%" + title + "%", cursor, pageable);
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        List<ShellResponseDto> shellsDto = shellMapper.shellsToInquiryResponseDto(shells);
        searchResponseDto.setShells(shellsDto);
        return searchResponseDto;
    }

    @Transactional(readOnly = true)
    public Shell getShellByOtherLayer(long shellId) {
        return findById(shellId);
    }

    @Transactional(readOnly = true)
    public List<Shell> getShellsByOtherLayer(List<Long> shellIds) {
        return shellRepository.findAllById(shellIds);
    }

    private Shell findById(long shellId) {
        Optional<Shell> byId = shellRepository.findById(shellId);
        return byId.orElseThrow(() -> new ShellLogicException(ShellExceptionCode.SHELL_NOT_MY_ID));
    }

    public void saveFromOtherLayer(Shell shell) {
        shellRepository.save(shell);
    }
}
