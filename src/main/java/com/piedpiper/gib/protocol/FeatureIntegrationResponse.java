package com.piedpiper.gib.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public @Data
class FeatureIntegrationResponse extends Response {
    private Integer avg;
    private Integer count;
    private Integer max;
    private Integer min;
}
