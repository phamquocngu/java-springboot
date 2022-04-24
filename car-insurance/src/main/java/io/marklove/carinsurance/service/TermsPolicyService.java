package io.marklove.carinsurance.service;

import io.marklove.carinsurance.dto.term.TermsPolicyDto;
import io.marklove.carinsurance.entity.TermsPolicy;

public interface TermsPolicyService {

    TermsPolicy getTermsPolicy();

    void updateTermsPolicy(TermsPolicyDto dto);
}
