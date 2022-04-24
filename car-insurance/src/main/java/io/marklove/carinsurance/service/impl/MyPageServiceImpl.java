package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.constant.Constants;
import io.marklove.carinsurance.constant.UserStatus;
import io.marklove.carinsurance.dto.AccountSetting;
import io.marklove.carinsurance.dto.FAQInfo;
import io.marklove.carinsurance.dto.MemberInfo;
import io.marklove.carinsurance.entity.Member;
import io.marklove.carinsurance.entity.embeddable.NoticeSetting;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.CompanyGroupRepository;
import io.marklove.carinsurance.repository.FAQRepository;
import io.marklove.carinsurance.repository.MemberRepository;
import io.marklove.carinsurance.security.AuthenticationFacade;
import io.marklove.carinsurance.security.UserDetails;
import io.marklove.carinsurance.service.CompanyService;
import io.marklove.carinsurance.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final CompanyService companyService;
    private final AuthenticationFacade authenticationFacade;
    private final MemberRepository memberRepo;
    private final CompanyGroupRepository companyGroupRepo;
    private final FAQRepository faqRepo;
    private final ModelMapper mapper;

    @Override
    public MemberInfo getMemberInfo() {
        var member = getCurrentUser();
        var memberInfo = mapper.map(member, MemberInfo.class);

        var companyGroup = member.getGroup();
        if(companyGroup == null) {
            companyGroup = companyGroupRepo.findByName(Constants.GENERAL_GROUP)
                    .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_GROUP_NOT_FOUND_GG));
        }

        memberInfo.setFee(companyGroup.getFee());
        memberInfo.setDeliveryCost(companyGroup.getDeliveryCost());

        var company = member.getCompany();
        if(company != null) {
            memberInfo.setCompanyId(company.getId());
            memberInfo.setRegisteredCompany(true);
            memberInfo.setCompanyStatus(company.getProcessingStatus());
            if(company.getExpiredDateTime() != null) {
                memberInfo.setExpired(company.getExpiredDateTime().toLocalDate().isAfter(LocalDate.now())
                        ? false : true);
            } else {
                memberInfo.setExpired(true);
            }
        }

        return memberInfo;
    }

    @Override
    public AccountSetting getAccountInfo() {
        var account = getCurrentUser();

        return mapper.map(account, AccountSetting.class);
    }

    @Override
    public NoticeSetting getNoticeSetting() {
        return getCurrentUser().getNoticeSetting() == null ?
                new NoticeSetting() :
                mapper.map(getCurrentUser().getNoticeSetting(), NoticeSetting.class);
    }

    @Override
    public void setupNoticeSetting(NoticeSetting setting) {
        var user = getCurrentUser();

        user.setNoticeSetting(setting);
        memberRepo.save(user);
    }

    @Override
    public Page<FAQInfo> getFAQs(Pageable pageable) {
        var sort = Sort.by("position").descending().and(Sort.by("createdOn").descending());
        Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        var faqs = faqRepo.findAll(paging);

        return faqs.map(f -> mapper.map(f, FAQInfo.class));
    }

    @Override
    public void withdraw() {
        var user = getCurrentUser();

        user.setStatus(UserStatus.INACTIVATED);

        memberRepo.save(user);
    }

    private Member getCurrentUser() {
        UserDetails userDetails = authenticationFacade.getAuthentication();

        return memberRepo.findById(userDetails.getUserId())
                .orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
    }
}
