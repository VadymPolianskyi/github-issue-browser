package com.piedpiper.gib.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data
class TokenRequest extends Request{

    @NotNull
    private String token;
}
