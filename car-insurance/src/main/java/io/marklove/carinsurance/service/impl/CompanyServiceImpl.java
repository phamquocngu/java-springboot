package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.constant.Constants;
import io.marklove.carinsurance.constant.enums.EActivateStatus;
import io.marklove.carinsurance.constant.enums.ERegistrationStatus;
import io.marklove.carinsurance.constant.enums.EUsageStatus;
import io.marklove.carinsurance.dto.ConstructionExDto;
import io.marklove.carinsurance.dto.company.*;
import io.marklove.carinsurance.dto.enums.EAdApplicantFilterSearch;
import io.marklove.carinsurance.dto.enums.EAdCompanyFilterSearch;
import io.marklove.carinsurance.dto.enums.ECompanyRegistrationProcess;
import io.marklove.carinsurance.entity.Company;
import io.marklove.carinsurance.entity.Member;
import io.marklove.carinsurance.entity.QuartzJob;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.CompanyGroupRepository;
import io.marklove.carinsurance.repository.CompanyRepository;
import io.marklove.carinsurance.repository.ConstructionExampleRepository;
import io.marklove.carinsurance.repository.MemberRepository;
import io.marklove.carinsurance.security.AuthenticationFacade;
import io.marklove.carinsurance.service.CompanyService;
import io.marklove.carinsurance.service.QuartzJobService;
import io.marklove.carinsurance.utils.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private static final String STORAGE_PATH = "user/%d/company/";

    private final CompanyRepository companyRepo;
    private final MemberRepository memberRepo;
    private final ConstructionExampleRepository constructionExampleRepo;
    private final CompanyGroupRepository companyGroupRepo;
    private final ModelMapper mapper;
    private final StorageService storageService;
    private final AuthenticationFacade authenticationFacade;
    private final QuartzJobService quartzJobService;

    @Override
    public Page<AdminCompany> getCompanyList(EUsageStatus usageStatus, EAdCompanyFilterSearch optionSearch,
                                             String term, Pageable pageable) {

        String companyName = null;
        String companyId = null;

        if(optionSearch != null) {
            if(optionSearch.equals(EAdCompanyFilterSearch.COMPANY_NAME)) {
                companyName = term;
                term = null;
            } else if(optionSearch.equals(EAdCompanyFilterSearch.COMPANY_MEMBER_ID)) {
                companyId = term;
                term = null;
            }
        }

        Page<Company> company;
        if(usageStatus == null) {
            company = companyRepo.getCompaniesByPage(companyName, companyId, term, pageable);
        } else if(usageStatus == EUsageStatus.USE) {
            company = companyRepo.getCompaniesUseByPage(companyName, companyId, term, pageable);
        } else {
            company = companyRepo.getCompaniesUnusedByPage(companyName, companyId, term, pageable);
        }

        return company.map(m -> mapper.map(m, AdminCompany.class));
    }

    @Override
    public AdminCompanyDetail getCompanyById(long id) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        return mapper.map(company, AdminCompanyDetail.class);
    }

    @Override
    public Page<AdminApplicant> getApplicantList(ERegistrationStatus processingStatus, EAdApplicantFilterSearch optionSearch,
                                                 String term, Pageable pageable) {

        String companyName = null;
        String applicantId = null;

        if(optionSearch != null) {
            if(optionSearch.equals(EAdApplicantFilterSearch.COMPANY_NAME)) {
                companyName = term;
                term = null;
            } else if(optionSearch.equals(EAdApplicantFilterSearch.APPLICANT_ID)) {
                applicantId = term;
                term = null;
            }
        }

        Page<Company> companies = companyRepo.getApplicantsByPage(processingStatus,
                companyName, applicantId, term, pageable);

        return companies.map(m -> mapper.map(m, AdminApplicant.class));
    }

    @Override
    public AdminApplicantDetail getApplicantById(long id) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        return mapper.map(company, AdminApplicantDetail.class);
    }

    @Override
    public CompanyDetail getCompany(long id) {
        var company = companyRepo.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        var result =  mapper.map(company, CompanyDetail.class);
        result.setTotalReview(company.getTotalReview());

        var constructionExamples = constructionExampleRepo.findByWriter_Id(company.getRequestUser().getId(),
                PageRequest.of(0, 5, Sort.by("id").descending()))
                .getContent()
                .stream()
                .map(c -> mapper.map(c, ConstructionExDto.class))
                .collect(Collectors.toList());

        result.setConstructionExamples(constructionExamples);

        return result;
    }

    @Override
    @Transactional
    public FileInfo registerCompany(CompanyRegistration dto) {
        var user = getCurrentUser();

        // check exists company registered by user request
        if(companyRepo.getByUserAndStatus(user, null).isPresent()) {
            throw new CommonException(ErrorCode.EXIST_COMPANY_BY_USER);
        }

        // mapper to entity and save
        Company company = mapper.map(dto, Company.class);
        company.setRequestUser(user);
        company.setCompanyId(user.getMemberId());
        company.setActivate(EActivateStatus.ACTIVATE);
        company.setProcessingStatus(ERegistrationStatus.WAITING);

        //Get presigned url for client put to S3
        FileInfo fileInfo = dto.getAttachFile();
        final String objectKey = String.format(STORAGE_PATH, user.getId())
                + System.currentTimeMillis() + "_" + fileInfo.getFileName();
        fileInfo.setObjectKey(storageService.getPresignedUrl(objectKey, true).toString());

        company.setAttachFile(new FileInfo(fileInfo.getFileName(), objectKey));

        companyRepo.save(company);

        //update member
        user.setCompanyMember(true);
        memberRepo.save(user);

        return fileInfo;
    }

    @Override
    public CompanyRegistrationHistory getRegistrationHistory() {
        var user = getCurrentUser();

        var company = companyRepo.getByUserAndStatus(user, null)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        return mapper.map(company, CompanyRegistrationHistory.class);
    }

    @Override
    public FileInfo updateCompany(CompanyEditInfo dto) {
        var user = getCurrentUser();
        var company = companyRepo.getByUserAndStatus(user, null)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        company.setCompanyName(dto.getCompanyName());
        company.setAddress(dto.getAddress());
        company.setWorkingTime(dto.getWorkingTime());
        company.setConstructableType(dto.getConstructableType());
        company.setIntroduction(dto.getIntroduction());

        //Check update attach file
        FileInfo fileInfo = dto.getAttachFile();
        if(StringUtils.isBlank(fileInfo.getObjectKey())) {
            //Get presigned url for client put to S3
            final String objectKey = String.format(STORAGE_PATH, user.getId())
                    + System.currentTimeMillis() + "_" + fileInfo.getFileName();
            fileInfo.setObjectKey(storageService.getPresignedUrl(objectKey, true).toString());

            //Remove old attach file
            storageService.remove(company.getAttachFile().getObjectKey());

            company.setAttachFile(new FileInfo(fileInfo.getFileName(), objectKey));
        }

        companyRepo.save(company);

        return fileInfo;
    }

    @Override
    public void withdraw() {
        var company = getCurrentCompany();
        var user = company.getRequestUser();

        company.setActivate(EActivateStatus.DEACTIVATE);
        companyRepo.save(company);

        user.setCompanyMember(false);
        memberRepo.save(user);
    }

    @Override
    public FileInfo reApply(CompanyRegistration dto) {
        var user = getCurrentUser();
        var company = companyRepo.getByUserAndStatus(user, null)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        //check: the company has been approved?
        if(company.getProcessingStatus() == ERegistrationStatus.APPROVE) {
            throw new CommonException(ErrorCode.COMPANY_APPROVED);
        }

        company.setCompanyName(dto.getCompanyName());
        company.setAddress(dto.getAddress());
        company.setWorkingTime(dto.getWorkingTime());
        company.setConstructableType(dto.getConstructableType());
        company.setContact(dto.getContact());
        company.setRepresentativeName(dto.getRepresentativeName());
        company.setEntryRoute(dto.getEntryRoute());
        company.setProcessingStatus(ERegistrationStatus.WAITING);

        //Check update attach file
        FileInfo fileInfo = dto.getAttachFile();
        if(StringUtils.isBlank(fileInfo.getObjectKey())) {
            //Get presigned url for client put to S3
            final String objectKey = String.format(STORAGE_PATH, user.getId())
                    + System.currentTimeMillis() + "_" + fileInfo.getFileName();
            fileInfo.setObjectKey(storageService.getPresignedUrl(objectKey, true).toString());

            //Remove old attach file
            storageService.remove(company.getAttachFile().getObjectKey());

            company.setAttachFile(new FileInfo(fileInfo.getFileName(), objectKey));
        }

        companyRepo.save(company);

        return fileInfo;
    }

    @Override
    public void cancelRegistration() {
        var user = getCurrentUser();
        var company = companyRepo.getByUserAndStatus(user, null)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        //check: the company has been approved?
        if(company.getProcessingStatus() == ERegistrationStatus.APPROVE) {
            throw new CommonException(ErrorCode.COMPANY_APPROVED);
        }

        companyRepo.delete(company);

        user.setCompanyMember(false);
        memberRepo.save(user);

        //Remove old attach file
        storageService.remove(company.getAttachFile().getObjectKey());
    }

    @Override
    public void process(long id, AdminProcessDto dto) {
        ECompanyRegistrationProcess action = dto.getAction();

        var company = companyRepo.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));

        if(company.getProcessingStatus() != ERegistrationStatus.WAITING) {
            throw new CommonException(ErrorCode.COMPANY_CANNOT_PROCESS);
        }

        if(action == ECompanyRegistrationProcess.APPROVE) {
            company.setEntryDate(LocalDateTime.now());
            company.setProcessingStatus(ERegistrationStatus.APPROVE);
        } else  {
            String reason = dto.getReason();
            if(StringUtils.isBlank(reason)) {
                throw new CommonException(ErrorCode.COMPANY_PROCESS_REASON_BLANK);
            }
            company.setReason(reason);
            company.setProcessingStatus(ERegistrationStatus.REJECT);
        }

        companyRepo.save(company);
    }

    @Override
    public Company getCurrentCompany() {
        var user = getCurrentUser();

        return companyRepo.getByUserAndStatus(user, ERegistrationStatus.APPROVE)
                .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_NOT_FOUND));
    }

    @Override
    public void extension() throws SchedulerException {

        var company = getCurrentCompany();
        var member = company.getRequestUser();
        var companyGroup = member.getGroup();
        if(companyGroup == null) {
            companyGroup = companyGroupRepo.findByName(Constants.GENERAL_GROUP)
                    .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_GROUP_NOT_FOUND_GG));
        }

        //check have enough point
        long point = member.getPoint() - companyGroup.getFee();
        if(point < 0) {
            throw new CommonException(ErrorCode.COMPANY_NOT_ENOUGH_POINT);
        }

        member.setPoint(point);
        var expiredTime = LocalDateTime.now().plusDays(30);
        company.setExpiredDateTime(expiredTime);
        //auto extend expired time
        if(company.isAutoExtend()) {
            var quartJob = quartzJobService.fromCompany(company);
            if(quartJob == null) {
                var quartzJob = new QuartzJob(company);
                quartzJobService.create(quartzJob);
            } else {
                quartzJobService.updateExtendedTime(quartJob, expiredTime);
            }
        }

        memberRepo.save(member);
        companyRepo.save(company);
    }

    private Member getCurrentUser() {
        var userDetail = authenticationFacade.getAuthentication();

        return memberRepo.findById(userDetail.getUserId())
                .orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
    }
}
