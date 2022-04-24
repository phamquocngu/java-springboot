package io.marklove.carinsurance.controller.admin;

import io.marklove.carinsurance.dto.admin.DashboardDto;
import io.marklove.carinsurance.service.MainHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@Api(tags = {"Dashboard"})
@RequiredArgsConstructor
public class AdminDashboardController {

    private final MainHomeService mainHomeService;

    @GetMapping("/")
    @ApiOperation(value = "api for admin get info for dashboard")
    @ResponseStatus(HttpStatus.OK)
    public DashboardDto dashboard() {

        return mainHomeService.dashboard();
    }
}
