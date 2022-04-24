package io.marklove.carinsurance.dto.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOrder implements Serializable {

    private long id;

    private int orderCategory;
}
