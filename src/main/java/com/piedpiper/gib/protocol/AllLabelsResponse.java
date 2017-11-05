package com.piedpiper.gib.protocol;

import com.piedpiper.gib.protocol.dao.LabelDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public @Data
class AllLabelsResponse extends Response{
    private List<LabelDao> labels;
}
