package com.piedpiper.gib.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Author: Vadym Polyanski
 * Date: 07.11.17
 * Time: 17:08
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data
class RepositoryRequest extends TokenRequest{
    private String user;
    private String repository;
}
