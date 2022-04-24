package io.marklove.carinsurance.controller.admin;

import io.marklove.carinsurance.constant.UserStatus;
import io.marklove.carinsurance.constant.enums.EMemberStatus;
import io.marklove.carinsurance.dto.admin.EditMemberDto;
import io.marklove.carinsurance.dto.admin.MemberDetailDto;
import io.marklove.carinsurance.dto.enums.EAdMemberFilterSearch;
import io.marklove.carinsurance.entity.CompanyGroup;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.projection.admin.MemberView;
import io.marklove.carinsurance.repository.CompanyGroupRepository;
import io.marklove.carinsurance.repository.MemberRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequestMapping("/api/admin/members")
@RestController
@RequiredArgsConstructor
@Api(tags = {"Member Management"})
public class MemberController {

    private final MemberRepository memberRepo;
    private final CompanyGroupRepository companyGroupRepo;
    private final ModelMapper modelMapper;

    @GetMapping
    @ApiOperation("Get List Members")
    Page<MemberView> getList(@RequestParam(required = false) EMemberStatus memberStatus,
                             @RequestParam(required = false) Boolean companyMember,
                             @RequestParam(required = false) Long companyGroupId,
                             @RequestParam(required = false) EAdMemberFilterSearch filter,
                             @RequestParam(required = false) String term,
                             Pageable page) {

        Page<MemberView> rs = null;
        String name = null;
        String memberId = null;

        if(EAdMemberFilterSearch.NAME.equals(filter)) {
            name = term;
            term = null;
        }

        if(EAdMemberFilterSearch.MEMBER_ID.equals(filter)) {
            memberId = term;
            term = null;
        }

        CompanyGroup companyGroup =  null;
        if(companyGroupId != null) {
            companyGroup = companyGroupRepo.findById(companyGroupId).orElse(null);
        }

        var lastLogIn = LocalDate.now().minusDays(365).atStartOfDay();

        if(memberStatus == null) {
            rs = memberRepo.search(null, companyMember,lastLogIn, companyGroup, name, memberId, term, page);
        } else if(EMemberStatus.NORMAL.equals(memberStatus)){
            rs = memberRepo.search(true, companyMember,lastLogIn, companyGroup, name, memberId, term, page);
        } else {
            rs = memberRepo.search(false, companyMember,lastLogIn, companyGroup, name, memberId, term, page);
        }

        return rs;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get Member Details")
    MemberDetailDto getById(@RequestParam long id) {
        final var member = memberRepo.findById(id)
                                         .orElseThrow(() -> new CommonException(ErrorCode.ENTITY_NOT_FOUND));

        return modelMapper.map(member, MemberDetailDto.class);

    }

    @DeleteMapping("/{id}")
    @ApiOperation("Force Withdrawal")
    void forceWithdraw(@RequestParam long id) {
        var member = memberRepo.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.ENTITY_NOT_FOUND));
        member.setStatus(UserStatus.DELETED);
        memberRepo.save(member);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Edit Member Details")
    void edit(@Valid @RequestBody EditMemberDto dto) {
        var member = memberRepo.findById(dto.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.ENTITY_NOT_FOUND));

        if(dto.getGroupId() != null) {
            var group = companyGroupRepo.findById(dto.getGroupId())
                    .orElseThrow(() -> new CommonException(ErrorCode.ENTITY_NOT_FOUND));

            member.setGroup(group);
        }
        member.setMemo(dto.getMemo());

        memberRepo.save(member);
    }
}
