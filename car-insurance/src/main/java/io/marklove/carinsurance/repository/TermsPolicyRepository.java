package io.marklove.carinsurance.repository;

import io.marklove.carinsurance.entity.TermsPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsPolicyRepository extends JpaRepository<TermsPolicy, Long> {
}
