package io.marklove.carinsurance.service;

import io.marklove.carinsurance.entity.Company;
import io.marklove.carinsurance.entity.QuartzJob;
import org.quartz.SchedulerException;

import java.time.LocalDateTime;

public interface QuartzJobService {
    void schedule(QuartzJob quartzjob) throws SchedulerException;

    void create(QuartzJob quartzJob) throws SchedulerException;

    void updateExtendedTime(QuartzJob quartzJob, LocalDateTime extendTime) throws SchedulerException;

    void remove(QuartzJob quartzJob) throws SchedulerException;

    QuartzJob fromCompany(Company company);
}
