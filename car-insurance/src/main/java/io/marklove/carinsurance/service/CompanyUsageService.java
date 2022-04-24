package io.marklove.carinsurance.service;

import org.quartz.SchedulerException;

public interface CompanyUsageService {

    void releaseAutoExtension() throws SchedulerException;

    void restoreAutoExtension() throws SchedulerException;
}
