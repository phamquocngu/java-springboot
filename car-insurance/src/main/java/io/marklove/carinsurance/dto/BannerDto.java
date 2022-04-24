package io.marklove.carinsurance.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.constant.ShowStatus;
import io.marklove.carinsurance.dto.converters.AbsoluteBannerUrlSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {

    private int position;

    private ShowStatus status;

    @JsonSerialize(using = AbsoluteBannerUrlSerializer.class)
    private String imgUrl;

    private String connectionLink;
}
