package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.entity.QuartzJob;
import io.marklove.carinsurance.repository.CompanyRepository;
import io.marklove.carinsurance.service.CompanyService;
import io.marklove.carinsurance.service.CompanyUsageService;
import io.marklove.carinsurance.service.QuartzJobService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyUsageServiceImpl implements CompanyUsageService {
    private final CompanyService companyService;
    private final CompanyRepository companyRepo;
    private final QuartzJobService quartzJobService;

//    @Override
//    @Transactional
//    public void addPoints(AddPointsDto dto) throws SchedulerException {
//        var company = companyService.getCurrentCompany();
//        var companyUsage = companyUsageRepo.findByCompany(company).orElse(new CompanyUsage(company));
//        var expiredTime = LocalDateTime.now().plusDays(30);
//        companyUsage.setExpiredTime(expiredTime);
//
//        if (companyUsage.isAutoExtend()) {
//            companyUsage.setAutoExtendTime(expiredTime);
//
//            if (companyUsage.getId() > 0) {
//                quartzJobService.updateExtendedTime(quartzJobService.fromCompanyUsage(companyUsage), expiredTime);
//            } else {
//                companyUsage = companyUsageRepo.save(companyUsage);
//                var quartzJob = new QuartzJob(companyUsage);
//                quartzJobService.create(quartzJob);
//            }
//        }
//
//        // TODO add point later
//        companyUsageRepo.save(companyUsage);
//    }

    @Override
    @Transactional
    public void releaseAutoExtension() throws SchedulerException {
        var company = companyService.getCurrentCompany();
        var quartzJob = quartzJobService.fromCompany(company);

        if (quartzJob != null) {
            quartzJobService.remove(quartzJob);
        }

        company.setAutoExtend(false);
        companyRepo.save(company);
    }

    @Override
    @Transactional
    public void restoreAutoExtension() throws SchedulerException {
        var company = companyService.getCurrentCompany();
        var quartzJob = quartzJobService.fromCompany(company);
        if (quartzJob == null) {
            quartzJobService.create(new QuartzJob(company));
        }

        company.setAutoExtend(true);
        companyRepo.save(company);
    }
}
