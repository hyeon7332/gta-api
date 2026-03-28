package com.gta.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwapOwnedTransportRequest {

	@NotNull
    private Long sourceOwnedId;

    @NotNull
    private Long targetOwnedId;
}
