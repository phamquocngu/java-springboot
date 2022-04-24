package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.dto.term.TermsPolicyDto;
import io.marklove.carinsurance.entity.TermsPolicy;
import io.marklove.carinsurance.repository.TermsPolicyRepository;
import io.marklove.carinsurance.service.TermsPolicyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermPolicyServiceImpl implements TermsPolicyService {

    private final TermsPolicyRepository termsPolicyRepo;
    private final ModelMapper mapper;

    @Override
    public TermsPolicy getTermsPolicy() {

        var termsPolicy = termsPolicyRepo.findAll();

        if(termsPolicy == null || termsPolicy.isEmpty()) {
            return new TermsPolicy();
        } else {
            return termsPolicy.get(0);
        }
    }

    @Override
    public void updateTermsPolicy(TermsPolicyDto dto) {
        var entity = new TermsPolicy();

        var termsPolicy = termsPolicyRepo.findAll();

        if(termsPolicy != null && !termsPolicy.isEmpty()) {
            entity = termsPolicy.get(0);
        }
        //set data update
        entity.setServicePolicy(dto.getServicePolicy());
        entity.setRefundPolicy(dto.getRefundPolicy());
        entity.setPrivacyStatement(dto.getPrivacyStatement());

        termsPolicyRepo.save(entity);
    }
}
