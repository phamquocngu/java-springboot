package io.marklove.carinsurance.repository;

import io.marklove.carinsurance.entity.Company;
import io.marklove.carinsurance.entity.QuartzJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuartzJobRepository extends JpaRepository<QuartzJob, Long> {
    Optional<QuartzJob> findByCompany(Company company);
}
