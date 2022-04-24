package io.marklove.carinsurance.repository;

import io.marklove.carinsurance.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    Optional<FAQ> findFirstByOrderByPositionDesc();
}