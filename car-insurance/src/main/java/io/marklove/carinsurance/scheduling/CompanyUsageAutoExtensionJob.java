package io.marklove.carinsurance.scheduling;

import io.marklove.carinsurance.constant.Constants;
import io.marklove.carinsurance.constant.enums.ENoticeType;
import io.marklove.carinsurance.entity.AppNotice;
import io.marklove.carinsurance.entity.Company;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.AppNoticeRepository;
import io.marklove.carinsurance.repository.CompanyGroupRepository;
import io.marklove.carinsurance.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
public class CompanyUsageAutoExtensionJob implements Job {

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        var jobDataMap = context.getJobDetail().getJobDataMap();
        var company = (Company) jobDataMap.get("company");
        var companyGroupRepo = (CompanyGroupRepository) jobDataMap.get("companyGroupRepo");

        log.info("Start extension company " + company.getCompanyName());
        var expiredTime = company.getExpiredDateTime();
        expiredTime = expiredTime.plusDays(30);
        var member = company.getRequestUser();
        var companyGroup = member.getGroup();
        if(companyGroup == null) {
            companyGroup = companyGroupRepo.findByName(Constants.GENERAL_GROUP)
                    .orElseThrow(() -> new CommonException(ErrorCode.COMPANY_GROUP_NOT_FOUND_GG));
        }

        //check have enough point
        long point = member.getPoint() - companyGroup.getFee();

        if (point < 0) {
            var appNoticeRepo = (AppNoticeRepository) jobDataMap.get("appNoticeRepo");
            var notice = new AppNotice();
            notice.setContent("업체회원 자동 연장에 필요한 잔여 포인트가 부족합니다.");
            notice.setRecipient(company.getRequestUser());
            notice.setType(ENoticeType.POINT);
            appNoticeRepo.save(notice);
            log.info("Remaining points not enough the required for automatic renewal of company member");
        } else {
            var companyRepo = (CompanyRepository) jobDataMap.get("companyRepo");

            member.setPoint(point);
            company.setExpiredDateTime(expiredTime);
            companyRepo.save(company);
        }

        log.info("Automatic extension successfully!");
    }
}
