package io.marklove.carinsurance.controller.admin;

import io.marklove.carinsurance.constant.ShowStatus;
import io.marklove.carinsurance.dto.ConstructionExStatusDto;
import io.marklove.carinsurance.dto.admin.ConstructionExampleDetail;
import io.marklove.carinsurance.dto.admin.ConstructionExampleList;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.ConstructionExampleRepository;
import io.marklove.carinsurance.utils.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/construction-ex")
@Api(tags = {"Construction example"})
@RequiredArgsConstructor
public class AdConstructionExampleController {
    private final ConstructionExampleRepository constructionExampleRepo;
    private final ModelMapper mapper;
    private final StorageService storageService;

    @GetMapping
    @ApiOperation(value = "Api for slide 19")
    public Page<ConstructionExampleList> getReviewList(@RequestParam(required = false) ShowStatus status,
                                                       @RequestParam(required = false) String companyName,
                                                       @RequestParam(required = false) String writerId,
                                                       @RequestParam(required = false) String writerName,
                                                       Pageable page) {

        return constructionExampleRepo.findConstructionEx(status, companyName, writerId, writerName, page)
                .map(r -> mapper.map(r, ConstructionExampleList.class));
    }

    @GetMapping("/{id}")
    public ConstructionExampleDetail getDetail(@PathVariable long id) {
        var constructionEx = constructionExampleRepo.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.ENTITY_NOT_FOUND));

        var res = mapper.map(constructionEx, ConstructionExampleDetail.class);

        return res;
    }

    @PatchMapping
    @Transactional
    public void changeStatus(@Valid @RequestBody List<ConstructionExStatusDto> statusDtos) {
        for (var dto: statusDtos) {
            var constructionEx = constructionExampleRepo.findById(dto.getId())
                    .orElseThrow(() -> new CommonException(ErrorCode.ENTITY_NOT_FOUND));
            constructionEx.setStatus(dto.getStatus());
            constructionExampleRepo.save(constructionEx);
        }
    }
}
