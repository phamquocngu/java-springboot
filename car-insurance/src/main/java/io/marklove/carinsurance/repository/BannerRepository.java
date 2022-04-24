package io.marklove.carinsurance.repository;

import io.marklove.carinsurance.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Integer> {
}