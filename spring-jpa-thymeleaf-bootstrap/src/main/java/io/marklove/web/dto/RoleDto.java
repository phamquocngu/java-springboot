package io.marklove.web.dto;

import io.marklove.validators.annotation.ValidRoleName;
import lombok.Data;

/**
 * @author ngupq
 */
@Data
public class RoleDto {
    Long id;
    @ValidRoleName
    String name;
}
