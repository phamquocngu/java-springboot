package io.marklove.carinsurance.service;

import io.marklove.carinsurance.constant.enums.ERegistrationStatus;
import io.marklove.carinsurance.constant.enums.EUsageStatus;
import io.marklove.carinsurance.dto.company.*;
import io.marklove.carinsurance.dto.enums.EAdApplicantFilterSearch;
import io.marklove.carinsurance.dto.enums.EAdCompanyFilterSearch;
import io.marklove.carinsurance.entity.Company;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    Page<AdminCompany> getCompanyList(EUsageStatus usageStatus, EAdCompanyFilterSearch optionSearch,
                                      String term, Pageable pageable);

    AdminCompanyDetail getCompanyById(long id);

    Page<AdminApplicant> getApplicantList(ERegistrationStatus processingStatus,
                                          EAdApplicantFilterSearch optionSearch, String term, Pageable pageable);

    AdminApplicantDetail getApplicantById(long id);

    CompanyDetail getCompany(long id);

    FileInfo registerCompany(CompanyRegistration dto);

    CompanyRegistrationHistory getRegistrationHistory();

    FileInfo updateCompany(CompanyEditInfo dto);

    void withdraw();

    FileInfo reApply(CompanyRegistration dto);

    void cancelRegistration();

    void process(long id, AdminProcessDto dto);

    Company getCurrentCompany();

    void extension() throws SchedulerException;
}
