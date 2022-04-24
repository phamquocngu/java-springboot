package io.marklove.carinsurance.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDto {
    private int totalMembers;

    private int numberLogin;

    private int compare;

    private int apply;

    private int confirm;

    private int constructing;

    private int complete;
}
