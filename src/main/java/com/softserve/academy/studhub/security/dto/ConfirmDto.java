package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.constants.ValidationConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConfirmDto {

    @NotBlank(message = ValidationConstants.EMPTY_TOKEN)
    private String token;
}
