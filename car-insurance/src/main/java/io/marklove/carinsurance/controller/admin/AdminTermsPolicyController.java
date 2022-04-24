package io.marklove.carinsurance.controller.admin;

import io.marklove.carinsurance.dto.term.TermsPolicyDto;
import io.marklove.carinsurance.service.TermsPolicyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/terms-policy")
@Api(tags = {"Admin Terms Policy"})
@RequiredArgsConstructor
public class AdminTermsPolicyController {

    private final TermsPolicyService termsPolicyService;
    private final ModelMapper mapper;

    @PutMapping("/")
    @ApiOperation(value = "api for admin update terms policy")
    @ResponseStatus(HttpStatus.OK)
    public void updateTermsPolicy(@Valid @RequestBody TermsPolicyDto dto) {

        termsPolicyService.updateTermsPolicy(dto);
    }

    @GetMapping("/")
    @ApiOperation(value = "api for admin get terms policy")
    @ResponseStatus(HttpStatus.OK)
    public TermsPolicyDto getTermsPolicy() {

        return mapper.map(termsPolicyService.getTermsPolicy(), TermsPolicyDto.class);
    }
}
